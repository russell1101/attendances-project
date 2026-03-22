package web.cart.dao;

import java.util.List;

import core.entity.GiftCard;

public interface CartGiftCardDao {

	void save(GiftCard giftCard);

	List<GiftCard> findByEmployeeId(Long employeeId);

}
