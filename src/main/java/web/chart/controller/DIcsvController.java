package web.chart.controller;

import java.io.OutputStream;

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
	public void exportCsv(@RequestParam String startDate, @RequestParam String endDate,
			@RequestParam(required = false) Long deptId, @RequestParam(required = false) Long empId,
			HttpServletResponse resp) throws Exception {

		resp.setContentType("text/csv;charset=UTF-8");
		resp.setHeader("Content-Disposition", "attachment; filename=attendance_report.csv");

		String csv = service.getCsvString(startDate, endDate, deptId, empId);

		// 全部用 OutputStream 寫，不要混用 getWriter()
		byte[] bom = new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
		byte[] content = csv.getBytes("UTF-8");

		OutputStream os = resp.getOutputStream();
		os.write(bom);
		os.write(content);
		os.flush();
	}
}
