package web.productManage.dao;

import java.sql.Timestamp;
import java.util.List;

import web.productManage.dto.RedemptionDetailDto;
import web.productManage.dto.RedemptionSummaryDto;

public interface AdminRedemptionDao {

	List<RedemptionSummaryDto> findSummary();

	List<RedemptionDetailDto> findDetail(Long productId, String empName, Long statusId, Timestamp startDate, Timestamp endDate);
}
