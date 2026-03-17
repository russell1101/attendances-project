package core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_redemptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRedemption implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "redemption_id")
	private Long redemptionId;

	@Column(name = "employee_id", nullable = false)
	private Long employeeId;

	@Column(name = "product_id", nullable = false)
	private Long productId;

	@Column(name = "quantity", nullable = false)
	private Integer quantity;

	@Column(name = "points_spent", nullable = false)
	private BigDecimal pointsSpent;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "redeemed_at", insertable = false, updatable = false)
	private Timestamp redeemedAt;

}