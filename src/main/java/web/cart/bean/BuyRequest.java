package web.cart.bean;

//購買請求物件
public class BuyRequest {
	private Long employeeId;
	private Long productId;
	private Integer qty;

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long id) {
		id = employeeId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

}