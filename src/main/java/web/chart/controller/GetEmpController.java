package web.chart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import core.entity.Employee;
import web.chart.service.ChartService;

@Controller
@RequestMapping("admin/chart")
public class GetEmpController {
	@Autowired
	private ChartService service;
	
	@GetMapping("/getEmps")
	@ResponseBody
	public List<Employee> getEmps(@RequestParam(required = false) Integer deptId){
		return service.getEmpOptions(deptId);
	}
}
