package web.employee.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import core.entity.Department;
import web.employee.dao.DepartmentDao;
import web.employee.dao.EmployeeDao;
import web.employee.dto.DepartmentDto;
import web.employee.service.DepartmentService;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService{
	@Autowired
	private DepartmentDao departmentDao;
	@Autowired
	private EmployeeDao employeeDao;
	
	@Override
	public int saveDepartment(Department department) {
		return departmentDao.upsert(department);
	}

	@Override
	public int deleteDepartment(Long id) {
		Department department = departmentDao.selectById(id);
		
		// 所有啟用員工的depId
		List<Long> empDepIdList = employeeDao.selectDep();
		
		for (Long empDepId : empDepIdList) {
			if (empDepId == department.getDepartmentId()) {
				return -1;
			}
		}
		department.setIsActive(false);
		return departmentDao.upsert(department);
	}

	@Override
	public List<Department> getAllDepartments() {
		return departmentDao.selectAll();
	}

	@Override
	public List<DepartmentDto> getAllDepartmentList() {
		List<Department> departments = departmentDao.selectAll();
		List<DepartmentDto> departmentDto = new ArrayList<>();

		for (Department dep : departments) {
			DepartmentDto dto = new DepartmentDto(dep);
			departmentDto.add(dto);
		}
		return departmentDto;
	}
}
