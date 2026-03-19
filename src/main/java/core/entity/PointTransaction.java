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
@Table(name = "point_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PointTransaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "point_txn_id")
	private Long pointTxnId;

	@Column(name = "employee_id", nullable = false)
	private Long employeeId;

	@Column(name = "points_update", nullable = false)
	private BigDecimal pointsUpdate;

	@Column(name = "reason", nullable = false)
	private String reason;

	@Column(name = "related_type")
	private String relatedType;

	@Column(name = "related_id")
	private Long relatedId;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "created_at", insertable = false, updatable = false)
	private Timestamp createdAt;

}