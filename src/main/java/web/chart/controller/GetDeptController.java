package web.chart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import core.entity.Department;
import web.chart.service.ChartService;

@Controller
@RequestMapping("/admin/chart")
public class GetDeptController {
	@Autowired 
	private ChartService service;
	
	@GetMapping("getDepts")
	@ResponseBody
	public List<Department> getDepts() {
		return service.getDeptOptions();
	}
}
