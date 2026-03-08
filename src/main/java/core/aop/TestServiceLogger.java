package core.aop;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

// 測試用AOP類別 簡單追蹤service執行 上線可刪
@Component
@Aspect
public class TestServiceLogger {

	// ..表無限往下鑽(特例)
	@Before("execution(public * web..service.impl.*.*(..))")
	public void beforeServiceMethod(JoinPoint joinPoint) {

		String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

		// 執行中 之類別與方法名
		String className = joinPoint.getTarget().getClass().getSimpleName();
		String methodName = joinPoint.getSignature().getName();

		System.out.println("【AOP 服務追蹤】" + currentTime + " | 準備執行 Service: " + className + "." + methodName + "()");
	}
}