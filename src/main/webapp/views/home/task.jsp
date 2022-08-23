<%@page import="com.crm.uri.UriServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<body>
	<section class="wrapper site-min-height">
		<!-- page start-->
		<section class="panel">
			<header class="panel-heading">
				<span> ${viewsTask[0].group_name}</span> <span class="pull-right">
					<a href="project">/ views all</a>

				</span>
			</header>

			<div class="panel-body">
				<div class="adv-table editable-table ">
					<div class="clearfix">
						<div class="btn-group">
							<!--  Start Modal -->
							<a class="btn btn-compose" data-toggle="modal" href="#myModalAdd">
								Add Task </a>
							<!--  end modal -->
						</div>
						<div class="btn-group pull-right">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#">Print</a></li>
								<li><a href="#">Save as PDF</a></li>
								<li><a href="#">Export to Excel</a></li>
							</ul>
						</div>
					</div>
					<div class="space15"></div>
					<div class="row">
						<div class="col-lg-6">
							<div id="editable-sample_length" class="dataTables_length">
								<label><select size="1" name="editable-sample_length"
									aria-controls="editable-sample" class="form-control xsmall">
										<option value="5" selected>5</option>
										<option value="15">15</option>
										<option value="20">20</option>
								</select> records per page</label>
							</div>
						</div>
						<div class="col-lg-6 ">
							<div class="dataTables_filter" id="editable-sample_filter">
								<!--  search -->
								<form class="form-inline">
									<input type="text"
										value="<%=request.getParameter("keyword") != null ? request.getParameter("keyword") : ""%>"
										name="keyword" aria-controls="editable-sample"> <input
										type="submit" value="search">
								</form>
							</div>
						</div>
					</div>
					<table class="table table-striped table-hover table-bordered"
						id="editable-sample">
						<thead>
							<tr>
								<!--  task_id, task_name, end_date, group_id, account_id, status_id -->
								<th>STT</th>
								<th>NAME TASK</th>
								<th>END DATE</th>
								<th>EXECUTOR</th>
								<th>STATUS</th>
								<th>#ACTION</th>
							</tr>
						</thead>
						<tbody>
							<tr class="">
								<c:forEach var="item" items="${viewsTask}" varStatus="loop">
									<tr>
										<th scope="row">${loop.index + 1}</th>
										<td>${item.name}</td>
										<td>${item.end_date}</td>
										<td><a href="profile?id=${item.account_id}">${item.account_name}</a></td>
										<td><c:choose>
												<c:when test="${item.status_id == 1}">
													<span class="label label-info">Pending</span>
												</c:when>
												<c:when test="${item.status_id == 2}">
													<span class="label label-info">Ongoing</span>
												</c:when>
												<c:otherwise>
													<span class="label label-info">Completed</span>
												</c:otherwise>
											</c:choose></td>
										<td align="center"><a class="btn btn-success btn-xs"
											href="task"><i class="fa fa-eye"></i> View</a> <a
											class="btn btn-primary btn-xs btn-tasksss-edit"
											data-toggle="modal" data-id="${item.id}" href="#myModalEdit"><i
												class="fa fa-pencil"></i> Edit</a> <a
											class="btn btn-danger btn-xs"
											href="task/delete?id=${item.id}"><i
												class="fa fa-trash-o "></i> Delete</a>
									</tr>
								</c:forEach>
							</tr>
						</tbody>
					</table>
					<div class="row">
						<div class="col-lg-6">

							<div class="dataTables_paginate paging_bootstrap pagination">

								<ul>
									<li>total record: ${totalRecord}</li>
									<c:if test="${pageid != 1}">
										<li class="prev active"><a
											href="<%=request.getContextPath() + UriServlet.TASK %>?pageid=${pageid - 1}&keyword=<%=request.getParameter("keyword")%>">←
												Prev</a></li>
									</c:if>
									<c:forEach var="i" begin="1" end="${totalPage}">
										<c:choose>
											<c:when test="${pageid == i}">
												<li class="active"><a
													href="<%=request.getContextPath() + UriServlet.TASK%>?pageid=${i}&keyword=<%=request.getParameter("keyword")%>">${i}</a></li>
											</c:when>
											<c:otherwise>
												<li><a
													href="<%=request.getContextPath() + UriServlet.TASK%>?pageid=${i}&keyword=<%=request.getParameter("keyword")%>">${i}</a></li>
											</c:otherwise>
										</c:choose>
									</c:forEach>
									<c:if test="${pageid != totalPage}">
										<li class="next active"><a
											href="<%=request.getContextPath() + UriServlet.TASK%>?pageid=${pageid + 1}&keyword=<%=request.getParameter("keyword")%>">Next
												→ </a></li>
									</c:if>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
		<!-- page end-->

	</section>



	<!--  start modal create -->
	<div class="modal fade" id="myModalAdd" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">Create Task</h4>
				</div>
				<div class="modal-body">
					<!-- form du lieu  -->
					<form class="form-horizontal" role="form"
						action="<%=request.getContextPath() + UriServlet.TASK_ADD%>"
						method="post">
						<!--  task_id, task_name, end_date, group_id, account_id, status_id -->
						<div class="form-group">
							<label class="col-lg-2 control-label">Name Task</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="task_name">
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label">Project</label>
							<div class="col-lg-10">
								<c:set var="projectid"
									value='<%=Integer.parseInt((String) request.getParameter("project_id"))%>'></c:set>
								<select class="form-control" id="group_id" name="group_id">
									<c:forEach var="item" items="${listProject}">
										<c:if test="${item.id == projectid }">
											<option value="${item.id}" selected>${item.name}</option>
										</c:if>
										<c:if test="${item.id != projectid }">
											<option value="${item.id}">${item.name}</option>
										</c:if>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label">Executer</label>
							<div class="col-lg-10">
								<select class="form-control" id="account_id" name="account_id">
									<option value="">Select Executer</option>
									<c:forEach var="item" items="${listAccountIdAndFullName}">
										<option value="${item.account_id}">${item.fullname}</option>
									</c:forEach>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">Status</label>
							<div class="col-lg-10">
								<select class="form-control" id="status_id" name="status_id">
									<option value="1">chua thuc hien</option>
									<option value="2">dang thuc hien</option>
									<option value="3">hoan thanh</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<div class="col-lg-10">
								<input type="submit" value="Create Task"></input>
							</div>
						</div>
					</form>
					<!-- form du lieu  -->
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!--  end modal create -->

	<!-- Modal edit-->
	<div class="modal fade" id="myModalEdit" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">Update Task</h4>
				</div>
				<div class="modal-body">
					<!--  task_id, task_name, end_date, group_id, account_id, status_id -->
					<form class="form-horizontal" role="form"
						action="<%=request.getContextPath() + UriServlet.TASK_EDIT%>"
						method="post" id="formTask">
						<input type="hidden" id="id" name="id">
						<div class="form-group">
							<label class="col-lg-2 control-label">Name Task</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="name" name="name">
							</div>

						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label">Project</label>
							<div class="col-lg-10">
								<select class="form-control" id="group_id" name="group_id">
									<c:forEach var="item" items="${listProject}">
										<option value="${item.id}">${item.name}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label">Executer</label>
							<div class="col-lg-10">
								<select class="form-control" id="account_id" name="account_id">
									<c:forEach var="item" items="${listAccountIdAndFullName}">
										<option value="${item.account_id}">${item.fullname}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label">Status</label>
							<div class="col-lg-10">
								<select class="form-control" id="status_id" name="status_id">
									<option value="1">chua thuc hien</option>
									<option value="2">dang thuc hien</option>
									<option value="3">hoan thanh</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<div class="col-lg-10">
								<input type="submit" value="Update Task"></input>
							</div>
						</div>
					</form>
					<!-- form du lieu  -->
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal edit-->

</body>
</html>