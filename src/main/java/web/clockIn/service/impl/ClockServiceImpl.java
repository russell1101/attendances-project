package web.clockIn.service.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import core.entity.AttendanceRecord;
import core.entity.Department;
import core.entity.Employee;
import core.entity.PointTransaction;
import core.exception.BusinessException;
import web.clockIn.dao.AttendanceDao;
import web.clockIn.dao.DepartmentDao;
import web.clockIn.dao.EmployeeDao;
import web.clockIn.dto.ClockInResultDto;
import web.clockIn.service.ClockService;

@Service
@Transactional
public class ClockServiceImpl implements ClockService {
	@Autowired
	private AttendanceDao clockDao;
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private DepartmentDao departmentDao;

	@Override
	@Transactional
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

		Department dept = departmentDao.findById(emp.getDepartmentId());

		// 判斷準時/遲到
		boolean isOnTime = nowTime.compareTo(dept.getWorkStartTime().toLocalTime()) <= 0;
		String status = isOnTime ? "準時" : "遲到";

		// 決定點數
		BigDecimal pointsToAward;
		if (isOnTime) { //
			pointsToAward = dept.getOnTimeBonusPoints();
			if (pointsToAward.compareTo(BigDecimal.ZERO) == 0) {
				pointsToAward = new BigDecimal(clockDao.getGlobalSettingValue("GLOBAL_ON_TIME_BONUS"));
			}
		} else { // 遲到
			pointsToAward = dept.getLatePenaltyPoints();
			if (pointsToAward.compareTo(BigDecimal.ZERO) == 0) {
				pointsToAward = new BigDecimal(clockDao.getGlobalSettingValue("GLOBAL_LATE_PENALTY"));
			}
		}

		// 儲存打卡紀錄
		AttendanceRecord record = new AttendanceRecord();
		record.setEmployeeId(employeeId);
		record.setWorkDate(sqlToday);
		record.setClockInTime(sqlNowTime);
		record.setClockInStatus(status);
		record.setPointsAwarded(pointsToAward);
		clockDao.saveAttendance(record);

		// 更新員工身上的總點數
		emp.setCurrentPoints(emp.getCurrentPoints().add(pointsToAward));
		employeeDao.update(emp);

		// 點數異動明細
		PointTransaction txn = new PointTransaction();
		txn.setEmployeeId(employeeId);
		txn.setPointsUpdate(pointsToAward);
		txn.setReason("上班打卡 - " + status);
		txn.setRelatedType("ATTENDANCE");
		txn.setRelatedId(record.getAttendanceId()); // 取得剛新增id
		clockDao.savePointTransaction(txn);

		return new ClockInResultDto(status, pointsToAward);
	}
}
