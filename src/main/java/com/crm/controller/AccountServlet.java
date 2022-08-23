package com.crm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crm.dao.AccountDao;
import com.crm.model.Account;
import com.crm.uri.UriJsp;
import com.crm.uri.UriServlet;
import com.google.gson.Gson;
import com.mysql.cj.jdbc.NonRegisteringDriver;

@WebServlet(urlPatterns = { UriServlet.ACCOUNT, UriServlet.ACCOUNT_ADD, UriServlet.ACCOUNT_DELETE,
		UriServlet.ACCOUNT_EDIT })
public class AccountServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static AccountDao accountDao;
	private static ArrayList<Account> accounts;
	
	@Override
	public void init() throws ServletException {	
		
		super.init();
		accountDao = new AccountDao(); // new fix 12:26 
		
	}
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String action = req.getServletPath();
		System.out.println("action " + action);

		switch (action) {
		case UriServlet.ACCOUNT:
			processViews(req, resp);

			break;
		case UriServlet.ACCOUNT_EDIT:
			getEdit(req, resp);
			break;
		case UriServlet.ACCOUNT_DELETE:
			getDelete(req, resp);
			break;
		}

	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		System.out.println("from post: " + action);
		
		switch (action) {
		case UriServlet.ACCOUNT_EDIT:
			doEdit(req, resp);
			break;
		case UriServlet.ACCOUNT_ADD:
			doAdd(req, resp);
			break;
		}
	}

	private void processViews(HttpServletRequest req, HttpServletResponse resp) {
		try {
			String keyword = req.getParameter("keyword");
			String id = req.getParameter("pageid");
			String limitString = req.getParameter("limit");
			int pageid = 1;
			int limit = 20;
			
			if (keyword == null) {
				keyword = "";
			} 
			
			if(id != null  && !id.isEmpty()) {
				pageid = Integer.parseInt(id);
			}
			
			if(limitString != null  && !limitString.isEmpty()) {
				limit = Integer.parseInt(limitString);
			}
			
			int index = (pageid - 1) * limit;
			
			System.out.println("indexpage" + index);
			accounts = accountDao.search(keyword,index,limit);
			
			int totalPage = (int) Math.ceil((float)accountDao.getTotalRecord(keyword)/(float)limit);			
			req.setAttribute("totalRecord", accountDao.getTotalRecord(keyword));
			req.setAttribute("pageid", pageid);
			req.setAttribute("totalPage", totalPage);
			req.setAttribute("viewsAccount",accounts);
			req.getRequestDispatcher(UriJsp.ACCOUNT).forward(req, resp);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getEdit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {

			String account_id = req.getParameter("account_id");
			System.out.println("account_id " + account_id);

			if (account_id != null && !account_id.isEmpty()) {
				Account account = accountDao.getById(Integer.parseInt(account_id));
				PrintWriter out = resp.getWriter();
				resp.setContentType("application/json");
				Gson gson = new Gson();
				String objectReturn = gson.toJson(account);
				out.write(objectReturn);
				out.flush();
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			Account account = new Account();
			account.setFullname(req.getParameter("fullname"));
			account.setEmail(req.getParameter("email"));
			account.setPassword(req.getParameter("password"));
			account.setPhone(req.getParameter("phone"));
			account.setAddress(req.getParameter("address"));
			String account_id = req.getParameter("account_id");
			System.out.println(account_id);
			if (account_id != null && !account_id.isEmpty()) {
				account.setAccount_id(Integer.parseInt(account_id));
				accountDao.update(account);
				resp.sendRedirect(req.getContextPath() + UriServlet.ACCOUNT);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Account account = new Account();
		account.setFullname(req.getParameter("fullName"));
		account.setEmail(req.getParameter("email"));
		account.setPassword(req.getParameter("password"));
		account.setPhone(req.getParameter("phone"));
		account.setAddress(req.getParameter("address"));
		try {
			accountDao.insert(account);
			resp.sendRedirect(req.getContextPath() + UriServlet.ACCOUNT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void getDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			int id = Integer.valueOf((String) req.getParameter("id"));
			accountDao.delete(id);
			resp.sendRedirect(req.getContextPath() + UriServlet.ACCOUNT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
