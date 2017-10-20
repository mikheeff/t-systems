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

			#categoryInput {
				width: 200px; /* Ширина поля в пикселах */
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
							<c:if test="${cartList==null}">
								<li><a href="/catalog/goods/cart">Your Cart(0)</a></li>
							</c:if>
							<c:if test="${cartList!=null}">
								<li><a href="/catalog/goods/cart">Your Cart(${cartList.size()})</a></li>
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
					<a href="index.jsp" class="logo pull-left"><img src="/resources/themes/images/logo.png" class="site_logo" alt=""></a>
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
			<img class="pageBanner" src="/resources/themes/images/pageBanner.png" alt="New products" >
				<h4><span>Shopping Cart</span></h4>
			</section>
			<section class="main-content">				
				<div class="row">
					<%--<spring:form action="/catalog/goods/cart/order" method="post" commandName="cartItem" class="form-inline">--%>
					<div class="span9">
						<c:if test="${not empty error}">
							<div class="error">${error}</div>
						</c:if>
						<h4 class="title"><span class="text"><strong>Your</strong> Cart</span></h4>
						<table class="table table-striped">
							<thead>
								<tr>
									<th><big>Remove</big></th>
									<th><big>Image</big></th>
									<th><big>Product Name</big></th>
									<th><big>Quantity</big></th>
									<th><big>Unit Price</big></th>
									<th><big>Total</big></th>
								</tr>
							</thead>
							<tbody>
							<c:forEach var="cartItem"  items="${cartList}">
								<tr>
									<td>
										<div class="buttonHolder">
										<a href="" class="button cross"></a>
										</div>
									</td>
									<td><a href="${cartItem.goods.img}"><img alt="" height="100" width="100" src="${cartItem.goods.img}"></a></td>
										<td><big>${cartItem.goods.name}</big></td>
										<%--<spring:input path="cartItem" type="text" class="span1" placeholder="1" pattern="^[1-9]+$" title="Amount must be a integer and more then zero"/>--%>
									<%--<td><input type="text" placeholder="1" class="input-mini"></td>--%>
										<td><big>${cartItem.quantity}</big></td>
										<td><big>${cartItem.goods.price}</big></td>
										<td><big>${cartItem.goods.price*cartItem.quantity}</big></td>
								</tr>
							</c:forEach>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td><strong><big>${sum}</big></strong></td>
								</tr>		  
							</tbody>
						</table>
						<%--<h4>What would you like to do next?</h4>--%>
						<%--<p>Choose if you have a discount code or reward points you want to use or would like to estimate your delivery cost.</p>--%>
						<%--<label class="radio">--%>
							<%--<input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked="">--%>
							<%--Use Coupon Code--%>
						<%--</label>--%>
						<%--<label class="radio">--%>
							<%--<input type="radio" name="optionsRadios" id="optionsRadios2" value="option2">--%>
							<%--Estimate Shipping &amp; Taxes--%>
						<%--</label>--%>
						<%--<hr>--%>
						<%--<p class="cart-total right">--%>
							<%--<strong>Sub-Total</strong>:	$100.00<br>--%>
							<%--<strong>Eco Tax (-2.00)</strong>: $2.00<br>--%>
							<%--<strong>VAT (17.5%)</strong>: $17.50<br>--%>
							<%--<strong>Total</strong>: $119.50<br>--%>
						<%--</p>--%>
						<hr/>
						<c:if test="${client!=null}">
							<spring:form action="/catalog/profile/goods/cart/continue" method="get">
									<span class="pull-right">
										<button type="submit" class="btn btn-inverse">Continue</button>
									</span>
							</spring:form>
						</c:if>
						<c:if test="${client==null}">
							<spring:form action="/catalog/goods/cart?error" method="get">
									<span class="pull-right">
										<button type="submit" class="btn btn-inverse">Continue</button>
									</span>
							</spring:form>
						</c:if>

					</div>
					<div class="span3 col">
						<div class="block">
							<ul class="nav nav-list">
								<li class="nav-header">SUB CATEGORIES</li>
								<li><a href="${pageContext.request.contextPath}/catalog">All games</a></li>
								<c:forEach var="categoryVar"  items="${listCategory}">

									<c:if test="${client.role.name=='ROLE_EMPLOYEE'}" >
										<div class="buttonHolder">
											<a href="/catalog/employee/edit/category/${categoryVar.id}" class="button flower"></a>
										</div>
										<div class="buttonHolder">
											<a href="/catalog/employee/delete/category/${categoryVar.id}" class="button cross" onclick="return confirm('Are you sure?')"></a>
										</div>
									</c:if>

									<li
											<c:if test="${categoryVar.name == categoryName}" >
												class="active"
											</c:if>
									><a href="${pageContext.request.contextPath}/catalog/${categoryVar.name}/page/${1}">${categoryVar.name}</a>
									</li>

								</c:forEach>
							</ul>
							<br/>
							<ul class="nav nav-list below">
								<li class="nav-header">Filter</li>
								<li><a href="goods.jsp">Adidas</a></li>
								<li><a href="goods.jsp">Nike</a></li>
								<li><a href="goods.jsp">Dunlop</a></li>
								<li><a href="goods.jsp">Yamaha</a></li>
							</ul>
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
							<li><a href="/catalog/goods/cart">Your Cart</a></li>
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
			$(document).ready(function() {
				$('#checkout').click(function (e) {
					document.location.href = "checkout.jsp";
				})
			});
		</script>		
    </body>
</html>