package web.employee.dao.impl;

import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import core.entity.Employee;
import web.employee.dao.EmployeeDao;
import web.employee.dto.EmployeeDto;

@Repository("EmployeeDaoImpl")
public class EmployeeDaoImpl implements EmployeeDao{
	@PersistenceContext
	private Session session;
	
	@Override
	public int upsert(Employee employee) {
		session.merge(employee);
		return 1;
	}
	
	@Override
	public int deleteById(Integer id) {
		Employee employee = selectById(id);
		session.remove(employee);
		return 1;
	}
	
	@Override
	public Employee selectById(Integer id) {
		return session.get(Employee.class, id);
	}
	
	@Override
	public Employee selectByEmail(String email) {
		String hql = "FROM Employee WHERE email = :email";
		Query<Employee> query = session.createQuery(hql, Employee.class)
				.setParameter("email", email);
		return query.getSingleResult();
	}
	
	@Override
	public List<EmployeeDto> selectAll() {
		String hql = "SELECT \r\n"
				+ "    e.employee_id, \r\n"
				+ "    e.name, \r\n"
				+ "    e.email, \r\n"
				+ "    e.google_sub, \r\n"
				+ "    e.hire_date, \r\n"
				+ "    e.current_points, \r\n"
				+ "    d.department_name,\r\n"
				+ "    s.status_name,        \r\n"
				+ "    e.created_at, \r\n"
				+ "    e.updated_at\r\n"
				+ "FROM employees e\r\n"
				+ "LEFT JOIN departments d ON e.department_id = d.department_id\r\n"
				+ "LEFT JOIN employee_statuses s ON e.employee_status_id = s.employee_status_id;";
		Query<EmployeeDto> query = session.createQuery(hql, EmployeeDto.class); 
		return query.getResultList();
	}
}
