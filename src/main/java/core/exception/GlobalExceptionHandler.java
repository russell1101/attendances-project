package core.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import core.util.ApiResponse;

@RestControllerAdvice // 包含responsebody
public class GlobalExceptionHandler {
	private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(BusinessException.class)
	public ApiResponse<Void> handleBusinessException(BusinessException e) {
		logger.warn("業務邏輯阻擋: {}", e.getMessage());

		// 把組員寫的錯誤訊息，包裝進 ApiResponse 退回給前端
		return ApiResponse.error(e.getMessage());
	}

	// 處理系統預期外的嚴重錯誤
	@ExceptionHandler(Exception.class)
	public ApiResponse<Void> handleAllExceptions(Exception e) {
		logger.error("系統未預期錯誤", e);

		return ApiResponse.error("系統繁忙中，請稍後再試");
	}
}