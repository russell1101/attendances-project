package web.clockIn.dto;

import core.enums.AttendanceStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AttendanceHistoryDto {
	private String workDate;
	private String clockInTime;
	private AttendanceStatus clockInStatus;
	private String clockOutTime;
	private AttendanceStatus clockOutStatus;
	private Integer pointsAwarded;
}