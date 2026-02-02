package web.cart.service.impl;

import java.util.List;

import javax.naming.NamingException;

import core.pojo.Product;
import web.cart.dao.ProductDao;
import web.cart.dao.impl.ProductDaoImpl;
import web.cart.service.ProductService;

public class ProductServiceImpl implements ProductService {
	private ProductDao productDao;

	public ProductServiceImpl() throws NamingException {
		productDao = new ProductDaoImpl();
	}

	@Override
	public List<Product> getAllProducts() {
		List<Product> productList = productDao.getAllProducts();
		return productList;

	}

}
