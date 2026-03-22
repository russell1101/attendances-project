package web.employee.dao.impl;

import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import core.entity.Employee;
import web.employee.dao.EmployeeDao;

@Repository
public class EmployeeDaoImpl implements EmployeeDao{
	@PersistenceContext
	private Session session;
	
	@Override
	public Employee selectByEmail(String email) {
		String hql = "FROM employees WHERE email = :email";
		Query<Employee> query = session.createQuery(hql, Employee.class)
				.setParameter("email", email);
		return query.getSingleResult();
	}
	
	@Override
	public List<Employee> selectAll() {
		String hql = "FROM Employee";
		Query<Employee> query = session.createQuery(hql, Employee.class); 
		return query.getResultList();
	}
}
