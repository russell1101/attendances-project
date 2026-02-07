package web.cart.bean;

import java.math.BigDecimal;

public class BuyResult {

	private BigDecimal currentPoints;
	private Integer currentStock;
	private String message;

	public BuyResult(BigDecimal currentPoints, Integer currentStock) {
		this.currentPoints = currentPoints;
		this.currentStock = currentStock;
		this.message = "兌換成功！禮券已發送至您的票夾";
	}

	public BigDecimal getCurrentPoints() {
		return currentPoints;
	}

	public void setCurrentPoints(BigDecimal currentPoints) {
		this.currentPoints = currentPoints;
	}

	public Integer getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(Integer currentStock) {
		this.currentStock = currentStock;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
