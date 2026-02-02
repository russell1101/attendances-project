package web.attendance.service;

import web.attendance.bean.AttendanceRecordVO;
import java.util.List;

public interface AttendanceService {

	// 上班打卡
	String clockIn(Long employeeId);

	// 下班打卡
	String clockOut(Long employeeId);

	// 取得今日出勤狀態
	int getTodayStatus(Long employeeId);

	// 取得今日出勤紀錄
	AttendanceRecordVO getTodayRecord(Long employeeId);

	// 取得員工所有出勤紀錄
	List<AttendanceRecordVO> getRecordsByEmployeeId(Long employeeId);

	// 刪除出勤紀錄
	boolean deleteRecord(Long attendanceId);
}
