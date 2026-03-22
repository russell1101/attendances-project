package web.clockIn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import core.entity.Employee;
import core.util.ApiResponse;
import web.clockIn.dto.ClockInResultDto;
import web.clockIn.dto.ClockStatusDto;
import web.clockIn.service.ClockService;

@RestController
@RequestMapping("/frontUser/clock")
public class ClockController {
	@Autowired
	private ClockService service;

	@PostMapping("/clockin")
	public ApiResponse<ClockInResultDto> clockIn(@SessionAttribute("employee") Employee employee) {

		ClockInResultDto resultDto = service.clockIn(employee.getEmployeeId());
		return ApiResponse.success(resultDto);

	}

	@PostMapping("/clockout")
	public ApiResponse<ClockInResultDto> clockOut(@SessionAttribute("employee") Employee employee) {

		ClockInResultDto resultDto = service.clockOut(employee.getEmployeeId());
		return ApiResponse.success(resultDto);

	}

	@GetMapping("/status")
	public ApiResponse<ClockStatusDto> getTodayStatus(@SessionAttribute("employee") Employee employee) {

		ClockStatusDto status = service.getTodayStatus(employee.getEmployeeId());
		return ApiResponse.success(status);

	}

}
