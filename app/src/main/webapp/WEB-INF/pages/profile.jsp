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
	<link href="${pageContext.request.contextPath}/resources/themes/bootstrap/css/bootstrap.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resources/themes/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">

	<link href="${pageContext.request.contextPath}/resources/themes/css/bootstrappage.css" rel="stylesheet"/>

	<!-- global styles -->
	<link href="${pageContext.request.contextPath}/resources/themes/css/flexslider.css" rel="stylesheet"/>
	<link href="${pageContext.request.contextPath}/resources/themes/css/main.css" rel="stylesheet"/>
	<script src="${pageContext.request.contextPath}/resources/themes/js/jquery-1.7.2.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/themes/js/jquery.fancybox.js"></script>
	<script type="text/javascript">
        $(document).ready(function () {
            $('#cross').click(function () {
                    $.ajax({
                        type: 'GET',
                        url: "/clients/profile/edit/avatar/delete",
                        dataType: 'json',
                        success: function () {
                            document.getElementById("default_img").style.display = 'block';
                            document.getElementById("old_img").style.display = 'none';
                            document.getElementById("cross").style.display = 'none';
                            document.getElementById("row").style.display = 'none';
                        }
                    });
            });
        });
	</script>
	<!-- scripts -->
	<script src="${pageContext.request.contextPath}/resources/themes/js/jquery-1.7.2.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/themes/bootstrap/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/themes/js/superfish.js"></script>
	<!--[if lt IE 9]>
	<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<script src="/resources/themes/js/respond.min.js"></script>


	<![endif]-->
	<style>

		html, body{
			min-height:100%;
		}

		body{
			background-image: -webkit-linear-gradient(top, #edecec, #cecbc9);
			background-image: linear-gradient(top, #edecec, #cecbc9);
		}

		.buttonHolder{
			margin:23px auto;
			width:23px;
		}


		.button{
			background-image: -webkit-linear-gradient(top, #f4f1ee, #fff);
			background-image: linear-gradient(top, #f4f1ee, #fff);
			border-radius: 50%;
			box-shadow: 0px 8px 10px 0px rgba(0, 0, 0, .3), inset 0px 4px 1px 1px white, inset 0px -3px 1px 1px rgba(204,198,197,.5);
			float:left;
			height: 20px;
			margin: 0 8px 8px 0;
			position: relative;
			width: 20px;
			-webkit-transition: all .1s linear;
			transition: all .1s linear;
		}

		.button:after{
			color:#e9e6e4;
			content: "";
			display: block;
			font-size: 15px;
			height: 15px;
			text-decoration: none;
			text-shadow: 0px -1px 1px #bdb5b4, 1px 1px 1px white;
			position: absolute;
			width: 15px;
		}


		.heart:after{
			content: "❤";
			left: 6px;
			top: 4px;
		}

		.flower:after{
			content: "\270E";
			left: 3px;
			top: 0px;
		}

		.tick:after{
			content: "✔";
			left:6px;
			top:4px;
		}

		.cross:after{
			content: "\2716";
			left: 3px;
			top: 1px;
		}

		.button:hover{
			background-image: -webkit-linear-gradient(top, #fff, #f4f1ee);
			background-image: linear-gradient(top, #fff, #f4f1ee);
			color:#0088cc;
		}

		.heart:hover:after{
			color:#f94e66;
			text-shadow:0px 0px 6px #f94e66;
		}

		.flower:hover:after{
			color:#f99e4e;
			text-shadow:0px 0px 6px #f99e4e;
		}

		.tick:hover:after{
			color:#83d244;
			text-shadow:0px 0px 6px #83d244;
		}

		.cross:hover:after{
			color:#eb2f2f;
			text-shadow:0px 0px 6px #eb2f2f;
		}



		.button:active{
			background-image: -webkit-linear-gradient(top, #efedec, #f7f4f4);
			background-image: linear-gradient(top, #efedec, #f7f4f4);
			box-shadow: 0 3px 5px 0 rgba(0,0,0,.4), inset 0px -3px 1px 1px rgba(204,198,197,.5);
		}

		.button:active:after{
			color:#dbd2d2;
			text-shadow: 0px -1px 1px #bdb5b4, 0px 1px 1px white;
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

		.btn-file {
			position: relative;
			overflow: hidden;
		}
		.btn-file input[type=file] {
			position: absolute;
			top: 0;
			right: 0;
			min-width: 100%;
			min-height: 100%;
			font-size: 50%;
			text-align: right;
			filter: alpha(opacity=0);
			opacity: 0;
			outline: none;
			background: orangered;
			cursor: inherit;
			display: block;
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
			<c:if test="${not empty errorMatch}">
				<div class="error">${errorMatch}</div>
			</c:if>
			<c:if test="${not empty errorInvalidPass}">
				<div class="error">${errorInvalidPass}</div>
			</c:if>
	<c:if test="${not empty msg}">
				<div class="msg">${msg}</div>
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

										<spring:form action="/clients/profile/edit" method="post" modelAttribute="client" class="form-stacked">
										<div class="row-fluid">
											<div class="span6">
												<h4>Your Personal Details</h4>
												<c:if test="${not empty clientError}">
													<div class="error">${clientError}</div>
												</c:if>
												<c:if test="${not empty msgClient}">
													<div class="msg">${msgClient}</div>
												</c:if>

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
																<spring:input path="name" type="text" placeholder="" maxlength="30" class="input-xlarge" pattern="^[a-zA-Z0-9_]*$"/>
																<spring:errors path="name" cssClass="error"/>
															</div>
														</div>
														<div class="control-group">
															<label class="control-label">Last Name</label>
															<div class="controls">
																<spring:input path="surname" type="text" placeholder="" maxlength="30" class="input-xlarge" pattern="^[a-zA-Z0-9_]*$"/>
															</div>
														</div>
														<div class="control-group">
															<label class="control-label">Birthday</label>
															<div class="controls">
																<spring:input path="birthdate" type="date" placeholder="" class="input-xlarge" min="1900-01-01" max="2017-01-01"/>
															</div>
														</div>
														<div class="control-group">
															<label class="control-label">Email Address*</label>
															<div class="controls">
																<spring:input path="email" type="text" placeholder="" maxlength="30" class="input-xlarge" pattern="^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]+$"/>
																<spring:errors path="email" cssClass="error"/>
															</div>
														</div>

														<div class="control-group">
															<label class="control-label">Phone number</label>
															<div class="controls">
																<spring:input path="phone" type="text" placeholder="" class="input-xlarge" pattern="^\+[1-9][0-9]?[\s]*\(?\d{3}\)?[-\s]?\d{3}[-\s]?\d{2}[-\s]?\d{2}$"/>
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
																<spring:option value="ALA">Åland Islands</spring:option>
																<spring:option value="ALB">Albania</spring:option>
																<spring:option value="DZA">Algeria</spring:option>
																<spring:option value="ASM">American Samoa</spring:option>
																<spring:option value="FRO">Faroe Islands</spring:option>
																<spring:option value="FJI">Fiji</spring:option>
																<spring:option value="FIN">Finland</spring:option>
																<spring:option value="FRA">France</spring:option>
																<spring:option value="GUF">French Guiana</spring:option>
																<spring:option value="PRT">Portugal</spring:option>
																<spring:option value="PRI">Puerto Rico</spring:option>
																<spring:option value="QAT">Qatar</spring:option>
																<spring:option value="REU">Réunion</spring:option>
																<spring:option value="ROU">Romania</spring:option>
																<spring:option value="RUS">Russian Federation</spring:option>
																<spring:option value="RWA">Rwanda</spring:option>
																<spring:option value="BLM">Saint Barthélemy</spring:option>
																<spring:option value="SHN">Saint Helena, Ascension and Tristan da Cunha</spring:option>
																<spring:option value="KNA">Saint Kitts and Nevis</spring:option>
																<spring:option value="LCA">Saint Lucia</spring:option>
															</spring:select>
														</div>
													</div>
													<div class="control-group">
														<label class="control-label"><span class="required"></span> Region / State:</label>
														<div class="controls">
															<spring:input path="clientAddress.city" type="text" maxlength="30" placeholder="" class="input-xlarge" pattern="^[a-zA-Z]+(?:[\s-][a-zA-Z]+)*$"/>
														</div>
													</div>
													<div class="control-group">
														<label class="control-label"><span class="required"></span> Post Code:</label>
														<div class="controls">
															<spring:input path="clientAddress.postcode" maxlength="30" type="text" placeholder="" class="input-xlarge" pattern="^[ 0-9]+$"/>
														</div>
													</div>
													<div class="control-group">
														<label class="control-label"><span class="required"></span> Street:</label>
														<div class="controls">
															<spring:input path="clientAddress.street" type="text" maxlength="30" placeholder="" class="input-xlarge" pattern="^[a-zA-Z0-9_]*$"/>
														</div>
													</div>
													<div class="control-group">
														<label class="control-label"><span class="required"></span> House:</label>
														<div class="controls">
															<spring:input path="clientAddress.house" maxlength="10" type="text" placeholder="" class="input-xlarge" pattern="^[a-zA-Z0-9_]*$"/>
														</div>
													</div>
													<div class="control-group">
														<label class="control-label"><span class="required"></span> Flat:</label>
														<div class="controls">
															<spring:input path="clientAddress.flat" maxlength="10" type="text" placeholder="" class="input-xlarge" pattern="^[a-zA-Z0-9_]*$"/>
														</div>
													</div>
													<div class="control-group">
														<label class="control-label"><span class="required"></span> Additional Information:</label>
														<div class="controls">
															<spring:input path="clientAddress.addition" maxlength="500" type="text" placeholder="" class="input-xlarge"/>
														</div>
													</div>
												</fieldset>
												<div class="actions"><input tabindex="9" class="btn btn-inverse large" type="submit" value="Edit your profile"></div>
												</spring:form>

											</div>
										</div>
											<hr>
										<div class="row-fluid">
											<div class="span6">
												<h4>Set New Password</h4>
												<spring:form action="/clients/profile/edit/password" method="post" modelAttribute="passwordField" class="form-stacked">
													<fieldset>
														<div class="control-group">
															<label class="control-label">Old Password*</label>
															<div class="controls">
																<spring:input path="password" type="password" placeholder="" class="input-xlarge" />
															</div>
														</div>
														<%--<hr>--%>
														<div class="control-group">
															<label class="control-label">New Password*</label>
															<div class="controls">
																<spring:input path="newPasswordFirst" type="password" placeholder="" class="input-xlarge" pattern="^[a-zA-Z0-9]+$"/>
															</div>
														</div>
														<div class="control-group">
															<label class="control-label">Repeat New Password*</label>
															<div class="controls">
																<spring:input path="newPasswordSecond" type="password" placeholder="" class="input-xlarge" pattern="^[a-zA-Z0-9]+$"/>
															</div>
														</div>
													</fieldset>
													<div class="actions"><input tabindex="9" class="btn btn-inverse large" type="submit" value="Edit your password"></div>
												</spring:form>
											</div>
											<div class="span6">
												<h4>Set Profile Image</h4>

												<fieldset>
														<div class="control-group">
															<c:if test="${client.img != null}">
															<div id="row" class="row">
																<div class="buttonHolder" style="display: inline">
																	<a id="cross" class="button cross" onclick="return confirm('Do you want to delete your profile photo?')"></a>
																</div>
																<label class="control-label">Your Profile Image</label>
															</div>
															</c:if>
															<c:if test="${client.img == null}">
															<label class="control-label">Your Profile Image</label>
															</c:if>
															<div class="controls" >
																<c:if test="${client.img == null}">
																	<img src="/resources/themes/images/img_avatar.png" class="media-object" style="width:100px">
																</c:if>
																<c:if test="${client.img != null}">
																	<img id="old_img" alt="img" src="data:image/jpeg;base64,${client.imgBase64}"  style="height: 150px; width: 150px;"/>
																</c:if>
																<img id="default_img" src="/resources/themes/images/img_avatar.png" class="media-object" style="width:100px; display: none">
																<form method="post" action="/clients/profile/edit/avatar" enctype="multipart/form-data">
																	<span class="btn btn-small btn-file">
																		Browse <input type="file"  name="fileUpload">
																	</span>
															</div>
														</div>
													</fieldset>
												<div class="actions"><input class="btn btn-inverse pull-left" type="submit" value="Upload" /></div>
												</form>
												<br>
												<br>
												<c:if test="${not empty msg_img}">
													<div class="error">${msg_img}</div>
												</c:if>
											</div>
										</div>
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
														<th><big>Date:</big></th>
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
														<td><big>Paid</big></td>
														</c:if>
														<c:if test="${clientOrder.payStatus==0}">
														<td><big>Pending payment</big></td>
														</c:if>
														<td>
															<c:if test="${clientOrder.status.name=='canceled'}">
																<span class="label label-danger">Canceled</span>
															</c:if>
															<c:if test="${clientOrder.status.name=='awaiting shipment'}">
																<span class="label label-default">Awaiting Shipment</span>
															</c:if>
															<c:if test="${clientOrder.status.name=='shipped'}">
																<span class="label label-info">Shipped</span>
															</c:if>
															<c:if test="${clientOrder.status.name=='closed'}">
																<span class="label label-success">Closed</span>
															</c:if>
															<%--<big>${clientOrder.status.name}</big>--%>
														</td>
														<td><big>${clientOrder.date}</big></td>
														<td><a href="/order/details/${clientOrder.id}"><big>Details</big></a></td>
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
						</div>				
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