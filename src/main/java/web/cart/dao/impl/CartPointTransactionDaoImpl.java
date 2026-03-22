package web.cart.dao.impl;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import core.entity.PointTransaction;
import web.cart.dao.CartPointTransactionDao;

@Repository
public class CartPointTransactionDaoImpl implements CartPointTransactionDao {
	@PersistenceContext
	private Session session;

	@Override
	public void save(PointTransaction transaction) {
		session.save(transaction);
	}
}