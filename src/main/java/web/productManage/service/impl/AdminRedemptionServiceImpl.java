package web.productManage.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import core.enums.GiftCardStatusEnum;
import web.productManage.dao.AdminRedemptionDao;
import web.productManage.dto.RedemptionDetailDto;
import web.productManage.dto.RedemptionSummaryDto;
import web.productManage.service.AdminRedemptionService;

@Service
@Transactional
public class AdminRedemptionServiceImpl implements AdminRedemptionService {

	@Autowired
	private AdminRedemptionDao redemptionDao;

	// 商品兌換彙總
	@Override
	public List<RedemptionSummaryDto> getSummary() {
		List<Object[]> rows = redemptionDao.findSummary();
		List<RedemptionSummaryDto> result = new ArrayList<>();

		for (Object[] row : rows) {
			RedemptionSummaryDto dto = new RedemptionSummaryDto();
			dto.setProductId(toLong(row[0]));
			dto.setProductName((String) row[1]);
			dto.setStock(row[2] != null ? ((Number) row[2]).intValue() : 0);
			dto.setRemovedAt((Timestamp) row[3]);
			dto.setTotalIssued(toLong(row[4]));
			dto.setAvailableCount(toLong(row[5]));
			dto.setUsedCount(toLong(row[6]));
			dto.setExpiredCount(toLong(row[7]));
			result.add(dto);
		}

		return result;
	}

	// 單一商品兌換明細
	@Override
	public List<RedemptionDetailDto> getDetail(Long productId, String empName, Long statusId, String startDate, String endDate) {
		Timestamp startTs = parseStartDate(startDate);
		Timestamp endTs = parseEndDate(endDate);

		List<Object[]> rows = redemptionDao.findDetail(productId, empName, statusId, startTs, endTs);
		List<RedemptionDetailDto> result = new ArrayList<>();

		for (Object[] row : rows) {
			RedemptionDetailDto dto = new RedemptionDetailDto();
			dto.setGiftCardId(toLong(row[0]));
			dto.setGiftCode((String) row[1]);
			dto.setGiftName((String) row[2]);
			dto.setEmployeeName((String) row[3]);

			// giftCardStatusId → enum name (AVAILABLE / USED / EXPIRED)
			Long statusEnumId = toLong(row[4]);
			dto.setStatus(resolveStatus(statusEnumId));

			dto.setExchangedAt((Timestamp) row[5]);
			dto.setUsedAt((Timestamp) row[6]);
			dto.setExpiresAt((Timestamp) row[7]);
			dto.setPointsSpent(row[8] != null ? new java.math.BigDecimal(row[8].toString()) : null);
			result.add(dto);
		}

		return result;
	}

	// ===================== 工具方法 =====================

	private Long toLong(Object val) {
		if (val == null) return 0L;
		return ((Number) val).longValue();
	}

	private String resolveStatus(Long statusId) {
		if (statusId == null) return "UNKNOWN";
		for (GiftCardStatusEnum e : GiftCardStatusEnum.values()) {
			if (e.getId().equals(statusId)) return e.name();
		}
		return "UNKNOWN";
	}

	private Timestamp parseStartDate(String date) {
		if (date == null || date.trim().isEmpty()) return null;
		return Timestamp.valueOf(date.trim() + " 00:00:00");
	}

	private Timestamp parseEndDate(String date) {
		if (date == null || date.trim().isEmpty()) return null;
		return Timestamp.valueOf(date.trim() + " 23:59:59");
	}
}
