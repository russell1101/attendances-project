package web.productManage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import core.entity.AdminUser;
import core.util.ApiResponse;
import web.productManage.dto.RedemptionDetailDto;
import web.productManage.dto.RedemptionSummaryDto;
import web.productManage.service.AdminRedemptionService;

@RestController
@RequestMapping("/admin/redemption")
public class AdminRedemptionController {

	@Autowired
	private AdminRedemptionService service;

	// 商品兌換彙總清單
	@GetMapping("/summary")
	public ApiResponse<List<RedemptionSummaryDto>> getSummary(
			@SessionAttribute("adminUser") AdminUser adminUser) {

		return ApiResponse.success(service.getSummary());
	}

	// 單一商品兌換明細（支援篩選）
	@GetMapping("/detail")
	public ApiResponse<List<RedemptionDetailDto>> getDetail(
			@SessionAttribute("adminUser") AdminUser adminUser,
			@RequestParam Long productId,
			@RequestParam(required = false) String empName,
			@RequestParam(required = false) Long statusId,
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate) {

		return ApiResponse.success(service.getDetail(productId, empName, statusId, startDate, endDate));
	}
}
