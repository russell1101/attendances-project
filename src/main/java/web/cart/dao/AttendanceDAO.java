package web.cart.dao;

import core.pojo.AttendanceRecord;
import java.sql.Date;
import java.util.List;

/**
 * 出勤紀錄 DAO Interface
 */
public interface AttendanceDAO {

    /**
     * 新增出勤紀錄
     */
    void insert(AttendanceRecord record);

    /**
     * 更新出勤紀錄
     */
    void update(AttendanceRecord record);

    /**
     * 根據員工ID查詢所有紀錄
     */
    List<AttendanceRecord> findByEmployeeId(Long employeeId);

    /**
     * 根據員工ID和日期查詢紀錄
     */
    AttendanceRecord findByEmployeeIdAndDate(Long employeeId, Date workDate);
}
