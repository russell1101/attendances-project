package web.clockIn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClockStatusDto {
	private Boolean hasClockedIn;
	private Boolean hasClockedOut;
	private String clockInTime;
	private String clockOutTime;
}