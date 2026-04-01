package web.cart.service;

import web.cart.dto.UserAssetDto;

public interface CartAssetService {

	UserAssetDto getUserAssets(Long employeeId);

	void exchangeGiftCard(Long employeeId, Long cardId);

	void processExpiredGiftCards();

}
