package web.clockIn.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AttendanceHistoryDto {
    private String workDate;
    private String clockInTime;
    private String clockInStatus;
    private String clockOutTime;
    private String clockOutStatus;
    private Integer pointsAwarded;
}