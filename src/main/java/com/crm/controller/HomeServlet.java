package com.crm.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crm.connection.JDBCConection;
import com.crm.uri.UriJsp;
import com.crm.uri.UriServlet;

@WebServlet(name = "home" ,urlPatterns = {UriServlet.HOME})
public class HomeServlet  extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher(UriJsp.HOME).forward(req, resp);
		try (Connection conn = JDBCConection.getConnection()){
			if(conn != null) {
				System.out.println("connection thanhf coong ");
			}else {
				System.out.println("connection that bai");
			}
			conn.close();
			System.out.println(conn.isClosed());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
		}
		
	}
}
