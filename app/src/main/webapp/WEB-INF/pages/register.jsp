<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en" >
	<head>
		<meta charset="utf-8">
		<title>Log in or register</title>
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
			.error {
				padding: 15px;
				margin-bottom: 20px;
				border: 1px solid transparent;
				border-radius: 4px;
				color: #a94442;
				background-color: #f2dede;
				border-color: #ebccd1;
			}

			.msg {
				padding: 15px;
				margin-bottom: 20px;
				border: 1px solid transparent;
				border-radius: 4px;
				color: #31708f;
				background-color: #d9edf7;
				border-color: #bce8f1;
			}

			#login-box {
				width: 300px;
				padding: 20px;
				margin: 100px auto;
				background: #fff;
				-webkit-border-radius: 2px;
				-moz-border-radius: 2px;
				border: 1px solid #000;
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
		<section class="header_text sub">
			<img class="pageBanner" src="/resources/themes/images/pageBanner.png" alt="Login or register" >
			<h4><span>LOGIN OR REGISTER</span></h4>
		</section>
	</section>
			<section class="main-content">				
				<div class="row">
					<div class="span5" th:fragment="content">
						<h4 class="title"><span class="text"><strong>Login</strong> Form</span></h4>
						<c:if test="${not empty error}">
							<div class="error">${error}</div>
						</c:if>
						<c:if test="${not empty msg}">
							<div class="msg">${msg}</div>
						</c:if>

						<form name="loginForm"    action="<c:url value='/j_spring_security_check' />" method="POST">
							<input type="hidden" name="next" value="/">
							<fieldset>
								<div class="control-group">
									<label for="username" class="control-label">Email</label>
									<div class="controls">
										<input type='text' id="username" name='username' placeholder="Enter your username" id="username" class="input-xlarge">
									</div>
								</div>
								<div class="control-group">
									<label for="password" class="control-label">Password</label>
									<div class="controls">
										<input type="password" id="password" name="password" placeholder="Enter your password" class="input-xlarge">
									</div>
								</div>
								<div class="control-group">
									<input tabindex="3" class="btn btn-inverse large" type="submit" value="Sign into your account">
									<hr>
									<p class="reset">Recover your <a tabindex="4" href="/send/recover" title="Recover your password or confirm account">password or confirm account</a></p>
								</div>
							</fieldset>
						</form>
					</div>
					<div class="span7">					
						<h4 class="title"><span class="text"><strong>Register</strong> Form</span></h4>

						<c:if test="${not empty regError}">
							<div class="error">${regError}</div>
						</c:if>


						<spring:form action="success" method="post" commandName="newClient" class="form-stacked">
							<fieldset>
								<div class="control-group">
									<label class="control-label">Name</label>
									<div class="controls">
										<spring:input path="name" type="text" placeholder="Enter your name" class="input-xlarge" pattern="^[a-zA-Z0-9_]*$"/>
										<spring:errors path="name" cssClass="error"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">Email address:</label>
									<div class="controls">
										<spring:input path="email" type="text" placeholder="Enter your email" class="input-xlarge" pattern="^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]+$"/>
										<spring:errors path="email" cssClass="error"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">Password:</label>
									<div class="controls">
										<spring:input path="password" type="password" placeholder="Enter your password" class="input-xlarge" pattern="^[a-zA-Z0-9]+$"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">Phone number:</label>
									<div class="controls">
										<spring:input path="phone" type="text" placeholder="Enter your phone number" class="input-xlarge" pattern="^\+[1-9][0-9]?[\s]*\(?\d{3}\)?[-\s]?\d{3}[-\s]?\d{2}[-\s]?\d{2}$"/>
									</div>
								</div>
								<div class="control-group">
									<p>Now that we know who you are. I'm not a mistake! In a comic, you know how you can tell who the arch-villain's going to be?</p>
								</div>
								<hr>
								<div class="actions"><input tabindex="9" class="btn btn-inverse large" type="submit" value="Create new account"></div>
							</fieldset>
						</spring:form>
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
		<script>
			$(document).ready(function() {
				$('#checkout').click(function (e) {
					document.location.href = "checkout.jsp";
				})
			});
		</script>		
    </body>
</html>