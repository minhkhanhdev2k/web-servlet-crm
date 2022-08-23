<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="dec"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<!--  coppy toan bo header to header -->
<!-- BEGIN HEADER -->
<head>
<meta charset="utf-8">
<title><dec:title /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<meta name="keyword"
	content="FlatLab, Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">
<link rel="shortcut icon" href="lassets/img/favicon.html">
<!-- Bootstrap core CSS -->
<link href="lassets/css/bootstrap.min.css" rel="stylesheet">
<link href="lassets/css/bootstrap-reset.css" rel="stylesheet">
<!--external css-->
<link href="lassets/assets/font-awesome/css/font-awesome.css"
	rel="stylesheet" />
<link
	href="lassets/assets/jquery-easy-pie-chart/jquery.easy-pie-chart.css"
	rel="stylesheet" type="text/css" media="screen" />
<link rel="stylesheet" href="lassets/css/owl.carousel.css"
	type="text/css">
<!--right slidebar-->
<link href="lassets/css/slidebars.css" rel="stylesheet">
<!-- Custom styles for this template -->
<link href="lassets/css/style.css" rel="stylesheet">
<link href="lassets/css/style-responsive.css" rel="stylesheet" />
<link rel="stylesheet"
	href="lassets/assets/data-tables/DT_bootstrap.css" />
</head>
<!-- END HEADER -->


<!-- BEGIN BODY -->
<body>

	<section id="container" class="">
		<!-- HEADER  -->
		<jsp:include page="/views/layout/header.jsp" />

		<!-- SIDEBAR -->
		<jsp:include page="/views/layout/sidebar.jsp" />

		<!--main content start-->
		<section id="main-content">

			<!-- body -->
			<dec:body />

		</section>
		<!--main content end-->
	</section>

	<!-- FOOTER BEGIN -->
	<jsp:include page="/views/layout/footer.jsp" />

	<!--BEGIN SCRIPT !-->
	<!-- js placed at the end of the document so the pages load faster -->
	<script src="lassets/js/jquery.js"></script>
	<script src="lassets/js/bootstrap.min.js"></script>
	<script src="lassets/js/jquery.dcjqaccordion.2.7.js" class="include"
		type="text/javascript"></script>
	<script src="lassets/js/jquery.scrollTo.min.js"></script>
	<script src="lassets/js/jquery.nicescroll.js" type="text/javascript"></script>
	<script src="lassets/js/jquery.sparkline.js" type="text/javascript"></script>
	<script
		src="lassets/assets/jquery-easy-pie-chart/jquery.easy-pie-chart.js"></script>
	<script src="lassets/js/owl.carousel.js"></script>
	<script src="lassets/js/jquery.customSelect.min.js"></script>
	<script src="lassets/js/respond.min.js"></script>
	<!--right slidebar-->
	<script src="lassets/js/slidebars.min.js"></script>
	<!--common script for all pages-->
	<script src="lassets/js/common-scripts.js"></script>
	<!--script for this page-->
	<script src="lassets/js/sparkline-chart.js"></script>
	<script src="lassets/js/easy-pie-chart.js"></script>
	<script src="lassets/js/count.js"></script>
	<script type="text/javascript"
		src="lassets/assets/data-tables/jquery.dataTables.js"></script>
	<script type="text/javascript"
		src="lassets/assets/data-tables/DT_bootstrap.js"></script>


	<script src="lassets/js/jquery-ui-1.9.2.custom.min.js"></script>
	<script src="lassets/js/jquery-migrate-1.2.1.min.js"></script>

	<!-- accoiunt js -->

	<!--  <dec:getProperty property="page.scripts"></dec:getProperty> this or below -->
	<script src="lassets/js/webjs/account.js"></script>
	<script src="lassets/js/webjs/role.js"></script>
	<script src="lassets/js/webjs/project.js"></script>
	<script src="lassets/js/webjs/tasksss.js"></script>
	<!--END SCRIPT !-->

	<!-- begin script custom-->
	<script>
		//owl carousel
		$(document).ready(function() {
			$("#owl-demo").owlCarousel({
				navigation : true,
				slideSpeed : 300,
				paginationSpeed : 400,
				singleItem : true,
				autoPlay : true
			});
		});

		//custom select box
		$(function() {
			$('select.styled').customSelect();
		});
	</script>
	<!-- end script nhung  custom-->

	

	<!-- END JAVASCRIPTS -->

</body>
<!-- END BODY -->
</html>