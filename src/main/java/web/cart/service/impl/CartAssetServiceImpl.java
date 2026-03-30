package web.cart.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import core.annotation.DbOperation;
import core.entity.Employee;
import core.entity.GiftCard;
import core.enums.GiftCardStatusEnum;
import core.exception.BusinessException;
import web.cart.dao.CartEmployeeDao;
import web.cart.dao.CartGiftCardDao;
import web.cart.dto.GiftCardDto;
import web.cart.dto.UserAssetDto;
import web.cart.service.CartAssetService;

@Service
@Transactional
public class CartAssetServiceImpl implements CartAssetService {

	@Autowired
	private CartEmployeeDao employeeDao;
	@Autowired
	private CartGiftCardDao giftCardDao;

	@Override
	public UserAssetDto getUserAssets(Long employeeId) {
		// 取得員工當前點數
		Employee employee = employeeDao.findById(employeeId);
		if (employee == null) {
			throw new BusinessException("查無個人資訊");
		}

		// 取得所有禮物券
		List<Object[]> results = giftCardDao.findGiftCardsWithImageByEmployeeId(employeeId);
		List<GiftCardDto> dtoList = new ArrayList<>();

		for (Object[] row : results) {
			GiftCard gc = (GiftCard) row[0];
			byte[] imageData = (byte[]) row[1];

			GiftCardDto dto = new GiftCardDto();
			BeanUtils.copyProperties(gc, dto);

			for (GiftCardStatusEnum statusEnum : GiftCardStatusEnum.values()) {
				if (statusEnum.getId().equals(gc.getGiftCardStatusId())) {
					dto.setStatus(statusEnum.name());
					break;
				}
			}

			// 轉base64
			if (imageData != null && imageData.length > 0) {
				String base64String = Base64.getEncoder().encodeToString(imageData);
				dto.setProductImageBase64("data:image/jpeg;base64," + base64String);
			}

			dtoList.add(dto);
		}

		// 回傳
		UserAssetDto result = new UserAssetDto();
		result.setCurrentPoints(employee.getCurrentPoints());
		result.setGiftCards(dtoList);

		return result;
	}

	@Override
	@DbOperation(action = "員工兌換禮券") // 寫入log
	public void exchangeGiftCard(Long employeeId, Long cardId) {
		GiftCard giftCard = giftCardDao.findById(cardId);

		if (!giftCard.getEmployeeId().equals(employeeId)) {
			throw new BusinessException("非本人禮券，無法兌換");
		}

		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		if (giftCard.getExpiresAt() != null && currentTime.after(giftCard.getExpiresAt())) {
			throw new BusinessException("禮券已過期，無法兌換");
		}

		if (giftCard.getGiftCardStatusId().equals(GiftCardStatusEnum.USED.getId())) {
			throw new BusinessException("禮券已兌換");
		}

		giftCard.setGiftCardStatusId(GiftCardStatusEnum.USED.getId());
		giftCard.setUsedAt(currentTime);

		giftCardDao.save(giftCard);
	}

	// 排程 批次更新過期兌換券
	@Override
	@DbOperation(action = "系統更新過期禮券")
	public void processExpiredGiftCards() {
		java.sql.Timestamp now = new java.sql.Timestamp(System.currentTimeMillis());

		giftCardDao.updateExpiredGiftCards(now);
	}

}