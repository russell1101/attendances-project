package web.employee.dao;

import java.util.List;
import core.entity.Department;

public interface DepartmentDao {
	
	int upsert(Department department);
	
	int deleteById(Integer id);
	
	Department selectById(Integer id);
	
	List<Department> selectAll();

}
