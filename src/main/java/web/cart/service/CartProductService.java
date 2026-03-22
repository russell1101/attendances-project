package web.cart.service;

import java.util.List;

import web.cart.dto.BuyResultDto;
import web.cart.dto.ProductDto;

public interface CartProductService {
	List<ProductDto> getAllProducts();

	BuyResultDto buyProduct(Long employeeId, Long productId, Integer qty);
}