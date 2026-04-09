package core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
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
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "department_id")
	private Long departmentId;

	@Column(name = "department_name", unique = true, nullable = false)
	private String departmentName;

	@JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
	@Column(name = "work_start_time", nullable = false)
	private Time workStartTime;

	@JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
	@Column(name = "work_end_time", nullable = false)
	private Time workEndTime;

	@Column(name = "on_time_bonus_points", nullable = false)
	private BigDecimal onTimeBonusPoints;

	@Column(name = "late_penalty_points", nullable = false)
	private BigDecimal latePenaltyPoints;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "created_at", insertable = false, updatable = false)
	private Timestamp createdAt;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "updated_at", insertable = false, updatable = false)
	private Timestamp updatedAt;
	
	@Column(name = "is_active", nullable = false)
	private Boolean isActive;
}