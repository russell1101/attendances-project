package web.attendance.dao;

import web.attendance.bean.AttendanceRecordVO;
import java.sql.Date;
import java.util.List;

// DAO 介面，定義出勤紀錄的資料庫操作
public interface AttendanceDAO {

	// 新增打卡紀錄（上班）
	boolean insert(AttendanceRecordVO record);

	// 更新打卡紀錄（下班）
	boolean update(AttendanceRecordVO record);

	// 根據員工ID和日期查詢當天的出勤
	AttendanceRecordVO findByEmployeeIdAndDate(Long employeeId, Date workDate);

	// 查詢某個員工的所有出勤紀錄
	List<AttendanceRecordVO> findByEmployeeId(Long employeeId);

	// 刪除出勤紀錄
	boolean deleteById(Long attendanceId);
}