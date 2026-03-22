package web.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import core.entity.Employee;
import core.util.ApiResponse;
import web.cart.dto.BuyRequestDto;
import web.cart.dto.BuyResultDto;
import web.cart.dto.ProductDto;
import web.cart.service.CartProductService;

@RestController
@RequestMapping("/frontUser/product")
public class CartProductController {
	@Autowired
	private CartProductService productService;

	@GetMapping("/getAll")
	public ApiResponse<List<ProductDto>> getAllProducts() {
		List<ProductDto> products = productService.getAllProducts();
		return ApiResponse.success(products);
	}

	@PostMapping("/buy")
	public ApiResponse<BuyResultDto> buyProduct(@SessionAttribute("employee") Employee employee,
			@RequestBody BuyRequestDto request) {

		// 數量購買量
		int qty = (request.getQty() != null) ? request.getQty() : 1;

		BuyResultDto result = productService.buyProduct(employee.getEmployeeId(), request.getProductId(), qty);
		return ApiResponse.success(result);
	}
}