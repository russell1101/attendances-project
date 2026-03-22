package web.cart.dao;

import java.util.List;

import core.entity.Product;

public interface CartProductDao {
	List<Product> findAll();

	Product findById(Long productId);

	void update(Product product);
}