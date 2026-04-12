package web.chart.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import web.chart.service.ChartService;

@RestController
@RequestMapping("/admin/chart")
public class DIcsvController {
    @Autowired
    private ChartService service;

    @GetMapping("/exportCsv")
    private String exportCsv(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(required = false) Long deptId,
            @RequestParam(required = false) Long empId,
            HttpServletResponse resp) {

        resp.setContentType("text/csv;charset=UTF-8");
        resp.setHeader("Content-Disposition", "attachment; filename=attendance_report.csv");
        return service.getCsvString(startDate, endDate, deptId, empId);
    }
}
