package web.cart.service;

/**
 * 出勤服務 Interface
 */
public interface AttendanceService {

    /**
     * 上班打卡
     * 
     * @param employeeId 員工ID
     * @return 打卡結果訊息
     */
    String clockIn(Long employeeId);

    /**
     * 下班打卡
     * 
     * @param employeeId 員工ID
     * @return 打卡結果訊息
     */
    String clockOut(Long employeeId);

    /**
     * 取得今日打卡狀態
     * 
     * @param employeeId 員工ID
     * @return 0=尚未打卡 1=已上班打卡 2=已下班打卡
     */
    int getTodayStatus(Long employeeId);
}
