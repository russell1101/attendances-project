package web.employee.dao;

import java.util.List;
import core.entity.Department;

public interface DepartmentDao {
	
	int upsert(Department department);
	
	Department selectById(Long id);
	
	List<Department> selectAll();

}
