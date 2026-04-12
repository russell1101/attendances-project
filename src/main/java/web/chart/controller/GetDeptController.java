package web.chart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import core.entity.Department;
import web.chart.service.ChartService;

@RestController
@RequestMapping("/admin/chart")
public class GetDeptController {
	@Autowired 
	private ChartService service;
	
	@GetMapping("getDepts")
	public List<Department> getDepts() {
		return service.getDeptOptions();
	}
}
