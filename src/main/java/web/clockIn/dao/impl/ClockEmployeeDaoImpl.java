package web.clockIn.dao.impl;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import core.entity.Employee;
import web.clockIn.dao.ClockEmployeeDao;

@Repository
public class ClockEmployeeDaoImpl implements ClockEmployeeDao {

	@PersistenceContext
	private Session session;

	@Override
	public Employee findById(Long employeeId) {
		return session.get(Employee.class, employeeId);
	}

	@Override
	public void update(Employee employee) {
		session.update(employee);
	}
}