		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
							<li><a href="${pageContext.request.contextPath}/clients/profile">My Account</a></li>
							<li><a href="cart.html">Your Cart</a></li>
							<li><a href="checkout.jsp">Checkout</a></li>
							<li><a href="${pageContext.request.contextPath}/clients/profile">Login</a></li>
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
							<div class="span5">
								<address>
									<el style="text-decoration: underline"><h4><strong>${goods.name}</strong></h4></el>

									<h5><strong>Number of players:</strong></h5> <h5><span>${goods.numberOfPlayers}</span></h5><br>
									<h5><strong>Duration of the game:</strong></h5> <h5><span>${goods.duration}</span></h5><br>
									<h5><strong>Complexity of the rules:</strong></h5> <h5><span>${goods.rule.name}</span></h5><br>
									<c:if test="${goods.amount==0}" >
										<h5><strong>Availability:</strong><span>Out Of Stock</span></h5><br>
									</c:if>
									<c:if test="${goods.amount!=0}" >
										<h5><strong>Availability:</strong></h5> <h5><span>Available</span></h5><br>
									</c:if>
								</address>
								<h4><strong>Price: ${goods.price} &#8381;</strong></h4>
							</div>
							<div class="span5">
								<form class="form-inline">
									<label class="checkbox">
										<input type="checkbox" value=""> Option one is this and that
									</label>
									<br/>
									<label class="checkbox">
									  <input type="checkbox" value=""> Be sure to include why it's great
									</label>
									<p>&nbsp;</p>
									<label>Qty:</label>
									<input type="text" class="span1" placeholder="1">
									<button class="btn btn-inverse" type="submit">Add to cart</button>
								</form>
							</div>							
						</div>
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
							<ul class="nav nav-list below">
								<li class="nav-header">MANUFACTURES</li>
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
							<li><a href="index.jsp">Homepage</a></li>
							<li><a href="./about.html">About Us</a></li>
							<li><a href="./contact.html">Contac Us</a></li>
							<li><a href="./cart.html">Your Cart</a></li>
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