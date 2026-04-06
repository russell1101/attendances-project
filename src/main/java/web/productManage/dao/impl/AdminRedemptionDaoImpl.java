package web.productManage.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import web.productManage.dao.AdminRedemptionDao;
import web.productManage.dto.RedemptionDetailDto;
import web.productManage.dto.RedemptionSummaryDto;

@Repository
public class AdminRedemptionDaoImpl implements AdminRedemptionDao {

	@PersistenceContext
	private Session session;

	@Override
	public List<RedemptionSummaryDto> findSummary() {
		String hql = "SELECT NEW web.productManage.dto.RedemptionSummaryDto("
				+ "  p.productId, p.productName, p.stock, p.removedAt,"
				+ "  COUNT(gc.giftCardId),"
				+ "  SUM(CASE WHEN gc.giftCardStatusId = 1 THEN 1 ELSE 0 END),"
				+ "  SUM(CASE WHEN gc.giftCardStatusId = 2 THEN 1 ELSE 0 END),"
				+ "  SUM(CASE WHEN gc.giftCardStatusId = 3 THEN 1 ELSE 0 END)"
				+ ") "
				+ "FROM Product p "
				+ "LEFT JOIN ProductRedemption pr ON pr.productId = p.productId "
				+ "LEFT JOIN GiftCard gc ON gc.redemptionId = pr.redemptionId "
				+ "GROUP BY p.productId, p.productName, p.stock, p.removedAt, p.createdAt "
				+ "ORDER BY p.createdAt DESC";

		return session.createQuery(hql, RedemptionSummaryDto.class).getResultList();
	}

	@Override
	public List<RedemptionDetailDto> findDetail(Long productId, String empName, Long statusId,
			Timestamp startDate, Timestamp endDate) {

		StringBuilder hql = new StringBuilder(
				"SELECT NEW web.productManage.dto.RedemptionDetailDto("
				+ "  gc.giftCardId, gc.giftCode, gc.giftName, e.name, gc.giftCardStatusId,"
				+ "  gc.exchangedAt, gc.usedAt, gc.expiresAt, pr.pointsSpent"
				+ ") "
				+ "FROM GiftCard gc "
				+ "JOIN ProductRedemption pr ON gc.redemptionId = pr.redemptionId "
				+ "JOIN Employee e ON gc.employeeId = e.employeeId "
				+ "WHERE pr.productId = :productId");

		if (empName != null && !empName.trim().isEmpty()) {
			hql.append(" AND e.name LIKE :empName");
		}
		if (statusId != null) {
			hql.append(" AND gc.giftCardStatusId = :statusId");
		}
		if (startDate != null) {
			hql.append(" AND gc.exchangedAt >= :startDate");
		}
		if (endDate != null) {
			hql.append(" AND gc.exchangedAt <= :endDate");
		}
		hql.append(" ORDER BY gc.exchangedAt DESC");

		Query<RedemptionDetailDto> query = session.createQuery(hql.toString(), RedemptionDetailDto.class);
		query.setParameter("productId", productId);

		if (empName != null && !empName.trim().isEmpty()) {
			query.setParameter("empName", "%" + empName.trim() + "%");
		}
		if (statusId != null) {
			query.setParameter("statusId", statusId);
		}
		if (startDate != null) {
			query.setParameter("startDate", startDate);
		}
		if (endDate != null) {
			query.setParameter("endDate", endDate);
		}

		return query.getResultList();
	}
}
