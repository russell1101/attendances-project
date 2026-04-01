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

	@Override
	public GiftCard findById(Long id) {
		return session.get(GiftCard.class, id);
	}

	@Override
	public List<Object[]> findGiftCardsWithImageByEmployeeId(Long employeeId) {
		String hql = "SELECT gc, p.imageData " + "FROM GiftCard gc "
				+ "JOIN ProductRedemption pr ON gc.redemptionId = pr.redemptionId "
				+ "JOIN Product p ON pr.productId = p.productId " + "WHERE gc.employeeId = :employeeId";

		return session.createQuery(hql, Object[].class).setParameter("employeeId", employeeId).getResultList();
	}

	@Override
	public int updateExpiredGiftCards(java.sql.Timestamp now) {
		String hql = "UPDATE GiftCard g SET g.giftCardStatusId = 3 "
				+ "WHERE g.giftCardStatusId = 1 AND g.expiresAt < :now";

		return session.createQuery(hql).setParameter("now", now).executeUpdate();
	}
}