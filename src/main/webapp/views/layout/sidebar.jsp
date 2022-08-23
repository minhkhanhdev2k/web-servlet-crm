<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.crm.uri.UriServlet"%>

<!--sidebar start-->
<aside>
	<div id="sidebar" class="nav-collapse ">
		<!-- sidebar menu start-->
		<ul class="sidebar-menu" id="nav-accordion">
			<li><a href="home"> <i class="fa fa-dashboard"></i> <span>Dashboard</span>
			</a></li>

			<li><a
				href="<%=request.getContextPath() + UriServlet.ACCOUNT%>">
					<i class="fa fa-cog"></i><span>Thành Viên</span>
			</a></li>

			<li><a
				href="<%=request.getContextPath() + UriServlet.ROLE %>"><i
					class="fa fa-flag"></i><span>Quyền</span> </a></li>
			<li><a
				href="<%=request.getContextPath() + UriServlet.PROJECT %>">
					<i class="fa fa-book"></i> <span>Dự Án</span>
			</a></li>
			
			
		</ul>
		<!-- sidebar menu end-->
	</div>
</aside>
<!--sidebar end-->
