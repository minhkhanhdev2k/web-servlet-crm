package com.crm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crm.dao.ProjectDao;
import com.crm.model.Project;
import com.crm.uri.UriJsp;
import com.crm.uri.UriServlet;
import com.google.gson.Gson;

@WebServlet(urlPatterns = { UriServlet.PROJECT, UriServlet.PROJECT_ADD, UriServlet.PROJECT_DELETE,
		UriServlet.PROJECT_EDIT })
public class ProjectServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static ProjectDao projectDao;
	private static ArrayList<Project> projects;

	@Override
	public void init() throws ServletException {

		super.init();
		projectDao = new ProjectDao(); // new fix 12:26

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = req.getServletPath();
		System.out.println("action " + action);

		switch (action) {
		case UriServlet.PROJECT:
			processViews(req, resp);

			break;
		case UriServlet.PROJECT_EDIT:
			getEdit(req, resp);
			break;
		case UriServlet.PROJECT_DELETE:
			getDelete(req, resp);
			break;
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		System.out.println("from post: " + action);

		switch (action) {
		case UriServlet.PROJECT_EDIT:
			doEdit(req, resp);
			break;
		case UriServlet.PROJECT_ADD:
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

			if (id != null && !id.isEmpty()) {
				pageid = Integer.parseInt(id);
			}

			if (limitString != null && !limitString.isEmpty()) {
				limit = Integer.parseInt(limitString);
			}

			int index = (pageid - 1) * limit;

			System.out.println("indexpage" + index);
			projects = projectDao.search(keyword, index, limit);

			int totalPage = (int) Math.ceil((float) projectDao.getTotalRecord(keyword) / (float) limit);
			req.setAttribute("totalRecord", projectDao.getTotalRecord(keyword));
			req.setAttribute("pageid", pageid);
			req.setAttribute("totalPage", totalPage);
			req.setAttribute("viewsProject", projects);
			req.getRequestDispatcher(UriJsp.PROJECT).forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getEdit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {

			String id = req.getParameter("id");
			System.out.println("id " + id);

			if (id != null && !id.isEmpty()) {
				Project project = projectDao.getById(Integer.parseInt(id));
				PrintWriter out = resp.getWriter();
				resp.setContentType("application/json");
				Gson gson = new Gson();
				String objectReturn = gson.toJson(project);
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
			Project project = new Project();

			project.setName(req.getParameter("name"));
			project.setDescription(req.getParameter("description"));

			String id = req.getParameter("id");
			System.out.println(id);

			if (id != null && !id.isEmpty()) {
				project.setId(Integer.parseInt(id));
				projectDao.update(project);
				resp.sendRedirect(req.getContextPath() + UriServlet.PROJECT);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Project project = new Project();
		project.setName(req.getParameter("name"));
		project.setDescription(req.getParameter("description"));

		try {
			projectDao.insert(project);
			resp.sendRedirect(req.getContextPath() + UriServlet.PROJECT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			int id = Integer.valueOf((String) req.getParameter("id"));
			projectDao.delete(id);
			resp.sendRedirect(req.getContextPath() + UriServlet.PROJECT);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
