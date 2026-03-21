package web.clockIn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import core.entity.Employee;
import core.util.ApiResponse;
import web.clockIn.dto.ClockInResultDto;
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

}
