package web.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import core.entity.Employee;
import core.exception.BusinessException;
import core.util.ApiResponse;
import web.cart.dto.ExChangeRequestDto;
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

	@PostMapping("/exchange")
	public ApiResponse<Void> exchangeGiftCard(@SessionAttribute("employee") Employee employee,
			@RequestBody ExChangeRequestDto request) {
		if (request.getGiftCardId() == null) {
			throw new BusinessException("尚未選擇欲兌換禮券");
		}

		assetService.exchangeGiftCard(employee.getEmployeeId(), request.getGiftCardId());
		return ApiResponse.success(null);
	}
}
