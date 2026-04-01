package web.cart.dao;

import java.sql.Timestamp;
import java.util.List;

import core.entity.GiftCard;

public interface CartGiftCardDao {

	void save(GiftCard giftCard);

	List<GiftCard> findByEmployeeId(Long employeeId);

	GiftCard findById(Long id);

	List<Object[]> findGiftCardsWithImageByEmployeeId(Long employeeId);

	int updateExpiredGiftCards(Timestamp now);

}
