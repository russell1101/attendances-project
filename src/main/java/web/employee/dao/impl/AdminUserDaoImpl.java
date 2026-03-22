package web.employee.dao.impl;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import core.entity.AdminUser;
import web.employee.dao.AdminUserDao;

@Repository
public class AdminUserDaoImpl implements AdminUserDao {
	@PersistenceContext
	private Session session;

	@Override
	public AdminUser selectByUsername(String username) {
		String hql = "FROM admin_users WHERE username = :username";
		Query<AdminUser> query = session.createQuery(hql, AdminUser.class)
				.setParameter("username", username);
		return query.getSingleResult();
	}
}
