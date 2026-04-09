package web.employee.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import core.entity.Employee;
import core.util.ApiResponse;
import web.employee.dto.EmployeeDto;
import web.employee.service.AdminUserService;
import web.employee.service.EmployeeService;

@RestController
@RequestMapping("admin/emp")
public class EmpManageController {
	@Autowired
	private EmployeeService service;
	@Autowired
	private AdminUserService adminUserService;
	
	// 只看的到啟用狀態帳號
	@GetMapping("manage")
	@ResponseBody
	public ApiResponse<List<EmployeeDto>> manage() {
		List<EmployeeDto> employeeList = service.getAllEmployees();
		return ApiResponse.success(employeeList);
	}
	
	@PostMapping("save")
	@ResponseBody
	public ApiResponse<Integer> save(@RequestBody Employee employee) {
		if (employee == null) {
			return ApiResponse.error("新增失敗");
		} 
			return ApiResponse.success(adminUserService.saveEmployee(employee));
	}
	
	@PostMapping("remove")
	@ResponseBody
	public ApiResponse<Integer> remove(@RequestBody Employee employee) {
		final Long id = employee.getEmployeeId();
		if (id == null) {
			ApiResponse.error("刪除失敗");
		}
		return ApiResponse.success(adminUserService.deleteEmployee(id));
	}
	
	@PostMapping("update")
	@ResponseBody
	public ApiResponse<Integer> update(@RequestBody Employee employee) {
		if (employee == null) {
			return ApiResponse.error("更新失敗");
		} 
			return ApiResponse.success(adminUserService.saveEmployee(employee));
	}
}
