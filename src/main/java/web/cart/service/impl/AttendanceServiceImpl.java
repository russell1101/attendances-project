package web.cart.service.impl;

import web.cart.service.AttendanceService;
import web.cart.dao.AttendanceDAO;
import web.cart.dao.impl.AttendanceDAOImpl;
import core.pojo.AttendanceRecord;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 出勤服務實作類別
 */
public class AttendanceServiceImpl implements AttendanceService {

    private AttendanceDAO dao = new AttendanceDAOImpl();

    @Override
    public String clockIn(Long employeeId) {
        // 取得今天的日期
        Date today = Date.valueOf(LocalDate.now());

        // 檢查今天是否已經打過卡
        AttendanceRecord existingRecord = dao.findByEmployeeIdAndDate(employeeId, today);

        if (existingRecord != null) {
            return "今天已經打過上班卡了！";
        }

        // 建立新的出勤紀錄
        AttendanceRecord newRecord = new AttendanceRecord();
        newRecord.setEmployeeId(employeeId);
        newRecord.setWorkDate(today);
        newRecord.setClockInTime(Time.valueOf(LocalTime.now()));
        newRecord.setClockInStatus("ON_TIME"); // 簡化版本，先設定為準時
        newRecord.setPointsAwarded(BigDecimal.ZERO);

        // 儲存到資料庫
        dao.insert(newRecord);

        return "上班打卡成功！";
    }

    @Override
    public String clockOut(Long employeeId) {
        // 取得今天的日期
        Date today = Date.valueOf(LocalDate.now());

        // 檢查今天的出勤紀錄
        AttendanceRecord record = dao.findByEmployeeIdAndDate(employeeId, today);

        if (record == null) {
            return "請先打上班卡！";
        }

        if (record.getClockOutTime() != null) {
            return "今天已經打過下班卡了！";
        }

        // 更新下班時間
        record.setClockOutTime(Time.valueOf(LocalTime.now()));
        record.setClockOutStatus("NORMAL"); // 簡化版本，先設定為正常

        // 更新到資料庫
        dao.update(record);

        return "下班打卡成功！";
    }

    @Override
    public int getTodayStatus(Long employeeId) {
        // 取得今天的日期
        Date today = Date.valueOf(LocalDate.now());

        // 查詢今天的出勤紀錄
        AttendanceRecord record = dao.findByEmployeeIdAndDate(employeeId, today);

        if (record == null) {
            return 0; // 尚未打卡
        }

        if (record.getClockOutTime() != null) {
            return 2; // 已下班打卡
        }

        return 1; // 已上班打卡
    }
}
