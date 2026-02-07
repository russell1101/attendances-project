package web.attendance.service;

import web.attendance.bean.AttendanceRecordVO;
import java.util.List;

public interface AttendanceService {

	// 上班打卡
	String clockIn(Long employeeId);

	// 下班打卡
	String clockOut(Long employeeId);

	// 取得今天的狀態 (0=未打卡, 1=已上班, 2=已下班)
	int getTodayStatus(Long employeeId);

	// 取得今天的出勤紀錄
	AttendanceRecordVO getTodayRecord(Long employeeId);

	// 取得員工所有的出勤紀錄
	List<AttendanceRecordVO> getRecordsByEmployeeId(Long employeeId);

	// 刪除出勤紀錄
	boolean deleteRecord(Long attendanceId);
}