package web.productManage.dao;

import java.util.List;

import core.entity.Product;

public interface AdminProductDao {

	List<Product> findAll();

	Product findById(Long productId);

	void save(Product product);

	void update(Product product);
}
