package core.util;

public class ProductApiResponse<T> {
	private Integer success; // 1:成功, 以外:失敗
	private String errMsg; // 錯誤訊息
	private T data; // 資料

	public ProductApiResponse() {
	};

	public ProductApiResponse(Integer success, String errmsg, T data) {
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

}
