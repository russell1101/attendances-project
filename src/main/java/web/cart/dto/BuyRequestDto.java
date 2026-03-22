package web.cart.dto;

import lombok.Data;

@Data
public class BuyRequestDto {
	private Long productId;
	private Integer qty;
}