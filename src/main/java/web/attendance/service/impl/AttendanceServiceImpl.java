package web.attendance.service.impl;

import web.attendance.bean.AttendanceRecordVO;
import web.attendance.dao.AttendanceDAO;
import web.attendance.dao.impl.AttendanceDAOImpl;
import web.attendance.service.AttendanceService;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;
import java.util.List;

public class AttendanceServiceImpl implements AttendanceService {

	private AttendanceDAO dao = new AttendanceDAOImpl();

	@Override
	public String clockIn(Long employeeId) {
		// 取得現在的日期和時間
		LocalDate today = LocalDate.now();
		LocalTime now = LocalTime.now();

		System.out.println("DEBUG: 員工 " + employeeId + " 打卡，時間: " + now);

		AttendanceRecordVO record = new AttendanceRecordVO();
		record.setEmployeeId(employeeId);
		record.setWorkDate(Date.valueOf(today));
		record.setClockInTime(Time.valueOf(now));

		// 判斷是否遲到 (上班時間是9點)
		LocalTime startTime = LocalTime.of(9, 0);
		if (now.isAfter(startTime)) {
			record.setClockInStatus("LATE");
			record.setPointsAwarded(new BigDecimal("-5.0"));
		} else {
			record.setClockInStatus("ON_TIME");
			record.setPointsAwarded(new BigDecimal("10.0"));
		}

		dao.insert(record);

		if ("LATE".equals(record.getClockInStatus())) {
			return "上班打卡成功！遲到 -5 分";
		} else {
			return "上班打卡成功！準時 +10 分";
		}
	}

	@Override
	public String clockOut(Long employeeId) {
		LocalDate today = LocalDate.now();
		LocalTime now = LocalTime.now();

		// 查詢今天有沒有上班打卡
		AttendanceRecordVO record = dao.findByEmployeeIdAndDate(employeeId, Date.valueOf(today));
		if (record == null) {
			return "請先上班打卡";
		}

		// 檢查是否有上班時間（防止資料異常）
		if (record.getClockInTime() == null) {
			return "打卡資料異常，請聯絡管理員";
		}

		record.setClockOutTime(Time.valueOf(now));

		// 計算工作時數
		LocalTime clockIn = record.getClockInTime().toLocalTime();
		long hours = Duration.between(clockIn, now).toHours();

		System.out.println("工作時數: " + hours + " 小時");

		// 判斷是否早退 (要工作滿9小時)
		if (hours < 9) {
			record.setClockOutStatus("EARLY_LEAVE");
			BigDecimal penalty = new BigDecimal("-10.0");
			// 確保 pointsAwarded 不是 null
			if (record.getPointsAwarded() != null) {
				record.setPointsAwarded(record.getPointsAwarded().add(penalty));
			} else {
				record.setPointsAwarded(penalty);
			}
			// record.setPointsAwarded(record.getPointsAwarded() - 10.0); // 舊寫法
		} else {
			record.setClockOutStatus("NORMAL");
		}

		dao.update(record);

		if ("EARLY_LEAVE".equals(record.getClockOutStatus())) {
			return "下班打卡成功！工作未滿 9 小時，早退 -10 分";
		} else {
			return "下班打卡成功！";
		}
	}

	@Override
	public int getTodayStatus(Long employeeId) {
		LocalDate today = LocalDate.now();
		AttendanceRecordVO record = dao.findByEmployeeIdAndDate(employeeId, Date.valueOf(today));

		if (record == null) {
			return 0; // 還沒上班
		} else if (record.getClockOutTime() == null) {
			return 1; // 工作中
		} else {
			return 2; // 已下班
		}
	}

	@Override
	public AttendanceRecordVO getTodayRecord(Long employeeId) {
		LocalDate today = LocalDate.now();
		return dao.findByEmployeeIdAndDate(employeeId, Date.valueOf(today));
	}

	@Override
	public List<AttendanceRecordVO> getRecordsByEmployeeId(Long employeeId) {
		// TODO: 可能需要加分頁
		return dao.findByEmployeeId(employeeId);
	}

	@Override
	public boolean deleteRecord(Long attendanceId) {
		return dao.deleteById(attendanceId);
	}
}