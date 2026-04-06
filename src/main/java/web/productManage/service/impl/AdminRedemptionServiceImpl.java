package web.productManage.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.productManage.dao.AdminRedemptionDao;
import web.productManage.dto.RedemptionDetailDto;
import web.productManage.dto.RedemptionSummaryDto;
import web.productManage.service.AdminRedemptionService;

@Service
@Transactional
public class AdminRedemptionServiceImpl implements AdminRedemptionService {

	@Autowired
	private AdminRedemptionDao redemptionDao;

	@Override
	public List<RedemptionSummaryDto> getSummary() {
		return redemptionDao.findSummary();
	}

	@Override
	public List<RedemptionDetailDto> getDetail(Long productId, String empName, Long statusId,
			String startDate, String endDate) {

		Timestamp startTs = parseStartDate(startDate);
		Timestamp endTs = parseEndDate(endDate);

		return redemptionDao.findDetail(productId, empName, statusId, startTs, endTs);
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
