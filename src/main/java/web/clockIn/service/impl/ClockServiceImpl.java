package web.clockIn.service.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import core.annotation.DbOperation;
import core.entity.AttendanceRecord;
import core.entity.Department;
import core.entity.Employee;
import core.entity.PointTransaction;
import core.enums.AttendanceStatus;
import core.enums.EmployeeStatusEnum;
import core.enums.GlobalSettingKeyEnum;
import core.enums.PointTransactionType;
import core.exception.BusinessException;
import web.clockIn.dao.AttendanceDao;
import web.clockIn.dao.ClockDepartmentDao;
import web.clockIn.dao.ClockEmployeeDao;
import web.clockIn.dto.AttendanceHistoryDto;
import web.clockIn.dto.ClockInResultDto;
import web.clockIn.dto.ClockStatusDto;
import web.clockIn.dto.EmployeeProfileDto;
import web.clockIn.service.ClockService;

@Service
@Transactional
public class ClockServiceImpl implements ClockService {
	@Autowired
	private AttendanceDao attendanceDao;
	@Autowired
	private ClockEmployeeDao employeeDao;
	@Autowired
	private ClockDepartmentDao departmentDao;

	@Override
	@DbOperation(action = "員工打卡上班")
	public ClockInResultDto clockIn(Long employeeId) {
		// 取得當前時間與日期
		LocalDate today = LocalDate.now();
		LocalTime nowTime = LocalTime.now();
		Date sqlToday = Date.valueOf(today);
		Time sqlNowTime = Time.valueOf(nowTime);

		// 取得員工與部門資訊
		Employee emp = employeeDao.findById(employeeId);
		if (emp == null) {
			throw new BusinessException("找不到該員工");
		}

		// 檢查狀態
		if (Boolean.FALSE.equals(emp.getIsActive())) {
			throw new BusinessException("帳號已被停用，無法進行打卡作業。");
		}
		if (!EmployeeStatusEnum.ACTIVE.getId().equals(emp.getEmployeeStatusId())) {
			throw new BusinessException("目前非在職狀態，無法進行打卡作業。");
		}

		// 檢查是否已打卡
		AttendanceRecord existingRecord = attendanceDao.findTodayRecord(employeeId, sqlToday);
		if (existingRecord != null) {

			if (existingRecord.getClockInTime() != null) {
				throw new BusinessException("今日已經完成上班打卡，請勿重複操作");
			}
		}

		Department dept = departmentDao.findById(emp.getDepartmentId());

		// 判斷準時/遲到
		boolean isOnTime = nowTime.compareTo(dept.getWorkStartTime().toLocalTime()) <= 0;
		AttendanceStatus status = isOnTime ? AttendanceStatus.ON_TIME : AttendanceStatus.LATE;

		Long lateMinutes = 0L;
		if (!isOnTime) {
			// 遲到分鐘數
			lateMinutes = Duration.between(dept.getWorkStartTime().toLocalTime(), nowTime).toMinutes();
		}

		// 決定點數
		BigDecimal pointsToAward;
		if (isOnTime) { //
			pointsToAward = dept.getOnTimeBonusPoints();
			if (pointsToAward.compareTo(BigDecimal.ZERO) == 0) {
				pointsToAward = new BigDecimal(
						attendanceDao.getGlobalSettingValue(GlobalSettingKeyEnum.GLOBAL_ON_TIME_BONUS.getKey()));
			}
		} else { // 遲到
			pointsToAward = dept.getLatePenaltyPoints();
			if (pointsToAward.compareTo(BigDecimal.ZERO) == 0) {
				pointsToAward = new BigDecimal(
						attendanceDao.getGlobalSettingValue(GlobalSettingKeyEnum.GLOBAL_LATE_PENALTY.getKey()));
			}
		}

		// 儲存打卡紀錄
		AttendanceRecord record = new AttendanceRecord();
		record.setEmployeeId(employeeId);
		record.setWorkDate(sqlToday);
		record.setClockInTime(sqlNowTime);
		record.setClockInStatus(status);
		record.setPointsAwarded(pointsToAward);
		attendanceDao.saveAttendance(record);

		// 更新員工身上的總點數
		emp.setCurrentPoints(emp.getCurrentPoints().add(pointsToAward));
		employeeDao.update(emp);

		// 點數異動明細
		PointTransaction txn = new PointTransaction();
		txn.setEmployeeId(employeeId);
		txn.setPointsUpdate(pointsToAward);
		txn.setReason("上班打卡 - " + status);
		txn.setRelatedType(PointTransactionType.ATTENDANCE.name());

		txn.setRelatedId(record.getAttendanceId());
		attendanceDao.savePointTransaction(txn);

		return new ClockInResultDto(status, pointsToAward, lateMinutes);
	}

