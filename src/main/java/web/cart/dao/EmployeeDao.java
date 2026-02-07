package web.cart.dao;

import java.math.BigDecimal;
import java.sql.Connection;

import core.pojo.Employee;

public interface EmployeeDao {
	Employee selectById(Long id);

	Employee selectById(Connection conn, Long employeeId);

	int deductPoints(Connection conn, Long employeeId, BigDecimal cost);
}
