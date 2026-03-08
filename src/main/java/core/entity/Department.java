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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import core.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private int departmentId;

    @Column(name ="department_name")
    private String departmentName;
    
    @Column(name = "work_start_time")
    private Time workStartTime;
    
    @Column(name = "work_end_time")
    private Time workEndTime;
    
    @Column(name = "on_time_bonus_points", insertable = false,updatable = false)
    private Integer onTimeBonusPoints;
	
	@Column(name = "late_penalty_points", insertable = false,updatable = false)
	private Integer latePenaltyPoints;
	
	
	@Column(name = "created_at", insertable = false,updatable = false)
	private Timestamp createdAt;
	
	@Column(name = "updated_at", insertable = false,updatable = false)
	private Timestamp updatedAt;
}
