package web.cart.service;

import java.util.List;

import core.pojo.Product;
import web.cart.dto.BuyResult;

public interface ProductService {
	List<Product> getAllProducts();

	BuyResult buyProduct(Long empId, Long prodId, Integer qty) throws Exception;
}
