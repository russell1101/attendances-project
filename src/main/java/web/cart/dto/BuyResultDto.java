package web.cart.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyResultDto {
	private BigDecimal currentPoints;
	private Integer currentStock;
	private String message;
}