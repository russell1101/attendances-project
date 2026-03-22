package web.clockIn.dao.impl;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import core.entity.AttendanceRecord;
import core.entity.GlobalSetting;
import core.entity.PointTransaction;
import web.clockIn.dao.AttendanceDao;

@Repository
public class AttendanceDaoImpl implements AttendanceDao {
	@PersistenceContext
	private Session session;

	// 存打卡紀錄
	@Override
	public void saveAttendance(AttendanceRecord record) {
		session.save(record);
	}

	// 存點數異動紀錄
	@Override
	public void savePointTransaction(PointTransaction transaction) {
		session.save(transaction);
	}

	// 查全域點數
	@Override
	public String getGlobalSettingValue(String key) {
		GlobalSetting setting = session.createQuery("FROM GlobalSetting WHERE settingKey = :key", GlobalSetting.class)
				.setParameter("key", key).uniqueResult();
		return setting != null ? setting.getSettingValue() : "0";
	}

	// 找到今日打卡紀錄
	@Override
	public AttendanceRecord findTodayRecord(Long employeeId, java.sql.Date today) {
		return session
				.createQuery("FROM AttendanceRecord WHERE employeeId = :empId AND workDate = :today",
						AttendanceRecord.class)
				.setParameter("empId", employeeId).setParameter("today", today).uniqueResult();
	}
}
