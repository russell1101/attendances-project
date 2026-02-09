package web.chart.controller;

import java.io.IOException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import core.pojo.Department;
import web.chart.service.ChartService;
import web.chart.service.impl.ChartServiceImpl;

@WebServlet("/getDepts")
public class GetDepController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ChartService service;

    @Override
    public void init() throws ServletException {
        try {
            service = new ChartServiceImpl();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Department> depts = service.getDeptOptions();
        Gson gson = new Gson();
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write(gson.toJson(depts));
    }
}