<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>My Account</title>
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
	<script src="${pageContext.request.contextPath}/resources/themes/js/jquery.scrolltotop.js"></script>
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
			<form method="POST" class="search_form">
				<input type="text" class="input-block-level search-query" Placeholder="Search">
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
                        //							</script>
					<li><a href="${pageContext.request.contextPath}clients/profile">My Account</a></li>

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
						<li><a href="${pageContext.request.contextPath}clients/identification">Login</a></li>
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
					<li><a href="${pageContext.request.contextPath}/catalog">Catalog</a>
						<ul>
							<c:forEach var="categoryVar"  items="${listCategory}">
								<li><a href="${pageContext.request.contextPath}/catalog/${categoryVar.name}/page/${1}">${categoryVar.name}</a></li>
							</c:forEach>
						</ul>
					</li>
					<li><a href="goods.jsp">Best Sellers</a>
					<li><a href="goods.jsp">How To Buy</a></li>
					<li><a href="goods.jsp">F.A.Q</a></li>
					<li><a href="goods.jsp">About us</a></li>
				</ul>
			</nav>
		</div>
	</section>
	<section class="header_text sub">
		<img class="pageBanner" src="/resources/themes/images/pageBanner.png" alt="" >
				<h4><span>${client.name.toUpperCase()}, its your profile</span></h4>
				<c:if test="${client.role.name!='ROLE_CLIENT'}" >
					<br>
					<h4><span>Your role is: ${client.role.name}</span></h4>
				</c:if>
			</section>
			<c:if test="${not empty error}">
				<div class="error">${error}</div>
			</c:if>
			<section class="main-content">
				<div class="row">
					<div class="span12">
						<div class="accordion" id="accordion2">
							<div class="accordion-group">
								<div class="accordion-heading">
									<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseOne">Account Details</a>
								</div>
								<div id="collapseOne" class="accordion-body collapse">
									<div class="accordion-inner">
										<spring:form action="edit" method="post" modelAttribute="client" class="form-stacked">
										<div class="row-fluid">
											<div class="span6">
												<h4>Your Personal Details</h4>
													<fieldset>
														<div class="control-group">
															<label class="control-label">Your ID</label>
															<div class="controls">
																<spring:input path="id" readonly="true" type="text" placeholder="" class="input-xlarge"/>
															</div>
														</div>
														<div class="control-group">
															<label class="control-label">First Name*</label>
															<div class="controls">
																<spring:input path="name" type="text" placeholder="" class="input-xlarge"/>
															</div>
														</div>
														<div class="control-group">
															<label class="control-label">Last Name</label>
															<div class="controls">
																<spring:input path="surname" type="text" placeholder="" class="input-xlarge"/>
															</div>
														</div>
														<%--<div class="control-group">--%>
															<%--<label class="control-label">Birthday</label>--%>
															<%--<div class="controls">--%>
																<%--<spring:input path="birthdate" type="date" placeholder="" class="input-xlarge"/>--%>
															<%--</div>--%>
														<%--</div>--%>
														<div class="control-group">
															<label class="control-label">Email Address*</label>
															<div class="controls">
																<spring:input path="email" type="text" placeholder="" class="input-xlarge"/>
															</div>
														</div>

														<div class="control-group">
															<label class="control-label">Phone number</label>
															<div class="controls">
																<spring:input path="phone" type="text" placeholder="" class="input-xlarge"/>
															</div>
														</div>
													</fieldset>
											</div>
											<div class="span6">
												<h4>Your Address</h4>
												<fieldset>
													<div class="control-group">
														<label class="control-label"><span class="required"></span> Country:</label>
														<div class="controls">
															<spring:select path="clientAddress.country" class="input-xlarge">
																<spring:option value="">---Please Select---</spring:option>
																<spring:option value="AFG">Afghanistan</spring:option>
																<spring:option value="ALB">Albania</spring:option>
																<spring:option value="ALG">Algeria</spring:option>
																<spring:option value="ASA">American Samoa</spring:option>
																<spring:option value="AND">Andorra</spring:option>
																<spring:option value="ANG">Angola</spring:option>
															</spring:select>
														</div>
													</div>
													<div class="control-group">
														<label class="control-label"><span class="required"></span> Region / State:</label>
														<div class="controls">
															<spring:input path="clientAddress.city" type="text" placeholder="" class="input-xlarge"/>
														</div>
													</div>
													<div class="control-group">
														<label class="control-label"><span class="required"></span> Post Code:</label>
														<div class="controls">
															<spring:input path="clientAddress.postcode" type="text" placeholder="" class="input-xlarge"/>
														</div>
													</div>
													<div class="control-group">
														<label class="control-label"><span class="required"></span> Street:</label>
														<div class="controls">
															<spring:input path="clientAddress.street" type="text" placeholder="" class="input-xlarge"/>
														</div>
													</div>
													<div class="control-group">
														<label class="control-label"><span class="required"></span> House:</label>
														<div class="controls">
															<spring:input path="clientAddress.house" type="text" placeholder="" class="input-xlarge"/>
														</div>
													</div>
													<div class="control-group">
														<label class="control-label"><span class="required"></span> Flat:</label>
														<div class="controls">
															<spring:input path="clientAddress.flat" type="text" placeholder="" class="input-xlarge"/>
														</div>
													</div>
													<div class="control-group">
														<label class="control-label"><span class="required"></span> Additional Information:</label>
														<div class="controls">
															<spring:input path="clientAddress.addition" type="text" placeholder="" class="input-xlarge"/>
														</div>
													</div>
												</fieldset>
												<div class="actions"><input tabindex="9" class="btn btn-inverse large" type="submit" value="Edit your profile"></div>
											</div>
										</div>
											<hr>
										</spring:form>
										<div class="row-fluid">
											<div class="span6">
												<h4>Set New Password</h4>
												<spring:form action="password" method="post" modelAttribute="passwordField" class="form-stacked">
													<fieldset>
														<div class="control-group">
															<label class="control-label">Old Password*</label>
															<div class="controls">
																<spring:input path="password" type="password" placeholder="" class="input-xlarge" />
															</div>
														</div>
														<hr>
														<div class="control-group">
															<label class="control-label">New Password*</label>
															<div class="controls">
																<spring:input path="newPasswordFirst" type="password" placeholder="" class="input-xlarge" pattern="^[a-zA-Z0-9]+$"/>
															</div>
														</div>
														<div class="control-group">
															<label class="control-label">Old Password*</label>
															<div class="controls">
																<spring:input path="newPasswordSecond" type="password" placeholder="" class="input-xlarge" pattern="^[a-zA-Z0-9]+$"/>
															</div>
														</div>
													</fieldset>
												</spring:form>
											</div>

										</div>
											<%--<input type="hidden"--%>
												   <%--name="${_csrf.parameterName}"--%>
												   <%--value="${_csrf.token}"/>--%>

									</div>
								</div>
							</div>
							<c:if test="${client.role.name=='ROLE_CLIENT'}">
								<div class="accordion-group">
									<div class="accordion-heading">
										<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">Orders History</a>
									</div>
									<div id="collapseTwo" class="accordion-body collapse">
										<div class="accordion-inner">

												<h4>Your Orders History</h4>
												<c:if test="${clientOrdersList.size()!=0}">
												<table class="table table-striped">
													<thead>
													<tr>
														<th><big>Order ID:</big></th>
														<th><big>Delivery Method</big></th>
														<th><big>Payment Type</big></th>
														<th><big>Pay Status</big></th>
														<th><big>Order Status:</big></th>
														<th><big>Order Details</big></th>
													</tr>
													</thead>
													<c:forEach var="clientOrder"  items="${clientOrdersList}">
													<tbody>
													<tr>
														<td><big>${clientOrder.id}</big></td>
														<td><big>${clientOrder.deliveryMethod.name}</big></td>
														<td><big>${clientOrder.paymentType.name}</big></td>
														<c:if test="${clientOrder.payStatus==1}">
														<td><big>paid</big></td>
														</c:if>
														<c:if test="${clientOrder.payStatus==0}">
														<td><big>pending payment</big></td>
														</c:if>
														<td><big>${clientOrder.status.name}</big></td>
														<td><a href="">Details</a></td>
													</tr>
													</tbody>
														<%--<hr>--%>
														<%--<br>--%>
													</c:forEach>
												</table>
												</c:if>

										</div>
									</div>
								</div>
							</c:if>
							<c:if test="${client.role.name!='ROLE_CLIENT'}">
								<div class="accordion-group">
									<div class="accordion-heading">
										<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseThree">Show All Orders</a>
									</div>
									<div id="collapseThree" class="accordion-body collapse">
										<div class="accordion-inner">

											<h4>All Orders</h4>
											<c:if test="${clientOrdersList.size()!=0}">
												<table class="table table-striped">
													<thead>
													<tr>
														<th><big>Order ID:</big></th>
														<th><big>Client ID:</big></th>
														<th><big>Payment Type:</big></th>
														<th><big>Pay Status:</big></th>
														<th><big>Order Status:</big></th>
														<th><big>Additional Information:</big></th>
														<th><big>Order Details:</big></th>
													</tr>
													</thead>

													<c:forEach var="clientOrder"  items="${clientOrdersList}">
														<tbody>
														<tr>
															<td><big>${clientOrder.id}</big></td>
															<td><big>${clientOrder.client.id}</big></td>
															<td><big>${clientOrder.paymentType.name}</big></td>
															<c:if test="${clientOrder.payStatus==1}">
																<td><big>paid</big></td>
															</c:if>
															<c:if test="${clientOrder.payStatus==0}">
																<td><big>pending payment</big></td>
															</c:if>
															<td><big>${clientOrder.status.name}</big></td>
															<td><big>${clientOrder.comment}</big></td>
															<td><a href="/catalog/profile/employee/details/order/${clientOrder.id}"><big>Details</big></a></td>
														</tr>
														<%--<hr>--%>
														<%--<br>--%>
													</tbody>
													</c:forEach>

												</table>
											</c:if>

										</div>
									</div>
								</div>
							</c:if>
						</div>				
					</div>
				</div>
			</section>			
			<section id="footer-bar">
				<div class="row">
					<div class="span3">
						<h4>Navigation</h4>
						<ul class="nav">
							<li><a href="index.jsp">Homepage</a></li>
							<li><a href="./about.html">About Us</a></li>
							<li><a href="./contact.html">Contac Us</a></li>
							<li><a href="cart.jsp">Your Cart</a></li>
							<li><a href="register.jsp">Login</a></li>
						</ul>					
					</div>
					<div class="span4">
						<h4>My Account</h4>
						<ul class="nav">
							<li><a href="#">My Account</a></li>
							<li><a href="#">Order History</a></li>
							<li><a href="#">Wish List</a></li>
							<li><a href="#">Newsletter</a></li>
						</ul>
					</div>
					<div class="span5">
						<p class="logo"><img src="/resources/themes/images/logo.png" class="site_logo" alt=""></p>
						<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. the  Lorem Ipsum has been the industry's standard dummy text ever since the you.</p>
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