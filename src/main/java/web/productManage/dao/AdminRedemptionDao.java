package web.productManage.dao;

import java.sql.Timestamp;
import java.util.List;

public interface AdminRedemptionDao {

	// 商品級別彙總：每個商品的兌換券發出與使用狀況
	List<Object[]> findSummary();

	// 單一商品的兌換券明細，支援員工姓名、狀態、日期範圍篩選
	List<Object[]> findDetail(Long productId, String empName, Long statusId, Timestamp startDate, Timestamp endDate);
}
