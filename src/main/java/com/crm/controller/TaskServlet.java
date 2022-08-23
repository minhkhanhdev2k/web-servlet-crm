package com.crm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.compiler.NewlineReductionServletWriter;

import com.crm.dao.AccountDao;
import com.crm.dao.ProjectDao;
import com.crm.dao.TaskDao;
import com.crm.model.Task;
import com.crm.uri.UriJsp;
import com.crm.uri.UriServlet;
import com.google.gson.Gson;

@WebServlet(urlPatterns = { UriServlet.TASK, UriServlet.TASK_ADD, UriServlet.TASK_DELETE,
		UriServlet.TASK_EDIT })
public class TaskServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static TaskDao taskDao;
	private static ArrayList<Task> tasks;
	
	@Override
	public void init() throws ServletException {	
		
		super.init();
		taskDao = new TaskDao(); 
		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String action = req.getServletPath();
		System.out.println("action " + action);

		switch (action) {
		case UriServlet.TASK:
			processViews(req, resp);
			break;
		case UriServlet.TASK_EDIT:
			getEdit(req, resp);
			break;
		case UriServlet.TASK_DELETE:
			getDelete(req, resp);
			break;
		}

	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		System.out.println("from post: " + action);
		
		switch (action) {
		case UriServlet.TASK_EDIT:
			doEdit(req, resp);
			break;
		case UriServlet.TASK_ADD:
			doAdd(req, resp);
			break;
		}
	}

	private void processViews(HttpServletRequest req, HttpServletResponse resp) {
		try {
			String _keyword = req.getParameter("keyword");
			String _pageId = req.getParameter("pageid");
			String _limit = req.getParameter("limit");
			String _projectId = req.getParameter("project_id");
			
			int pageId = 1;
			int limit = 20;
			
			if (_keyword == null) {
				_keyword = "";
			} 
			
			if(_pageId != null  && !_pageId.isEmpty()) {
				pageId = Integer.parseInt(_pageId);
			}
			
			if(_limit != null  && !_limit.isEmpty()) {
				limit = Integer.parseInt(_limit);
			}
			
			
			int totalPage = (int) Math.ceil((float)taskDao.getTotalRecord(_keyword)/(float)limit);	
			int index = (pageId - 1) * limit;
			int projectId = Integer.parseInt(_projectId);
			//tasks = taskDao.search(_keyword,index,limit);
			
			tasks = taskDao.getByProjectId(projectId);
			
			req.setAttribute("listAccountIdAndFullName", new AccountDao().getIdAndFullName());
			req.setAttribute("listProject", new ProjectDao().getAll());
			
			req.setAttribute("totalRecord", taskDao.getTotalRecord(_keyword));
			req.setAttribute("pageid", pageId);
			req.setAttribute("totalPage", totalPage);
			req.setAttribute("viewsTask",tasks);
			req.getRequestDispatcher(UriJsp.TASK).forward(req, resp);		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getEdit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {

			String id = req.getParameter("id");
			System.out.println("id " + id);

			if (id != null && !id.isEmpty()) {
				Task task = taskDao.getById(Integer.parseInt(id));
				PrintWriter out = resp.getWriter();
				resp.setContentType("application/json");
				Gson gson = new Gson();
				String objectReturn = gson.toJson(task);
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
			//tam thoi khi edit get localdatetime.now()
			Task task = new Task();
			task.setName(req.getParameter("name"));
			task.setGroup_id(Integer.parseInt((String) req.getParameter("group_id")));
			task.setAccount_id(Integer.parseInt((String) req.getParameter("account_id")));
			task.setStatus_id(Integer.parseInt((String) req.getParameter("status_id")));
			
			String id = req.getParameter("id");
			System.out.println("taskId doEdit: "+ id);
			
			if (id != null && !id.isEmpty()) {
				task.setId(Integer.parseInt(id));
				
				if(taskDao.update(task) > 0 ) {
					System.out.println("update thanh cong");
				}else {
					System.out.println("update that bai");
				}
				
				int projectId = Integer.parseInt((String) req.getParameter("group_id"));
				resp.sendRedirect(req.getContextPath() + UriServlet.TASK + "?project_id=" + projectId);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Task task = new Task();
		task.setName(req.getParameter("task_name"));
		task.setEnd_date(LocalDate.now());
		task.setGroup_id(Integer.parseInt((String) req.getParameter("group_id")));
		task.setAccount_id(Integer.parseInt((String) req.getParameter("account_id")));
		task.setStatus_id(Integer.parseInt((String) req.getParameter("status_id")));
		try {
			
			if(taskDao.insert(task) > 0) {
				System.out.println("tao task thanh cong");
			}else {
				System.out.println("tao task that bai");
			}
			resp.sendRedirect(req.getContextPath() + UriServlet.TASK+"?project_id="+req.getParameter("group_id"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void getDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			int id = Integer.valueOf((String) req.getParameter("id"));
			taskDao.delete(id);
			resp.sendRedirect(req.getContextPath() + UriServlet.TASK);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
