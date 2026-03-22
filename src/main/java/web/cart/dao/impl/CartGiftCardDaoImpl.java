package web.cart.dao.impl;

import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import core.entity.GiftCard;
import web.cart.dao.CartGiftCardDao;

@Repository
public class CartGiftCardDaoImpl implements CartGiftCardDao {
	@PersistenceContext
	private Session session;

	@Override
	public void save(GiftCard giftCard) {
		session.save(giftCard);
	}

	@Override
	public List<GiftCard> findByEmployeeId(Long employeeId) {
		String hql = "FROM GiftCard WHERE employeeId = :empId ORDER BY exchangedAt DESC";
		return session.createQuery(hql, GiftCard.class).setParameter("empId", employeeId).getResultList();
	}
}