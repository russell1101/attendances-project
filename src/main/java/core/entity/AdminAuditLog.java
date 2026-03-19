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
@Table(name = "admin_audit_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminAuditLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "admin_logs_id")
	private Long adminLogsId;

	@Column(name = "admin_user_id")
	private Long adminUserId;

	@Column(name = "action")
	private String action;

	@Column(name = "target_type")
	private String targetType;

	@Column(name = "target_id")
	private Long targetId;

	@Column(name = "summary")
	private String summary;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "created_at", insertable = false, updatable = false)
	private Timestamp createdAt;

}