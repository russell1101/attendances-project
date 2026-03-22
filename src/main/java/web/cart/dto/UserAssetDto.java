package web.cart.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class UserAssetDto {
	private BigDecimal currentPoints;
	private List<GiftCardDto> giftCards;
}