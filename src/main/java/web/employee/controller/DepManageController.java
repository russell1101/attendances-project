package web.employee.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import core.entity.Department;
import core.util.ApiResponse;
import web.employee.dto.DepartmentDto;
import web.employee.service.DepartmentService;

@RestController
@RequestMapping("admin/dep")
public class DepManageController {
	@Autowired
	private DepartmentService service;
	
	@GetMapping("manage")
	@ResponseBody
	public ApiResponse<List<Department>> manage() {
		List<Department> departments = service.getAllDepartments();
		return ApiResponse.success(departments);
	}
	
	@PostMapping("save")
	@ResponseBody
	public ApiResponse<Integer> save(@RequestBody Department department) {
		if (department == null) {
			return ApiResponse.error("新增失敗");
		} 
			return ApiResponse.success(service.saveDepartment(department));
	}
	
	@PostMapping("remove")
	@ResponseBody
	public ApiResponse<Integer> remove(@RequestBody Department department) {
		final Long id = department.getDepartmentId();
		if (id == null) {
			return ApiResponse.error("刪除失敗");
		}
		int result = service.deleteDepartment(id);
		if(result == -1) {
			return ApiResponse.error("該部門尚有員工");
		}
		return ApiResponse.success(1);
	}
	
	@PostMapping("update")
	@ResponseBody
	public ApiResponse<Integer> update(@RequestBody Department department) {
		if (department == null) {
			return ApiResponse.error("更新失敗");
		} 
			return ApiResponse.success(service.saveDepartment(department));
	}
	
	@GetMapping("depOptions")
	@ResponseBody
	public ApiResponse<List<DepartmentDto>> depOptions() {
		List<DepartmentDto> departmentList = service.getAllDepartmentList();
		return ApiResponse.success(departmentList);
	}
}
