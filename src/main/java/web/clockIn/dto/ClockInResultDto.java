package web.clockIn.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClockInResultDto {
	private String status; // 準時/遲到
	private BigDecimal pointsChanged; // 異動點數
}