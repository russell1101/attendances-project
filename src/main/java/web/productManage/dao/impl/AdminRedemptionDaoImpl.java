package web.productManage.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import web.productManage.dao.AdminRedemptionDao;

@Repository
public class AdminRedemptionDaoImpl implements AdminRedemptionDao {

	@PersistenceContext
	private Session session;

	// 彙總查詢：每個商品的兌換券發出數與各狀態數量
	@Override
	public List<Object[]> findSummary() {
		String hql = "SELECT p.productId, p.productName, p.stock, p.removedAt, "
				+ "COUNT(gc.giftCardId), "
				+ "SUM(CASE WHEN gc.giftCardStatusId = 1 THEN 1 ELSE 0 END), "
				+ "SUM(CASE WHEN gc.giftCardStatusId = 2 THEN 1 ELSE 0 END), "
				+ "SUM(CASE WHEN gc.giftCardStatusId = 3 THEN 1 ELSE 0 END) "
				+ "FROM Product p "
				+ "LEFT JOIN ProductRedemption pr ON pr.productId = p.productId "
				+ "LEFT JOIN GiftCard gc ON gc.redemptionId = pr.redemptionId "
				+ "GROUP BY p.productId, p.productName, p.stock, p.removedAt, p.createdAt "
				+ "ORDER BY p.createdAt DESC";

		return session.createQuery(hql, Object[].class).getResultList();
	}

	// 明細查詢：單一商品下所有兌換券，支援動態篩選
	@Override
	public List<Object[]> findDetail(Long productId, String empName, Long statusId, Timestamp startDate, Timestamp endDate) {
		StringBuilder hql = new StringBuilder(
				"SELECT gc.giftCardId, gc.giftCode, gc.giftName, e.name, gc.giftCardStatusId, "
				+ "gc.exchangedAt, gc.usedAt, gc.expiresAt, pr.pointsSpent "
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

		Query<Object[]> query = session.createQuery(hql.toString(), Object[].class);
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
