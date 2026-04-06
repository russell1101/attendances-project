package web.productManage.dao.impl;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import core.entity.GlobalSetting;
import web.productManage.dao.AdminSettingDao;

@Repository
public class AdminSettingDaoImpl implements AdminSettingDao {

	@PersistenceContext
	private Session session;

	@Override
	public GlobalSetting findByKey(String key) {
		return session.createQuery("FROM GlobalSetting WHERE settingKey = :key", GlobalSetting.class)
				.setParameter("key", key)
				.uniqueResult();
	}

	@Override
	public void update(GlobalSetting setting) {
		session.update(setting);
	}
}
