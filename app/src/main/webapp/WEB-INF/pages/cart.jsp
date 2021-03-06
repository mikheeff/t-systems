<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Shopping Cart</title>
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
			<img class="pageBanner" src="/resources/themes/images/pageBanner.png" alt="New products" >
				<h4><span>Shopping Cart</span></h4>
			</section>
			<section class="main-content">				
				<div class="row">
					<div class="span9">

						<h4 class="title"><span class="text"><strong>Your</strong> Cart</span></h4>
						<c:if test="${client==null}">
							<div class="msg">To place an order please log in</div>
						</c:if>
						<c:if test="${not empty error}">
							<div class="error">${error}</div>
						</c:if>
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
							<c:forEach var="cartItem"  items="${cartList}" varStatus="count">
								<tr>
									<td>
										<div class="buttonHolder">
										<a href="/catalog/goods/cart/delete/item/${count.index}" class="button cross"></a>
										</div>
									</td>
									<td><a href="/catalog/goods/${cartItem.goods.id}"><img alt="" height="100" width="100" src="/catalog/goods/image?id=${cartItem.goods.id}&number=0"></a></td>
										<td><big>
												${cartItem.goods.name}
												<br>
											<c:if test="${cartItem.goods.amount>0}">
												<span class="label label-success">Available</span>
											</c:if>
											<c:if test="${cartItem.goods.amount<=0}">
												<span class="label label-warning">Out of Stock</span>
											</c:if>
											</big></td>
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
						<hr/>
						<c:if test="${client!=null and cartList!=null and cartList.size()!=0}">
							<spring:form action="/order/continue" method="get">
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
								<li><a href="${pageContext.request.contextPath}/catalog/page/${1}">All games</a></li>
								<c:forEach var="categoryVar"  items="${listCategory}">

									<li><a href="${pageContext.request.contextPath}/catalog/${categoryVar.name}/page/${1}">${categoryVar.name}</a></li>

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
									<c:forEach var="goodsVar" items="${randomGoods}" varStatus="count">
									<div class=
										 <c:if test="${count.index==0}">
												 "active item"
									</c:if>
									<c:if test="${count.index!=0}">
										"item"
									</c:if>
									>
									<ul class="thumbnails listing-products">
										<li class="span3">
											<div class="product-box">
												<span class="sale_tag"></span>
												<a href="${pageContext.request.contextPath}/catalog/goods/${goodsVar.id}"><img alt="" src="/catalog/goods/image?id=${goodsVar.id}&number=0"></a><br/>
												<a href="${pageContext.request.contextPath}/catalog/goods/${goodsVar.id}">${goodsVar.name}</a><br/>
												<a href="#" class="category">${goodsVar.category.name}</a>
												<p class="price">${goodsVar.price} &#8381;</p>
											</div>
										</li>
									</ul>
								</div>
								</c:forEach>
							</div>
							</div>
						</div>
						<div class="block">
							<h4 class="title"><strong>Best</strong> Seller</h4>
							<ul class="small-product">
								<c:forEach var="goodsVar" items="${bestSellersList}">
									<li>
										<a href="${pageContext.request.contextPath}/catalog/goods/${goodsVar.id}" title="${goodsVar.name}">
											<img src="/catalog/goods/image?id=${goodsVar.id}&number=0" alt="">
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
		<!-- BEGIN JIVOSITE CODE {literal} -->
		<script type='text/javascript'>
            (function(){ var widget_id = 'MM4OLoDIT1';var d=document;var w=window;function l(){
                var s = document.createElement('script'); s.type = 'text/javascript'; s.async = true; s.src = '//code.jivosite.com/script/widget/'+widget_id; var ss = document.getElementsByTagName('script')[0]; ss.parentNode.insertBefore(s, ss);}if(d.readyState=='complete'){l();}else{if(w.attachEvent){w.attachEvent('onload',l);}else{w.addEventListener('load',l,false);}}})();
		</script>
		<!-- {/literal} END JIVOSITE CODE -->
    </body>
</html>