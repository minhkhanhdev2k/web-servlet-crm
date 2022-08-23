<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<body>
	<section class="wrapper">
		<!-- page start-->
		<div class="row">
			<aside class="profile-nav col-lg-3">
				<section class="panel">
					<div class="user-heading round">
						<a href="#"> <img src="lassets/img/profile-avatar.jpg" alt="">
						</a>
						<h1>${account.fullname}</h1>
						<p>${account.email }</p>
					</div>

					<ul class="nav nav-pills nav-stacked">
						<li class="active"><a href="#"> <i
								class="fa fa-user"></i> Profile
						</a></li>
						<li><a href="#"> <i
								class="fa fa-calendar"></i> Recent Activity <span
								class="label label-danger pull-right r-activity">9</span></a></li>
						<li><a href="#"> <i class="fa fa-edit"></i>
								Edit profile
						</a></li>
					</ul>

				</section>
			</aside>
			<aside class="profile-info col-lg-9">
				<section class="panel">
					<div class="bio-graph-heading">Aliquam ac magna metus. Nam
						sed arcu non tellus fringilla fringilla ut vel ispum. Aliquam ac
						magna metus.</div>
					<div class="panel-body bio-graph-info">
						<h1>${account.fullname}</h1>
						<div class="row">
							<div class="bio-row">
								<p>
									<span>Full Name</span>: ${account.fullname}
								</p>
							</div>
							<div class="bio-row">
								<p>
									<span>Email </span>: ${account.email }
								</p>
							</div>
							<div class="bio-row">
								<p>
									<span>Phone </span>: ${account.phone }
								</p>
							</div>
							<div class="bio-row">
								<p>
									<span>Address </span>: ${account.address }
								</p>
							</div>
						</div>
					</div>
				</section>
				<section>
					<div class="bio-graph-heading">Tasks Status</div>
					<div class="row">
						<div class="col-lg-6">
							<div class="panel">
								<div class="panel-body">
									<div class="bio-desk">
										<h4 class="red">Chưa Thực Hiện</h4>
										<div class="message-center">
											<c:forEach var="task" items="${taskByAccountId }">
												<c:choose>
													<c:when test="${task.status_id == 1 }">
														<div>
															<h5>${task.name }</h5>
															<span class="time">Kết thúc: ${task.end_date }</span>
														</div>
													</c:when>
													<c:otherwise>
														<div>
															<span> Hiện không có task nào! </span>
														</div>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-lg-6">
							<div class="panel">
								<div class="panel-body">
									<div class="bio-desk">
										<h4 class="red">Đang Thực Hiện</h4>
										<div class="message-center">
											<c:forEach var="task" items="${taskByAccountId }">
												<c:choose>
													<c:when test="${task.status_id == 2 }">
														<div>
															<h5>${task.name }</h5>
															<span class="time">Kết thúc: ${task.end_date }</span>
														</div>
													</c:when>
													<c:otherwise>
														<div>
															<span> Hiện không có task nào! </span>
														</div>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</div>

									</div>
								</div>
							</div>
						</div>
						<div class="col-lg-6">
							<div class="panel">
								<div class="panel-body">
									<div class="bio-desk">
										<h4 class="red">Hoàn Thành</h4>
										<div class="message-center">
											<c:forEach var="task" items="${taskByAccountId }">
												<c:choose>
													<c:when test="${task.status_id == 3 }">
														<div>
															<h5>${task.name }</h5>
															<span class="time">Kết thúc: ${task.end_date }</span>
														</div>
													</c:when>
													<c:otherwise>
														<div>
															<span> Hiện không có task nào! </span>
														</div>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</section>
			</aside>
		</div>
		<!-- page end-->
	</section>
</body>
</html>