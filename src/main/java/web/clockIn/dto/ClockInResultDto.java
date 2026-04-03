package web.clockIn.dto;

import java.math.BigDecimal;

import core.enums.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClockInResultDto {
	private AttendanceStatus status; // 準時/遲到
	private BigDecimal pointsChanged; // 異動點數
	private Long lateMinutes;
}