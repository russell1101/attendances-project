package web.employee.dao.impl;

import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import core.entity.Department;
import web.employee.dao.DepartmentDao;

@Repository("DepartmentDaoImpl")
public class DepartmentDaoImpl implements DepartmentDao{
	@PersistenceContext
	private Session session;

	@Override
	public int upsert(Department department) {
		session.merge(department);
		return 1;
	}

	@Override
	public Department selectById(Long id) {
		return session.get(Department.class, id);
	}

	@Override
	public List<Department> selectAll() {
		String hql = "FROM Department WHERE isActive = true";
		Query<Department> query = session.createQuery(hql, Department.class); 
		return query.getResultList();
	}
}
