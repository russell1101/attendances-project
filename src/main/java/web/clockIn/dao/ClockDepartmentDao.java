package web.clockIn.dao;

import core.entity.Department;

public interface ClockDepartmentDao {
	Department findById(Long departmentId);
}
