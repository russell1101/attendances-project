package web.cart.dao.impl;

import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import core.entity.Product;
import web.cart.dao.CartProductDao;

@Repository
public class CartProductDaoImpl implements CartProductDao {

	@PersistenceContext
	private Session session;

	@Override
	public List<Product> findAll() {
		return session.createQuery("FROM Product", Product.class).list();
	}

	@Override
	public Product findById(Long productId) {
		return session.get(Product.class, productId);
	}

	@Override
	public void update(Product product) {
		session.update(product);
	}
}