	@Override
	@DbOperation(action = "員工打卡下班")
	public ClockInResultDto clockOut(Long employeeId) {

		LocalDate today = LocalDate.now();
		LocalTime nowTime = LocalTime.now();
		Date sqlToday = Date.valueOf(today);
		Time sqlNowTime = Time.valueOf(nowTime);

		// 檢查權限
		Employee emp = employeeDao.findById(employeeId);
		if (emp == null) {
			throw new BusinessException("找不到該員工");
		}
		if (!Boolean.TRUE.equals(emp.getIsActive())) {
			throw new BusinessException("帳號已停用");
		}
		if (!EmployeeStatusEnum.ACTIVE.getId().equals(emp.getEmployeeStatusId())) {
			throw new BusinessException("非在職狀態");
		}

		// 檢查今日上班紀錄是否存在
		AttendanceRecord record = attendanceDao.findTodayRecord(employeeId, sqlToday);
		if (record == null || record.getClockInTime() == null) {
			throw new BusinessException("尚未進行上班打卡，無法進行下班打卡");
		}
		if (record.getClockOutTime() != null) {
			throw new BusinessException("今日已完成下班打卡，請勿重複操作");
		}

		// 判斷準時下班或早退
		Department dept = departmentDao.findById(emp.getDepartmentId());
		boolean isNormalLeave = nowTime.compareTo(dept.getWorkEndTime().toLocalTime()) >= 0;

		AttendanceStatus status = isNormalLeave ? AttendanceStatus.ON_TIME : AttendanceStatus.EARLY_LEAVE;

		Long earlyLeaveMinutes = 0L;
		if (!isNormalLeave) {
			earlyLeaveMinutes = Duration.between(nowTime, dept.getWorkEndTime().toLocalTime()).toMinutes();
		}

		// 更新 set資料
		record.setClockOutTime(sqlNowTime);
		record.setClockOutStatus(status);

		return new ClockInResultDto(status, BigDecimal.ZERO, earlyLeaveMinutes);
	}

	@Override
	public ClockStatusDto getTodayStatus(Long employeeId) {
		java.sql.Date today = java.sql.Date.valueOf(LocalDate.now());

		AttendanceRecord record = attendanceDao.findTodayRecord(employeeId, today);

		// 今天一筆紀錄都沒有
		if (record == null) {
			return new ClockStatusDto(false, false, null, null);
		}

		// 如果有紀錄 檢查欄位是否有值
		boolean hasClockedIn = record.getClockInTime() != null;
		boolean hasClockedOut = record.getClockOutTime() != null;

		return new ClockStatusDto(hasClockedIn, hasClockedOut, hasClockedIn ? record.getClockInTime().toString() : null,
				hasClockedOut ? record.getClockOutTime().toString() : null);
	}

	// 取得打卡歷程
	@Override
	public List<AttendanceHistoryDto> getMonthlyHistory(Long employeeId, int year, int month) {
		List<AttendanceRecord> records = attendanceDao.findHistoryByMonth(employeeId, year, month);
		List<AttendanceHistoryDto> dtoList = new java.util.ArrayList<>();

		for (AttendanceRecord record : records) {
			AttendanceHistoryDto dto = new AttendanceHistoryDto();

			// 日期轉字串
			if (record.getWorkDate() != null) {
				dto.setWorkDate(record.getWorkDate().toString());
			}

			if (record.getClockInTime() != null) {
				dto.setClockInTime(record.getClockInTime().toString());
			}
			if (record.getClockOutTime() != null) {
				dto.setClockOutTime(record.getClockOutTime().toString());
			}

			dto.setClockInStatus(record.getClockInStatus());
			dto.setClockOutStatus(record.getClockOutStatus());

			// 點數轉換
			if (record.getPointsAwarded() != null) {
				dto.setPointsAwarded(record.getPointsAwarded().intValue());
			} else {
				dto.setPointsAwarded(0);
			}

			dtoList.add(dto);
		}

		return dtoList;
	}

	// 取得會員資訊
	@Override
	public EmployeeProfileDto getEmployeeProfile(Long employeeId) {
		Employee emp = employeeDao.findById(employeeId);

		if (emp == null) {
			return null;
		}

		String deptName = "--";
		if (emp.getDepartmentId() != null) {
			Department dept = departmentDao.findById(emp.getDepartmentId());
			if (dept != null) {
				deptName = dept.getDepartmentName();
			}
		}

		String statusName = "--";
		if (emp.getEmployeeStatusId() != null) {
			EmployeeStatusEnum statusEnum = EmployeeStatusEnum.fromId(emp.getEmployeeStatusId());

			if (statusEnum != null) {
				statusName = statusEnum.getDescription();
			}
		}

		return EmployeeProfileDto.builder().employeeId(emp.getEmployeeId()).name(emp.getName()).email(emp.getEmail())
				.departmentName(deptName).hireDate(emp.getHireDate() != null ? emp.getHireDate().toString() : "")
				.currentPoints(emp.getCurrentPoints() != null ? emp.getCurrentPoints().intValue() : 0)
				.statusName(statusName).isActive(emp.getIsActive()).build();
	}
}
