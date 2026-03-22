package web.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import core.entity.Employee;
import core.util.ApiResponse;
import web.cart.dto.UserAssetDto;
import web.cart.service.CartAssetService;

@RestController
@RequestMapping("/frontUser/cart")
public class CartAssetController {

	@Autowired
	private CartAssetService assetService;

	// 取得會員 所有禮券+點數
	@GetMapping("/myAssets")
	public ApiResponse<UserAssetDto> getMyAssets(@SessionAttribute("employee") Employee employee) {
		UserAssetDto assets = assetService.getUserAssets(employee.getEmployeeId());
		return ApiResponse.success(assets);
	}
}
