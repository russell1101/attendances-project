package web.productManage.dto;

import lombok.Data;

@Data
public class GlobalSettingDto {
	private String onTimeBonus;  // 對應 GLOBAL_ON_TIME_BONUS
	private String latePenalty;  // 對應 GLOBAL_LATE_PENALTY
}
