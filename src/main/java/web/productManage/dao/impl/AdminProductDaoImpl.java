package web.productManage.dao.impl;

import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import core.entity.Product;
import web.productManage.dao.AdminProductDao;

@Repository
public class AdminProductDaoImpl implements AdminProductDao {

	@PersistenceContext
	private Session session;

	@Override
	public List<Product> findAll() {
		return session.createQuery("FROM Product ORDER BY createdAt DESC", Product.class).list();
	}

	@Override
	public Product findById(Long productId) {
		return session.get(Product.class, productId);
	}

	@Override
	public void save(Product product) {
		session.save(product);
	}

	@Override
	public void update(Product product) {
		session.update(product);
	}
}
