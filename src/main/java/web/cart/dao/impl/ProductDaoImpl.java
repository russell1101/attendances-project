package web.cart.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import core.pojo.Product;
import web.cart.dao.ProductDao;

public class ProductDaoImpl implements ProductDao {
	private DataSource ds;

	public ProductDaoImpl() throws NamingException {
		ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/attendances");
	}

	// 取得所有商品
	@Override
	public List<Product> getAllProducts() {
		String sql = "SELECT * FROM products";
		List<Product> productList = new ArrayList<Product>();

		try (Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();) {
			while (rs.next()) {
				Product product = new Product();
				product.setProductId(rs.getLong("product_id"));
				product.setProductName(rs.getString("product_name"));
				product.setDescription(rs.getString("description"));
				product.setStock(rs.getInt("stock"));
				product.setRequiredPoints(rs.getBigDecimal("required_points"));
				product.setImageData(rs.getBytes("image_data"));
				product.setVersion(rs.getInt("version"));
				product.setReleasedAt(rs.getTimestamp("released_at"));
				product.setRemovedAt(rs.getTimestamp("removed_at"));
				product.setCreatedAt(rs.getTimestamp("created_at"));
				product.setUpdatedAt(rs.getTimestamp("updated_at"));

				int days = rs.getInt("valid_days");
				if (rs.wasNull()) {
					product.setValidDays(null);
				} else {
					product.setValidDays(days);
				}

				productList.add(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return productList;
	}

	// 一般查詢商品
	@Override
	public Product selectById(Connection conn, Long productId) {
		String sql = "SELECT * FROM products WHERE product_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setLong(1, productId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					Product p = new Product();
					p.setProductId(rs.getLong("product_id"));
					p.setProductName(rs.getString("product_name"));
					p.setStock(rs.getInt("stock"));
					p.setRequiredPoints(rs.getBigDecimal("required_points"));
					p.setValidDays(rs.getInt("valid_days")); // 取得有效天數
					if (rs.wasNull())
						p.setValidDays(null);
					return p;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 扣商品
	@Override
	public int deductStock(Connection conn, Long productId, int qty) {
		String sql = "UPDATE products SET stock = stock - ? WHERE product_id = ? AND stock >= ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, qty);
			pstmt.setLong(2, productId);
			pstmt.setInt(3, qty);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

}
