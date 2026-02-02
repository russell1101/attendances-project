package web.chart.controller;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.chart.service.ChartService;
import web.chart.service.impl.ChartServiceImpl;

@WebServlet()
public class ChartController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ChartService service;
	
	@Override
	public void init() throws ServletException {
//		try {
//		
//		} catch (NamingException e) {
//			e.printStackTrace();
//		}
	}
	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		 
		
	}
}
