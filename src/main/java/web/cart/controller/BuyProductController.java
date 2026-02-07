package web.cart.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import core.util.ProductApiResponse;
import web.cart.bean.BuyRequest;
import web.cart.bean.BuyResult;
import web.cart.service.ProductService;
import web.cart.service.impl.ProductServiceImpl;

@WebServlet("/product/buy")
public class BuyProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductService productService;

	@Override
	public void init() throws ServletException {
		try {
			productService = new ProductServiceImpl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 設定回應格式
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		Gson gson = new Gson();

		ProductApiResponse<BuyResult> apiResponse = new ProductApiResponse<>();

		try {
			BuyRequest reqData = gson.fromJson(req.getReader(), BuyRequest.class);

			if (reqData == null) {
				outputError(out, gson, apiResponse, "無效的請求資料");
				return;
			}
			if (reqData.getEmployeeId() == null) {
				outputError(out, gson, apiResponse, "缺少必要參數: employeeId");
				return;
			}
			if (reqData.getProductId() == null) {
				outputError(out, gson, apiResponse, "缺少必要參數: productId");
				return;
			}
			Integer qty = reqData.getQty();
			if (qty == null) {
				qty = 1;
			} else if (qty <= 0) {
				outputError(out, gson, apiResponse, "購買數量必須大於 0");
				return;
			}

			BuyResult result = productService.buyProduct(reqData.getEmployeeId(), reqData.getProductId(), qty);

			// 5. 成功
			apiResponse.setSuccess(1);
			apiResponse.setData(result);
			apiResponse.setErrMsg("");

		} catch (Exception e) {
			e.printStackTrace();
			apiResponse.setSuccess(-1);
			apiResponse.setErrMsg("系統錯誤: " + e.getMessage());
			apiResponse.setData(null);
		}

		// 5. 輸出
		out.write(gson.toJson(apiResponse));
	}
	
	// 固定打錯誤方法
	private void outputError(PrintWriter out, Gson gson, ProductApiResponse<BuyResult> apiResponse, String msg) {
		apiResponse.setSuccess(0);
		apiResponse.setErrMsg(msg);
		apiResponse.setData(null);
		out.write(gson.toJson(apiResponse));
	}
}
