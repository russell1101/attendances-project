package web.cart.dao.impl;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import core.entity.ProductRedemption;
import web.cart.dao.CartProductRedemptionDao;

@Repository
public class CartProductRedemptionDaoImpl implements CartProductRedemptionDao {
	@PersistenceContext
	private Session session;

	@Override
	public void save(ProductRedemption redemption) {
		session.save(redemption);
	}
}