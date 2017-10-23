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
							</script>
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
							<li><a href="${pageContext.request.contextPath}/catalog">Catalog</a>
								<ul>
									<c:forEach var="categoryVar"  items="${listCategory}">
										<li><a href="${pageContext.request.contextPath}/catalog/${categoryVar.name}/page/${1}">${categoryVar.name}</a></li>
									</c:forEach>
								</ul>
							</li>
							<li><a href="#">Best Sellers</a>
							<li><a href="#">How To Buy</a></li>
							<li><a href="#">F.A.Q</a></li>
							<li><a href="#">About us</a></li>
						</ul>
					</nav>
				</div>
			</section>
			<section class="header_text sub">
				<img class="pageBanner" src="/resources/themes/images/pageBanner.png" alt="Product Detail" >
				<h3><span>Product Detail</span></h3>
			</section>
			<section class="main-content">				
				<div class="row">						
					<div class="span9">
						<div class="row">
						<div class="span4">
							<a href="${goods.img}" class="thumbnail" data-fancybox-group="group1" title="Description 1"><img alt="" src="${goods.img}"></a>
							<ul class="thumbnails small">
								<li class="span1">
									<a href="${goods.img}" class="thumbnail" data-fancybox-group="group1" title="Description 2"><img src="${goods.img}" alt=""></a>
								</li>
								<li class="span1">
									<a href="${goods.img}" class="thumbnail" data-fancybox-group="group1" title="Description 3"><img src="${goods.img}" alt=""></a>
								</li>
								<li class="span1">
									<a href="${goods.img}" class="thumbnail" data-fancybox-group="group1" title="Description 4"><img src="${goods.img}" alt=""></a>
								</li>
								<li class="span1">
									<a href="${goods.img}" class="thumbnail" data-fancybox-group="group1" title="Description 5"><img src="${goods.img}" alt=""></a>
								</li>
							</ul>
						</div>
						<c:if test="${client.role.name=='ROLE_EMPLOYEE'}" >
							<div class="span5" id="user">
								<c:if test="${not empty msg}">
									<div class="msg">${msg}</div>
								</c:if>

								<c:if test="${not empty error}">
									<div class="error">${error}</div>
								</c:if>
								<spring:form action="/catalog/employee/edit" method="post" commandName="goods" class="form-stacked" >
									<fieldset>
										<div class="control-group">
											<label class="control-label">Id:</label>
											<div class="controls">
												<spring:input path="id" readonly="true" type="text" placeholder="Enter name of goods" class="input-xlarge" />
											</div>
										</div><div class="control-group">
											<label class="control-label">Name:</label>
											<div class="controls">
												<spring:input path="name" type="text" placeholder="Enter name of goods" class="input-xlarge" />
												<spring:errors path="name" cssClass="error"/>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">Price:</label>
											<div class="controls">
												<spring:input path="price" type="text" placeholder="Enter price" class="input-xlarge" pattern="\d+(\.\d{1})?"/>
												<spring:errors path="price" cssClass="error"/>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">Number Of Players:</label>
											<div class="controls">
												<spring:select path="numberOfPlayers" class="input-xlarge">
													<c:forEach   begin = "1" end = "10"  varStatus="count">
														<spring:option value="${count.index}">${count.index}</spring:option>
													</c:forEach>
												</spring:select>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">Duration Of The Game:</label>
											<div class="controls">
												<spring:select path="duration" class="input-xlarge">
													<c:forEach  begin = "1" end = "8"  varStatus="count">
														<spring:option value="${count.index/2.0}">${count.index/2.0}</spring:option>
													</c:forEach>
												</spring:select>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">Amount:</label>
											<div class="controls">
												<spring:input path="amount" type="text" placeholder="Enter the quantity of goods" class="input-xlarge" pattern="^[ 0-9]+$"/>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">Is Visible:</label>
											<div class="controls">
												<spring:select path="visible" class="input-xlarge">
													<spring:option value="1">Yes</spring:option>
													<spring:option value="0">No</spring:option>
												</spring:select>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">Description:</label>
											<div class="controls">
												<spring:textarea path="description" cols="20" rows="5"/>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">Complexity Of Rules:</label>
											<div class="controls">
												<spring:select path="rule.name" class="input-xlarge">
													<spring:option value="${'easy'}">Easy</spring:option>
													<spring:option value="${'very easy'}">Very Easy</spring:option>
													<spring:option value="${'medium'}">Medium</spring:option>
													<spring:option value="${'hard'}">Hard</spring:option>
													<spring:option value="${'very hard'}">Very Hard</spring:option>
												</spring:select>

											</div>
										</div>
										<div class="control-group">
											<label class="control-label">Category Name:</label>
											<div class="controls">
												<spring:select path="category.name" class="input-xlarge">
													<c:forEach var="categoryVar"  items="${listCategory}">
														<spring:option value="${categoryVar.name}">${categoryVar.name}</spring:option>
													</c:forEach>
												</spring:select>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">Image:</label>
											<div class="controls">
												<spring:input path="img" type="text" placeholder="Put URL of image here" class="input-xlarge"/>
											</div>
										</div>
										<hr>
										<div class="actions"><input tabindex="9" class="btn btn-inverse large" type="submit" value="Edit goods"></div>
									</fieldset>
									<%--<input type="hidden"--%>
										   <%--name="${_csrf.parameterName}"--%>
										   <%--value="${_csrf.token}"/>--%>
								</spring:form>
								<form action="/catalog/employee/delete/${goods.id}">
									<button type="submit" class="btn btn-inverse">Delete goods</button>
								</form>
							</div>
						</div>
						</c:if>
						<c:if test="${client.role.name!='ROLE_EMPLOYEE'}" >
							<div class="span5">
								<address>
									<el style="text-decoration: underline"><h3><strong>${goods.name}</strong></h3></el>
									<table border="3" width="100%" cellpadding="3" cellspacing="2">
									<tr>
										<td><big><big><strong>Number of players:</strong></big></big></td>
										<td><big><strong>${goods.numberOfPlayers}</strong></big></td>
									</tr>
									<tr>
										<td><big><big><strong>Duration of the game:</strong></big></big></td>
										<td><big><strong>${goods.duration}</strong></big></td>
									</tr>
									<tr>
										<td><big><big><strong>Complexity of the rules:</strong></big></big></td>
										<td><big><strong>${goods.rule.name}</strong></big></td>
									</tr>
									<tr>
										<td><big><big><strong>Availability:</strong></big></big></td>
										<c:if test="${goods.amount==0}" >
										<td><big><strong>Out Of Stock</strong></big></td>
										</c:if>
										<c:if test="${goods.amount!=0}" >
										<td><big><strong>Available</strong></big></td>
										</c:if>
									</tr>
									 <%--<br>--%>
									<%--<c:if test="${goods.amount==0}" >--%>
										<%--<h5><strong>Availability:</strong><span>Out Of Stock</span></h5><br>--%>
									<%--</c:if>--%>
									<%--<c:if test="${goods.amount!=0}" >--%>
										<%--<h5><strong>Availability:</strong></h5> <h5><span>Available</span></h5><br>--%>
									<%--</c:if>--%>
									</table>
								</address>
								<h4><strong>Price: ${goods.price} &#8381;</strong></h4>
							</div>
							<div class="span5">
								<%--<form  class="form-inline">--%>
									<%--<fieldset>--%>
										<%--<div class="control-group">--%>
											<%--<p>&nbsp;</p>--%>
											<%--<label>Qty:</label>--%>
											<%--<div class="controls">--%>
												<%--<input path="quantity" type="text" placeholder="1" class="span1"/>--%>
												<%--<spring:errors path="name" cssClass="error"/>--%>
											<%--</div>--%>
										<%--</div>--%>
										<%--<div class="actions"><input tabindex="9" class="btn btn-inverse large" type="submit" value="Add to cart"></div>--%>
									<%--</fieldset>--%>
								<%--</form>--%>
								<spring:form action="/catalog/goods/cart/${goods.id}/add" method="post" commandName="cartItem" class="form-inline">
									<p>&nbsp;</p>
									<label>Qty:</label>
									<spring:input path="quantity" type="text" class="span1" placeholder="1" pattern="^[1-9]+$" title="Amount must be a integer and more then zero"/>
									<button class="btn btn-inverse" type="submit">Add to cart</button>
								</spring:form>
							</div>							
						</div>
						</c:if>
						<div class="row">
							<div class="span9">
								<ul class="nav nav-tabs" id="myTab">
									<li class="active"><a href="#home">Description</a></li>
									<li class=""><a href="#profile">Additional Information</a></li>
								</ul>							 
								<div class="tab-content">
									<div class="tab-pane active" id="home">${goods.description}</div>
									<div class="tab-pane" id="profile">
										<table class="table table-striped shop_attributes">	
											<tbody>
												<tr class="">
													<th>Size</th>
													<td>Large, Medium, Small, X-Large</td>
												</tr>		
												<tr class="alt">
													<th>Colour</th>
													<td>Orange, Yellow</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>							
							</div>						
							<div class="span9">	
								<br>
								<h4 class="title">
									<span class="pull-left"><span class="text"><strong>Related</strong> Products</span></span>
									<span class="pull-right">
										<a class="left button" href="#myCarousel-1" data-slide="prev"></a><a class="right button" href="#myCarousel-1" data-slide="next"></a>
									</span>
								</h4>
								<div id="myCarousel-1" class="carousel slide">
									<div class="carousel-inner">
										<div class="active item">
											<ul class="thumbnails listing-products">
												<c:forEach var="goodsVar"  begin = "0" end = "2" items="${randomGoods}">
												<li class="span3">
													<div class="product-box">
														<span class="sale_tag"></span>												
														<a href="${pageContext.request.contextPath}/catalog/goods/${goodsVar.id}"><img alt="" src="${goodsVar.img}"></a><br/>
														<a href="${pageContext.request.contextPath}/catalog/goods/${goodsVar.id}">${goodsVar.name}</a><br/>
														<a href="#" class="category">${goodsVar.category.name}</a>
														<p class="price">${goodsVar.price} &#8381;</p>
													</div>
												</li>
												</c:forEach>

											</ul>
										</div>
										<div class="item">
											<ul class="thumbnails listing-products">
												<c:forEach var="goodsVar"  begin = "3" end = "5" items="${randomGoods}">
													<li class="span3">
														<div class="product-box">
															<span class="sale_tag"></span>
															<a href="${pageContext.request.contextPath}/catalog/goods/${goodsVar.id}"><img alt="" src="${goodsVar.img}"></a><br/>
															<a href="${pageContext.request.contextPath}/catalog/goods/${goodsVar.id}">${goodsVar.name}</a><br/>
															<a href="#" class="category">${goodsVar.category.name}</a>
															<p class="price">${goodsVar.price} &#8381;</p>
														</div>
													</li>
												</c:forEach>

											</ul>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="span3 col">
						<div class="block">
							<c:if test="${client.role.name=='ROLE_EMPLOYEE'}" >
								<form action="/catalog/employee/add">
									<button type="submit" class="btn btn-inverse">Add new Goods or Category</button>
								</form>
							</c:if>
							<ul class="nav nav-list">
								<li class="nav-header">SUB CATEGORIES</li>
								<li
										<c:if test="${!categoryFilter}" >
											class="active"
										</c:if>
								><a href="${pageContext.request.contextPath}/catalog">All games</a></li>
								<c:forEach var="categoryVar"  items="${listCategory}">
									<li
											<c:if test="${categoryVar.name == categoryName}" >
												class="active"
											</c:if>
									><a href="${pageContext.request.contextPath}/catalog/${categoryVar.name}/page/${1}">${categoryVar.name}</a></li>
								</c:forEach>
							</ul>
							<br/>
						</div>
						<div class="block">
							<h4 class="title">
								<span class="pull-left"><span class="text">Randomize</span></span>
								<span class="pull-right">
									<a class="left button" href="#myCarousel" data-slide="prev"></a><a class="right button" href="#myCarousel" data-slide="next"></a>
								</span>
							</h4>
							<div id="myCarousel" class="carousel slide">
								<div class="carousel-inner">
									<div class="active item">
										<ul class="thumbnails listing-products">
											<c:forEach var="goodsVar"  begin = "3" end = "3" items="${randomGoods}">
												<li class="span3">
													<div class="product-box">
														<span class="sale_tag"></span>
														<a href="${pageContext.request.contextPath}/catalog/goods/${goodsVar.id}"><img alt="" src="${goodsVar.img}"></a><br/>
														<a href="${pageContext.request.contextPath}/catalog/goods/${goodsVar.id}">${goodsVar.name}</a><br/>
														<a href="#" class="category">${goodsVar.category.name}</a>
														<p class="price">${goodsVar.price} &#8381;</p>
													</div>
												</li>
											</c:forEach>
										</ul>
									</div>
									<div class="item">
										<ul class="thumbnails listing-products">
											<c:forEach var="goodsVar"  begin = "4" end = "4" items="${randomGoods}">
												<li class="span3">
													<div class="product-box">
														<span class="sale_tag"></span>
														<a href="${pageContext.request.contextPath}/catalog/goods/${goodsVar.id}"><img alt="" src="${goodsVar.img}"></a><br/>
														<a href="${pageContext.request.contextPath}/catalog/goods/${goodsVar.id}">${goodsVar.name}</a><br/>
														<a href="#" class="category">${goodsVar.category.name}</a>
														<p class="price">${goodsVar.price} &#8381;</p>
													</div>
												</li>
											</c:forEach>
										</ul>
									</div>
								</div>
							</div>
						</div>
						<div class="block">								
							<h4 class="title"><strong>Best</strong> Seller</h4>								
							<ul class="small-product">
								<c:forEach var="goodsVar"  begin = "0" end = "2" items="${randomGoods}">
									<li>
										<a href="${pageContext.request.contextPath}/catalog/goods/${goodsVar.id}" title="${goodsVar.name}">
											<img src="${goodsVar.img}" alt="">
										</a>
											<a href="${pageContext.request.contextPath}/catalog/goods/${goodsVar.id}">${goodsVar.name}</a>
									</li>
								</c:forEach>
							</ul>
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
							<li><a href="#">About Us</a></li>
							<li><a href="#">Contac Us</a></li>
							<c:if test="${cartList==null}">
								<li><a href="cart.jsp">Your Cart(0)</a></li>
							</c:if>
							<c:if test="${cartList!=null}">
							<li><a href="cart.jsp">Your Cart(${cartList.size()})</a></li>
							</c:if>
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
		<script>
			$(function () {
				$('#myTab a:first').tab('show');
				$('#myTab a').click(function (e) {
					e.preventDefault();
					$(this).tab('show');
				})
			})
			$(document).ready(function() {
				$('.thumbnail').fancybox({
					openEffect  : 'none',
					closeEffect : 'none'
				});
				
				$('#myCarousel-2').carousel({
                    interval: 2500
                });								
			});
		</script>
    </body>
</html>