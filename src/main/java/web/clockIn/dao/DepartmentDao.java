package web.clockIn.dao;

import core.entity.Department;

public interface DepartmentDao {
	Department findById(Long departmentId);
}
