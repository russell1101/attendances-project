package web.cart.dao.impl;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import core.entity.Employee;
import web.cart.dao.CartEmployeeDao;

@Repository
public class CartEmployeeDaoImpl implements CartEmployeeDao {

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
