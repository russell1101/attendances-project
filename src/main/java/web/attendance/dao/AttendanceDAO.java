package web.attendance.dao;

import web.attendance.bean.AttendanceRecordVO;
import java.sql.Date;
import java.util.List;

/**
 * ?ºå‹¤ç´€??DAO Interface
 */
public interface AttendanceDAO {

    /**
     * ?°å??ºå‹¤ç´€?„ï?ä¸Šç­?“å¡ï¼?     * 
     * @param record ?ºå‹¤ç´€?„ç‰©ä»?     * @return ?°å??å??å‚³ true
     */
    boolean insert(AttendanceRecordVO record);

    /**
     * ?´æ–°?ºå‹¤ç´€?„ï?ä¸‹ç­?“å¡ï¼?     * 
     * @param record ?ºå‹¤ç´€?„ç‰©ä»?     * @return ?´æ–°?å??å‚³ true
     */
    boolean update(AttendanceRecordVO record);

    /**
     * ?¹æ??¡å·¥ID?Œæ—¥?ŸæŸ¥è©¢å‡º?¤ç???     * 
     * @param employeeId ?¡å·¥ID
     * @param workDate   å·¥ä??¥æ?
     * @return ?ºå‹¤ç´€?„ç‰©ä»¶ï??¥ç„¡?‡å???null
     */
    AttendanceRecordVO findByEmployeeIdAndDate(Long employeeId, Date workDate);

    /**
     * ?¥è©¢?¡å·¥?„æ??‰å‡º?¤ç???     * 
     * @param employeeId ?¡å·¥ID
     * @return ?ºå‹¤ç´€?„å?è¡?     */
    List<AttendanceRecordVO> findByEmployeeId(Long employeeId);

    /**
     * ?ªé™¤?ºå‹¤ç´€??     * 
     * @param attendanceId ?ºå‹¤ç´€?„ID
     * @return ?ªé™¤?å??å‚³ true
     */
    boolean deleteById(Long attendanceId);
}
