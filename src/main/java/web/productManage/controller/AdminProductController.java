package web.productManage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import core.entity.AdminUser;
import core.util.ApiResponse;
import web.productManage.dto.AdminProductDto;
import web.productManage.service.AdminProductService;

@RestController
@RequestMapping("/admin/product")
public class AdminProductController {

	@Autowired
	private AdminProductService service;

	// 取得所有商品清單
	@GetMapping("/list")
	public ApiResponse<List<AdminProductDto>> getAllProducts(
			@SessionAttribute("adminUser") AdminUser adminUser) {

		List<AdminProductDto> products = service.getAllProducts();
		return ApiResponse.success(products);
	}

	// 新增或修改商品
	@PostMapping("/save")
	public ApiResponse<String> saveProduct(
			@SessionAttribute("adminUser") AdminUser adminUser,
			@RequestBody AdminProductDto dto) {

		service.saveProduct(dto);
		return ApiResponse.success("success");
	}
}
