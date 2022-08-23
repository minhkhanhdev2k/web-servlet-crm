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
			<header class="panel-heading"> Manage Project </header>


			<div class="panel-body">
				<div class="adv-table editable-table ">
					<div class="clearfix">
						<div class="btn-group">
							<!--  Start Modal -->
							<a class="btn btn-compose" data-toggle="modal" href="#myModalAdd"><i
								class="fa fa-plus"></i> new project </a>
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
										<option value="5" selected="selected">5</option>
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
								<th>STT</th>
								<th>NAME</th>
								<th>DESCRIPTION</th>
								<th>ACTION</th>
							</tr>
						</thead>
						<tbody>
							<tr class="">
								<c:forEach var="project" items="${viewsProject}"
									varStatus="loop">
									<tr>
										<th scope="row">${loop.index + 1}</th>
										<td>${project.name}</td>
										<td>${project.description}</td>
										<td><a class="btn btn-success btn-xs"
											href="task?project_id=${project.id}"><i class="fa fa-eye"></i>
												View </a> <a class="btn btn-primary btn-xs btn-project-edit"
											data-toggle="modal" data-id="${project.id}"
											href="#myModalEdit"><i class="fa fa-pencil"></i> Edit </a> <a
											class="btn btn-danger btn-xs"
											href="project/delete?id=${project.id}"><i
												class="fa fa-trash-o "></i> Delete </a></td>
									</tr>
								</c:forEach>
							</tr>
						</tbody>
					</table>
					<div class="row">
						<div class="col-lg-6">
							<div class="dataTables_paginate paging_bootstrap ">
								<ul>
									<li>total record: ${totalRecord}</li>
									<c:if test="${pageid != 1}">
										<li class="prev active"><a
											href="<%=request.getContextPath() + UriServlet.PROJECT %>?pageid=${pageid - 1}&keyword=<%=request.getParameter("keyword")%>">←
												Prev</a></li>
									</c:if>
									<c:forEach var="i" begin="1" end="${totalPage}">
										<c:choose>
											<c:when test="${pageid == i}">
												<li class="active"><a
													href="<%=request.getContextPath() + UriServlet.PROJECT %>?pageid=${i}&keyword=<%=request.getParameter("keyword")%>">${i}</a></li>
											</c:when>
											<c:otherwise>
												<li><a
													href="<%=request.getContextPath() + UriServlet.PROJECT%>?pageid=${i}&keyword=<%=request.getParameter("keyword")%>">${i}</a></li>
											</c:otherwise>
										</c:choose>
									</c:forEach>
									<c:if test="${pageid != totalPage}">
										<li class="next active"><a
											href="<%=request.getContextPath() + UriServlet.PROJECT %>?pageid=${pageid + 1}&keyword=<%=request.getParameter("keyword")%>">Next
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
					<h4 class="modal-title">Create Project</h4>
				</div>
				<div class="modal-body">
					<!-- form du lieu  -->
					<form class="form-horizontal" role="form"
						action="<%=request.getContextPath() + UriServlet.PROJECT_ADD%>"
						method="post">
						<div class="form-group">
							<label class="col-lg-2 control-label">Name</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="name">
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label">Description</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="description">
							</div>
						</div>
						<div class="form-group">
							<div class="col-lg-10">
								<input type="submit" value="Create Project"></input>
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
					<h4 class="modal-title">Update Project</h4>
				</div>
				<div class="modal-body">
					<!-- form du lieu  -->
					<form class="form-horizontal" role="form"
						action="<%=request.getContextPath() + UriServlet.PROJECT_EDIT%>"
						method="post" id="formProject">
						<input type="hidden" id="id" name="id">
						<div class="form-group">
							<label class="col-lg-2 control-label">Name</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="name" name="name">
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label">Description</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="description"
									name="description">
							</div>
						</div>
						<div class="form-group">
							<div class="col-lg-10">
								<input type="submit" value="Update Project"></input>
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