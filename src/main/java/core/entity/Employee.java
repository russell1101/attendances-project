package core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_id")
	private Long employeeId;
	
	@ManyToOne
	@JoinColumn(name = "department_id", insertable = false, updatable = false)
	private Department department;
	
	@ManyToOne
	@JoinColumn(name = "employee_status_id", insertable = false, updatable = false)
	private EmployeeStatus employeeStatus;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password_hash",updatable = false)
	private String passwordHash;

	@Column(name = "google_sub", unique = true)
	private String googleSub;

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@Column(name = "hire_date", nullable = false)
	private Date hireDate;

	@Column(name = "current_points", nullable = false)
	private BigDecimal currentPoints;

	@Column(name = "department_id", nullable = false)
	private Long departmentId;

	@Column(name = "employee_status_id", nullable = false)
	private Long employeeStatusId;

	@Column(name = "is_active", nullable = false)
	private Boolean isActive;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "created_at", insertable = false, updatable = false)
	private Timestamp createdAt;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "updated_at", insertable = false, updatable = false)
	private Timestamp updatedAt;
}