package web.cart.dao;

import java.sql.Connection;
import java.util.List;

import core.pojo.Product;

public interface ProductDao {
	List<Product> getAllProducts();

	Product selectById(Connection conn, Long productId);

	int deductStock(Connection conn, Long productId, int qty);
}
