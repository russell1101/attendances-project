package web.productManage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import web.productManage.dto.RedemptionDetailDto;
import web.productManage.dto.RedemptionSummaryDto;

@Service
public interface AdminRedemptionService {

	List<RedemptionSummaryDto> getSummary();

	List<RedemptionDetailDto> getDetail(Long productId, String empName, Long statusId, String startDate, String endDate);
}
