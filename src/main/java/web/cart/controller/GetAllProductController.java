package web.cart.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import core.pojo.Product;
import core.util.ProductApiResponse;
import web.cart.service.ProductService;
import web.cart.service.impl.ProductServiceImpl;

@WebServlet("/product/getAll")
public class GetAllProductController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	ProductService productService;

	@Override
	public void init() throws ServletException {
		try {
			productService = new ProductServiceImpl();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		Gson gson = new Gson();

		ProductApiResponse<List<JsonObject>> apiResponse = new ProductApiResponse<>();

		try {
			List<Product> productList = productService.getAllProducts();

			// 先將 圖片欄位位元組資料 轉為base64
			List<JsonObject> modifiedList = new ArrayList<>();
			for (Product p : productList) {
				JsonObject item = gson.toJsonTree(p).getAsJsonObject();
				if (item.has("imageData")) {
					item.remove("imageData");
				}

				if (p.getImageData() != null && p.getImageData().length > 0) {
					String base64 = Base64.getEncoder().encodeToString(p.getImageData());
					item.addProperty("imageData", "data:image/jpeg;base64," + base64);
				} else {
					item.add("imageData", null);
				}

				modifiedList.add(item);
			}

			apiResponse.setSuccess(1);
			apiResponse.setErrMsg("");
			apiResponse.setData(modifiedList);
		} catch (Exception e) {
			// 錯誤時印出給前端
			e.printStackTrace();
			apiResponse.setSuccess(-1);
			apiResponse.setErrMsg("系統錯誤: " + e.getMessage());
			apiResponse.setData(null);
		}

		out.write(gson.toJson(apiResponse));
	}
}
