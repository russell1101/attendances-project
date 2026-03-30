package core.aop;

import java.lang.reflect.Method;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import core.annotation.DbOperation;
import core.entity.AdminUser;
import core.entity.Employee;



@Component
@Aspect
public class DbAuditAspect {

	private static final Logger auditLog = LogManager.getLogger("DatabaseAuditLogger");

	@AfterReturning("@annotation(core.annotation.DbOperation)")
	public void logDatabaseOperation(JoinPoint joinPoint) {
		try {

			MethodSignature signature = (MethodSignature) joinPoint.getSignature();
			Method method = signature.getMethod();

			DbOperation dbOp = AnnotationUtils.findAnnotation(method, DbOperation.class);
			if (dbOp == null) {
				Method targetMethod = joinPoint.getTarget().getClass().getMethod(method.getName(),
						method.getParameterTypes());
				dbOp = AnnotationUtils.findAnnotation(targetMethod, DbOperation.class);
			}

			if (dbOp == null) {
				return;
			}

			String actionName = dbOp.action();

			// 取得執行的方法與傳入的參數
			String className = joinPoint.getTarget().getClass().getSimpleName();
			String methodName = joinPoint.getSignature().getName();
			String arguments = Arrays.toString(joinPoint.getArgs());

			// 從 Session 取得當前操作者
			String operator = "系統/未登入";
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes();
			if (attributes != null) {
				HttpServletRequest request = attributes.getRequest();
				HttpSession session = request.getSession(false);
				if (session != null && session.getAttribute("employee") != null) {
					Employee emp = (Employee) session.getAttribute("employee");
					operator = "員工 ID: " + emp.getEmployeeId();
				} else if (session != null && session.getAttribute("adminUser") != null) {
					AdminUser emp = (AdminUser) session.getAttribute("adminUser");
					operator = "後台操作者 ID: " + emp.getAdminUserId();
				}
			}

			// 組裝訊息
			String logMessage = String.format("操作者: [%s] | 動作: [%s] | 方法: %s.%s() | 參數: %s", operator, actionName,
					className, methodName, arguments);
			auditLog.info(logMessage);
		} catch (Exception e) {
			System.err.println("【Audit AOP】記錄失敗: " + e.getMessage());
			e.printStackTrace();
		}
	}
}