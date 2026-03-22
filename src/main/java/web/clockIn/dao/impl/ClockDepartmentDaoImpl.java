package web.clockIn.dao.impl;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import core.entity.Department;
import web.clockIn.dao.ClockDepartmentDao;

@Repository
public class ClockDepartmentDaoImpl implements ClockDepartmentDao {

	@PersistenceContext
	private Session session;

	@Override
	public Department findById(Long departmentId) {
		return session.get(Department.class, departmentId);
	}
}
