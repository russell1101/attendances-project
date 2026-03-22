package web.clockIn.dao;

import core.entity.AttendanceRecord;
import core.entity.PointTransaction;

public interface AttendanceDao {

	void saveAttendance(AttendanceRecord record);

	String getGlobalSettingValue(String key);

	void savePointTransaction(PointTransaction transaction);

	AttendanceRecord findTodayRecord(Long employeeId, java.sql.Date today);

	java.util.List<core.entity.AttendanceRecord> findHistoryByMonth(Long employeeId, int year, int month);
}
