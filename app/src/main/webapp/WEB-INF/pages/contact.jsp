<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Catalog of games</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<!--[if ie]><meta content='IE=8' http-equiv='X-UA-Compatible'/><![endif]-->
	<!-- bootstrap -->
	<link href="${pageContext.request.contextPath}/resources/themes/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resources/themes/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">

	<link href="${pageContext.request.contextPath}/resources/themes/css/bootstrappage.css" rel="stylesheet"/>

	<!-- global styles -->
	<link href="${pageContext.request.contextPath}/resources/themes/css/flexslider.css" rel="stylesheet"/>
	<link href="${pageContext.request.contextPath}/resources/themes/css/main.css" rel="stylesheet"/>

	<!-- scripts -->
	<script src="${pageContext.request.contextPath}/resources/themes/js/jquery-1.7.2.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/themes/bootstrap/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/themes/js/superfish.js"></script>
	<!--[if lt IE 9]>
	<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<script src="/resources/themes/js/respond.min.js"></script>
	<![endif]-->
	<style>
		.msg {
			padding: 15px;
			margin-bottom: 20px;
			border: 1px solid transparent;
			border-radius: 4px;
			color: #31708f;
			background-color: #d9edf7;
			border-color: #bce8f1;
		}
		.error {
			padding: 15px;
			margin-bottom: 20px;
			border: 1px solid transparent;
			border-radius: 4px;
			color: #a94442;
			background-color: #f2dede;
			border-color: #ebccd1;
		}
	</style>
</head>
<body>
<div id="top-bar" class="container">
	<div class="row">
		<div class="span4">
			<form action="/catalog/search?page=1" method="POST" command class="search_form">
				<input name="searchStr" type="text" class="input-block-level search-query" maxlength="15" Placeholder="Search games">
			</form>
		</div>
		<div class="span8">
			<div class="account pull-right">
				<ul class="user-menu">
					<c:url value="/j_spring_security_logout" var="logoutUrl" />
					<script>
                        function formSubmit() {
                            document.getElementById("logoutForm").submit();
                        }
					</script>
					<c:if test="${client.role.name=='ROLE_EMPLOYEE'}">
						<li><a href="/employee/administration">Administration</a></li>
					</c:if>
					<li><a href="/clients/profile">My Account</a></li>
					<c:if test="${client.role.name!='ROLE_EMPLOYEE'}">

						<c:if test="${cartList==null}">
							<li><a href="/catalog/goods/cart">Your Cart(0)</a></li>
						</c:if>
						<c:if test="${cartList!=null}">
							<li><a href="/catalog/goods/cart">Your Cart(${cartList.size()})</a></li>
						</c:if>
					</c:if>

					<c:if test="${client.role.name!=null}" >

						<form action="${logoutUrl}" method="post" id="logoutForm" style="display: inline;" >

							<input type="hidden" size="0"
								   name="${_csrf.parameterName}"
								   value="${_csrf.token}" />
						</form>
						<li><a href="javascript:formSubmit()">Logout</a></li>
					</c:if>
					<c:if test="${client.role.name==null}" >
						<li><a href="/clients/identification">Login</a></li>
					</c:if>
				</ul>
			</div>
		</div>
	</div>
</div>
<div id="wrapper" class="container">
	<section class="navbar main-menu">
		<div class="navbar-inner main-menu">
			<a href="${pageContext.request.contextPath}/" class="logo pull-left"><img src="/resources/themes/images/logo.png" class="site_logo" alt=""></a>
			<nav id="menu" class="pull-right">
				<ul>
					<li><a href="${pageContext.request.contextPath}/catalog/page/${1}">Catalog</a>
						<ul>
							<c:forEach var="categoryVar"  items="${listCategory}">
								<li><a href="${pageContext.request.contextPath}/catalog/${categoryVar.name}/page/${1}">${categoryVar.name}</a></li>
							</c:forEach>
						</ul>
					</li>
					<li><a href="${pageContext.request.contextPath}/bestsellers">Best Sellers</a>
					<li><a href="${pageContext.request.contextPath}/contact">Contact us</a></li>
				</ul>
			</nav>
		</div>
	</section>
	<section class="google_map">
		<iframe width="100%" height="300" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="https://www.google.com/maps/embed/v1/view?zoom=16&center=59.9323%2C30.3503&key=AIzaSyADsATMpSfkZWZft7TXyJfGEXmwWKKqwes."></iframe>
	</section>
	<section class="header_text sub">
		<img class="pageBanner" src="/resources/themes/images/pageBanner.png" alt="" >
	</section>
			<section class="main-content">				
				<div class="row">				
					<div class="span5">
						<div>
							<h5>OUR CONTACTS</h5>
							<p><strong>Phone:</strong>&nbsp;(123) 456-7890<br>
							<strong>Fax:</strong>&nbsp;+04 (123) 456-7890<br>
							<strong>Email:</strong>&nbsp;<a href="#">dice.gamesstore@gmail.ru</a>
							</p>
							<br/>
							<h5>SECONDARY OFFICE IN SAINT-PETERSBURG</h5>
							<p><strong>Phone:</strong>&nbsp;(113) 023-1125<br>
							<strong>Fax:</strong>&nbsp;+04 (113) 023-1145<br>
							<strong>Email:</strong>&nbsp;<a href="#">dice.gamesstore@gmail.ru</a>
							</p>
						</div>
					</div>
					<div class="span7">
						<img src="/resources/themes/images/contact.jpg">
					</div>				
				</div>
			</section>
			<section id="footer-bar">
				<div class="row">
					<div class="span3">
						<h4>Navigation</h4>
						<ul class="nav">
							<li><a href="/">Homepage</a></li>
							<li><a href="/contact">Contact Us</a></li>
							<li><a href="/clients/identification">Login</a></li>
							<li><a href="/catalog/goods/cart">Your Cart</a></li>
							<li><a href="/clients/profile">My Account</a></li>
						</ul>
					</div>
					<div class="span5 pull-right">
						<p class="logo"><img src="/resources/themes/images/logo.png" class="site_logo" alt=""></p>
						<p>Site was made by Pavel Mikheev</p>
						<br/>
						<span class="social_icons">
									<a class="facebook" href="#">Facebook</a>
									<a class="twitter" href="#">Twitter</a>
									<a class="skype" href="#">Skype</a>
									<a class="vimeo" href="#">Vimeo</a>
								</span>
					</div>
				</div>
			</section>
			<section id="copyright">
				<span>Copyright 2013 bootstrappage template  All right reserved.</span>
			</section>
		</div>
		<script src="/resources/themes/js/common.js"></script>
	</body>
</html>