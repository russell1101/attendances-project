package core.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import core.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private int employeeId;

    @Column(name ="name")
    private String name;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "password_hash", insertable = false,updatable = false)
    private String passwordHash;
    
    @Column(name = "google_sub", insertable = false,updatable = false)
    private String googleSub;
	
	@Column(name = "hire_date", insertable = false,updatable = false)
	private Date hireDate;
	
	@Column(name = "current_points", insertable = false,updatable = false)
	private Integer currentPoints;
	
	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;
	
	@Column(name = "employee_status_id", insertable = false,updatable = false)
	private Integer employeeStatusId;
	
	@Column(name = "is_active", insertable = false,updatable = false)
	private boolean	isActive;
	
	@Column(name = "created_at", insertable = false,updatable = false)
	private Timestamp createdAt;
	
	@Column(name = "updated_at", insertable = false,updatable = false)
	private Timestamp updatedAt;
}
