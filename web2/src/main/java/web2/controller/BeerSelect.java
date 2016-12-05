package web2.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web2.model.BeerExpert;
import web2.model.Expert;

public class BeerSelect extends HttpServlet{


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String color = req.getParameter("color");
		Expert be = new BeerExpert();
		List<String> lst = be.getBrands(color);
		
//		resp.setContentType("text/html");
//		PrintWriter out = resp.getWriter();
//		out.println("Beer Selection Advice<br>");
		
		req.setAttribute("styles", lst);
		
//		for(String s : lst) {
//			out.println("<br>try: " + s);
//		}
		
		RequestDispatcher view = req.getRequestDispatcher("result.jsp");
		view.forward(req, resp);
		
	}
	
	

}
