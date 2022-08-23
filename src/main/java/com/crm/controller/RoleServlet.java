package com.crm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crm.dao.RoleDao;
import com.crm.model.Role;
import com.crm.uri.UriJsp;
import com.crm.uri.UriServlet;
import com.google.gson.Gson;

@WebServlet(urlPatterns = { UriServlet.ROLE, UriServlet.ROLE_ADD, UriServlet.ROLE_DELETE, UriServlet.ROLE_EDIT })

public class RoleServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static RoleDao roleDao;
	private static ArrayList<Role> roles;

	@Override
	public void init() throws ServletException {

		super.init();
		roleDao = new RoleDao(); // new fix 12:26

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = req.getServletPath();
		System.out.println("action " + action);

		switch (action) {
		case UriServlet.ROLE:
			processViews(req, resp);
			break;
		case UriServlet.ROLE_EDIT:
			getEdit(req, resp);
			break;
		case UriServlet.ROLE_DELETE:
			getDelete(req, resp);
			break;
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		System.out.println("from post: " + action);

		switch (action) {
		case UriServlet.ROLE_EDIT:
			doEdit(req, resp);
			break;
		case UriServlet.ROLE_ADD:
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
			int limit = 5;

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
			roles = roleDao.search(keyword, index, limit);

			int totalPage = (int) Math.ceil((float) roleDao.getTotalRecord(keyword) / (float) limit);
			req.setAttribute("totalRecord", roleDao.getTotalRecord(keyword));
			req.setAttribute("pageid", pageid);
			req.setAttribute("totalPage", totalPage);
			req.setAttribute("viewsRole", roles); // fix
			req.getRequestDispatcher(UriJsp.ROLE).forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getEdit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {

			String id = req.getParameter("id");
			
			System.out.println("getedit id " + id);
			
			if (id != null && !id.isEmpty()) {
				Role role = roleDao.getById(Integer.parseInt(id));
				PrintWriter out = resp.getWriter();
				resp.setContentType("application/json");
				Gson gson = new Gson();
				String objectReturn = gson.toJson(role);
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
			Role role = new Role();
			
			role.setName(req.getParameter("name"));
			role.setDescription(req.getParameter("description"));
			
			String id = req.getParameter("id");
			System.out.println("doEdit id " + id);
			
			
			if (id != null && !id.isEmpty()) {
				role.setId(Integer.parseInt(id));
				roleDao.update(role);
				resp.sendRedirect(req.getContextPath() + UriServlet.ROLE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Role role = new Role();
		role.setName(req.getParameter("name"));
		role.setDescription(req.getParameter("description"));

		try {
			roleDao.insert(role);
			resp.sendRedirect(req.getContextPath() + UriServlet.ROLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			int id = Integer.valueOf((String) req.getParameter("id"));
			roleDao.delete(id);
			resp.sendRedirect(req.getContextPath() + UriServlet.ROLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
