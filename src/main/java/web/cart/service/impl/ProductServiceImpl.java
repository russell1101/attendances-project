package web.cart.service.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

import javax.naming.NamingException;

import core.pojo.Employee;
import core.pojo.Product;
import core.util.DBConnection;
import web.cart.bean.BuyResult;
import web.cart.dao.EmployeeDao;
import web.cart.dao.ProductDao;
import web.cart.dao.impl.EmployeeDaoImpl;
import web.cart.dao.impl.ProductDaoImpl;
import web.cart.service.ProductService;

public class ProductServiceImpl implements ProductService {
	private ProductDao productDao;
	private EmployeeDao employeeDao;

	public ProductServiceImpl() throws NamingException {
		productDao = new ProductDaoImpl();
		employeeDao = new EmployeeDaoImpl();
	}

	// 取得所有商品
	@Override
	public List<Product> getAllProducts() {
		List<Product> productList = productDao.getAllProducts();
		return productList;
	}

	// 購買method
	@Override
	public BuyResult buyProduct(Long empId, Long prodId, Integer qty) throws Exception {

		try (Connection conn = DBConnection.getConnection()) {
			// 準備開始交易控制
			conn.setAutoCommit(false);

			try {
				if (qty <= 0) {
					throw new Exception("購買數量必須大於 0");
				}

				Product product = productDao.selectById(conn, prodId);
				if (product == null)
					throw new Exception("找不到該商品");

				// multiply代表乘
				BigDecimal cost = product.getRequiredPoints().multiply(new BigDecimal(qty));

				int empUpdateCount = employeeDao.deductPoints(conn, empId, cost);
				if (empUpdateCount == 0) {
					throw new Exception("點數不足或扣款失敗");
				}

				int prodUpdateCount = productDao.deductStock(conn, prodId, qty);
				if (prodUpdateCount == 0) {
					throw new Exception("庫存不足，晚了一步被搶光了");
				}

				// 全部成功，提交交易
				conn.commit();

				// 查找最新狀態之 員工/有異動之商品
				Employee updatedEmp = employeeDao.selectById(conn, empId);
				Product updatedProd = productDao.selectById(conn, prodId);

				return new BuyResult(updatedEmp.getCurrentPoints(), updatedProd.getStock());

			} catch (Exception e) {
				// 任一 發生錯誤 回滾
				conn.rollback();
				e.printStackTrace();
				throw e;
			}
		}
	}

}
