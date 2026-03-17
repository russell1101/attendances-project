package core.entity;

import java.io.Serializable;
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
@Table(name = "gift_cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GiftCard implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "gift_card_id")
	private Long giftCardId;

	@Column(name = "gift_name", nullable = false)
	private String giftName;

	@Column(name = "employee_id", nullable = false)
	private Long employeeId;

	@Column(name = "redemption_id")
	private Long redemptionId;

	@Column(name = "gift_card_status_id", nullable = false)
	private Long giftCardStatusId;

	@Column(name = "gift_code", nullable = false, unique = true)
	private String giftCode;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "exchanged_at", insertable = false, updatable = false)
	private Timestamp exchangedAt;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "used_at")
	private Timestamp usedAt;

	// 可為null 無限期
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "expires_at")
	private Timestamp expiresAt;

}