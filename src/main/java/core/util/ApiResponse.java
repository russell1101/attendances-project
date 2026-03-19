package core.util;

public class ApiResponse<T> {
	private Integer success; // 1:成功, 以外:失敗
	private String errMsg; // 錯誤訊息
	private T data; // 資料

	public ApiResponse() {
	};

	public ApiResponse(Integer success, String errmsg, T data) {
		this.success = success;
		this.errMsg = errmsg;
		this.data = data;
	}

	public Integer getSuccess() {
		return success;
	}

	public void setSuccess(Integer success) {
		this.success = success;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	// 快速回傳區
	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>(1, null, data);
	}

	public static <T> ApiResponse<T> error(String errMsg) {
		return new ApiResponse<>(-1, errMsg, null);
	}
}
