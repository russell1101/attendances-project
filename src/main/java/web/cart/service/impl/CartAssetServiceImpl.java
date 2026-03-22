package web.cart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		List<GiftCard> giftCardList = giftCardDao.findByEmployeeId(employeeId);
		List<GiftCardDto> dtoList = new ArrayList<>();

		for (GiftCard gc : giftCardList) {
			GiftCardDto dto = new GiftCardDto();
			BeanUtils.copyProperties(gc, dto);

			// 塞入enum status
			for (GiftCardStatusEnum statusEnum : GiftCardStatusEnum.values()) {
				if (statusEnum.getId().equals(gc.getGiftCardStatusId())) {
					dto.setStatus(statusEnum.name());
					break;
				}
			}

			dtoList.add(dto);
		}

		// 回傳
		UserAssetDto result = new UserAssetDto();
		result.setCurrentPoints(employee.getCurrentPoints());
		result.setGiftCards(dtoList);

		return result;
	}
}