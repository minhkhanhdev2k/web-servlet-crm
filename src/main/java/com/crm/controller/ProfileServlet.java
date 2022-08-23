package com.crm.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crm.dao.AccountDao;
import com.crm.dao.TaskDao;
import com.crm.model.Account;
import com.crm.model.Task;
import com.crm.uri.UriJsp;
import com.crm.uri.UriServlet;

@WebServlet (urlPatterns = {UriServlet.PROFILE})
public class ProfileServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private static TaskDao taskDao;
	private static AccountDao accountDao;
	private static ArrayList<Task> tasks;
	
	
	@Override
	public void init() throws ServletException {
		super.init();
		taskDao = new TaskDao();
		accountDao = new AccountDao();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String accountId = req.getParameter("id");
		Account account = new Account();
		if(accountId != null && !accountId.isEmpty()) {
			tasks = taskDao.getTaskByAccountId(Integer.parseInt(accountId));
			account = accountDao.getById(Integer.parseInt(accountId));
		}
		req.setAttribute("account", account);
		req.setAttribute("taskByAccountId", tasks);
		req.getRequestDispatcher(UriJsp.PROFILE).forward(req, resp);
	}

	
}
