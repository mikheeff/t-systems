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
			html, body{
				min-height:100%;
			}

			body{
				background-image: -webkit-linear-gradient(top, #edecec, #cecbc9);
				background-image: linear-gradient(top, #edecec, #cecbc9);
			}

			.buttonHolder{
				margin:23px auto;
				width:500px;
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

			#filterInput {
				width: 200px; /* Ширина поля в пикселах */
			}
		</style>
	</head>
    <ctio>
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
							<li><a href="${pageContext.request.contextPath}/bestsellers">Best Sellers</a></li>
							<li><a href="${pageContext.request.contextPath}/contact">Contact us</a></li>
						</ul>
					</nav>
				</div>
			</section>
			<section class="header_text sub">
			<img class="pageBanner" src="/resources/themes/images/pageBanner.png" alt="" >
				<c:if test="${!categoryFilter and !searchFlag and !isFilter}" >
				<h3><span>ALL GAMES</span></h3>
				</c:if>
				<c:if test="${searchFlag}" >
				<h3><span>SEARCH RESULTS WITH "${searchStr}"</span></h3>
				</c:if>
				<c:if test="${categoryFilter}" >
					<h3><span>${categoryName.toUpperCase()}</span></h3>
				</c:if>
				<c:if test="${isFilter}" >
					<h3><span>GOODS WITH REQUEST PARAMS</span></h3>
				</c:if>
			</section>
			<section class="main-content">
				
				<div class="row">						
					<div class="span9">
						<ul class="thumbnails listing-products">
							<c:forEach var="goodsVar" items="${listGoods}">
							<li class="span3">
								<div class="product-box">
									<span class="sale_tag"></span>												
									<a href="${pageContext.request.contextPath}/catalog/goods/${goodsVar.id}"><img alt="" src="/catalog/goods/image?id=${goodsVar.id}&number=0" style="height: 270px; width: 270px;" ></a><br/>
									<a href="${pageContext.request.contextPath}/catalog/goods/${goodsVar.id}" class="title">${goodsVar.name}</a><br/>
									<a href="#" class="category">${goodsVar.category.name}</a>
									<p class="price">${goodsVar.price} &#8381;</p>
								</div>
							</li>
							</c:forEach>
						</ul>								
						<hr>
						<div class="pagination pagination-small pagination-centered">
							<ul>
								<c:if test="${!categoryFilter and !searchFlag and !isFilter}" >

									<c:if test="${currentPage>1}" >
									<li><a href="${pageContext.request.contextPath}/catalog/page/${currentPage-1}">Prev</a></li>
									</c:if>

									<c:forEach var="i"  begin = "1" end = "${amountOfPages}" varStatus="count"  >
										<li
										<c:if test="${count.index==currentPage}" >
											 class="active"
										</c:if>
										><a href="${pageContext.request.contextPath}/catalog/page/${i}">${i}</a></li>
									</c:forEach>

									<c:if test="${currentPage<amountOfPages}" >
										<li><a href="${pageContext.request.contextPath}/catalog/page/${currentPage+1}">Next</a></li>
									</c:if>

								</c:if>

								<c:if test="${categoryFilter}" >

									<c:if test="${currentPage>1}" >
									<li><a href="${pageContext.request.contextPath}/catalog/${categoryName}/page/${currentPage-1}">Prev</a></li>
									</c:if>

									<c:forEach var="i"  begin = "1" end = "${amountOfPages}" varStatus="count"  >
										<li
										<c:if test="${count.index==currentPage}" >
											 class="active"
										</c:if>
										><a href="${pageContext.request.contextPath}/catalog/${categoryName}/page/${i}">${i}</a></li>
									</c:forEach>

									<c:if test="${currentPage<amountOfPages}" >
										<li><a href="${pageContext.request.contextPath}/catalog/${categoryName}/page/${currentPage+1}">Next</a></li>
									</c:if>

								</c:if>

								<c:if test="${searchFlag}" >

									<c:if test="${currentPage>1}" >
									<li><a href="${pageContext.request.contextPath}/catalog/search?page=${currentPage-1}">Prev</a></li>
									</c:if>

									<c:forEach var="i"  begin = "1" end = "${amountOfPages}" varStatus="count"  >
										<li
										<c:if test="${count.index==currentPage}" >
											 class="active"
										</c:if>
										><a href="${pageContext.request.contextPath}/catalog/search?page=${i}">${i}</a></li>
									</c:forEach>

									<c:if test="${currentPage<amountOfPages}" >
										<li><a href="${pageContext.request.contextPath}/catalog/search?page=${currentPage+1}">Next</a></li>
									</c:if>

								</c:if>

								<c:if test="${isFilter}" >

									<c:if test="${currentPage>1}" >
									<li><a href="/catalog/filter/page/${currentPage-1}">Prev</a></li>
									</c:if>

									<c:forEach var="i"  begin = "1" end = "${amountOfPages}" varStatus="count"  >
										<li
										<c:if test="${count.index==currentPage}" >
											 class="active"
										</c:if>
										><a href="/catalog/filter/page/${i}">${i}</a></li>
									</c:forEach>

									<c:if test="${currentPage<amountOfPages}" >
										<li><a href="/catalog/filter/page/${currentPage+1}">Next</a></li>
									</c:if>
									<%--<spring:form action="/catalog/filter/page/${currentPage+1}"  method="post" commandName="catalogQuery" class="form-stacked" >--%>
									<%--</spring:form>--%>
								</c:if>

							</ul>
						</div>
					</div>
					<div class="span3 col">
						<div class="block">
							<c:if test="${client.role.name=='ROLE_EMPLOYEE'}" >
								<form action="/employee/administration">
									<button type="submit" class="btn btn-inverse">Add new Goods or Category</button>
									<hr>
								</form>

								<c:if test="${not empty msg}">
								<div class="msg">${msg}</div>
								</c:if>

								<c:if test="${editCatFlag==true}" >
									<spring:form action="/catalog/employee/edit/category" method="post" commandName="category" class="form-stacked">
										<fieldset>
											<div class="control-group">
												<label class="control-label">Id:</label>
												<div class="controls">
													<spring:input path="id" readonly="true" type="text" class="input-xlarge" id="categoryInput"/>
												</div>
											</div>
											<div class="control-group">
												<label class="control-label">Name:</label>
												<div class="controls">
													<spring:input path="name" type="text" placeholder="Enter name of new category" class="input-xlarge" id="categoryInput"/>
													<spring:errors path="name" cssClass="error"/>
												</div>
											</div>
											<div class="actions"><input tabindex="9" class="btn btn-inverse small" type="submit" value="Edit category"></div>
											<hr>
										</fieldset>
									</spring:form>
								</c:if>
							</c:if>

							<ul class="nav nav-list">
								<li class="nav-header">SUB CATEGORIES</li>
									<li
									<c:if test="${!categoryFilter and !searchFlag}" >
									class="active"
									</c:if>
									><a href="${pageContext.request.contextPath}/catalog/page/${1}">All games</a></li>
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
								<c:if test="${client.role.name!='ROLE_EMPLOYEE'}">
								<hr>
								<h5>If necessary, specify conditions</h5>
								<spring:form action="/catalog/filter/page/${1}"  method="post" commandName="catalogQuery" class="form-stacked" >
								<fieldset>
									<div class="control-group">
										<label class="control-label"><span class="required"></span> Amount Of Players:</label>
										<div class="controls">
											<spring:select path="numberOfPlayers" class="input-xlarge" id="filterInput">
												<spring:option value="${null}">---Please Select---</spring:option>
												<c:forEach   begin = "1" end = "10"  varStatus="count">
													<spring:option value="${count.index}">${count.index}</spring:option>
												</c:forEach>
											</spring:select>
										</div>
										<label class="control-label"><span class="required"></span> Duration:</label>
										<div class="controls">
											<spring:select path="duration" class="input-xlarge" id="filterInput">
												<spring:option value="${null}">---Please Select---</spring:option>
												<c:forEach  begin = "1" end = "8"  varStatus="count">
													<spring:option value="${count.index/2.0}">${count.index/2.0}</spring:option>
												</c:forEach>
											</spring:select>
										</div>
										<label class="control-label"><span class="required"></span> Price:</label>
										<div class="controls">
											<spring:select path="price" class="input-xlarge" id="filterInput">
												<spring:option value="${null}">---Please Select---</spring:option>
												<spring:option value="500">less then 500</spring:option>
												<spring:option value="1000">less then 1000</spring:option>
												<spring:option value="1500">less then 1500</spring:option>
												<spring:option value="2000">less then 2000</spring:option>
												<spring:option value="3000">less then 3000</spring:option>
												<spring:option value="3001">more then 3000</spring:option>
											</spring:select>
										</div>
										<label class="control-label"><span class="required"></span> Rules:</label>
										<div class="controls">
											<spring:select path="rules" class="input-xlarge" id="filterInput">
												<spring:option value="${null}">---Please Select---</spring:option>
												<spring:option value="easy">Easy</spring:option>
												<spring:option value="very easy">Very Easy</spring:option>
												<spring:option value="medium">Medium</spring:option>
												<spring:option value="hard">Hard</spring:option>
												<spring:option value="very hard">Very Hard</spring:option>
											</spring:select>
										</div>
										<label class="control-label"><span class="required"></span> Sorting:</label>
										<div class="controls">
											<spring:select path="sort" class="input-xlarge" id="filterInput">
												<spring:option value="${null}">---Please Select---</spring:option>
												<spring:option value="PRICE">By Price</spring:option>
												<spring:option value="ALPHABET">By Alphabet</spring:option>
												<spring:option value="RATING">By Rating</spring:option>
												<spring:option value="DATE">By Date</spring:option>
											</spring:select>
										</div>
									</div>
									<div class="actions"><input tabindex="9" class="btn btn-inverse small pull-left" type="submit" value="Filter"></div>
										<span class="pull-right">
											<button class="btn pull-right" form="reset" type="submit">Reset</button>
										</span>

								</fieldset>
								</spring:form>
								<form action="/catalog/page/${1}" id="reset" style="display: none" class="form-inline" method="get">
								</form>
								</c:if>
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
		<!-- BEGIN JIVOSITE CODE {literal} -->
		<script type='text/javascript'>
            (function(){ var widget_id = 'MM4OLoDIT1';var d=document;var w=window;function l(){
                var s = document.createElement('script'); s.type = 'text/javascript'; s.async = true; s.src = '//code.jivosite.com/script/widget/'+widget_id; var ss = document.getElementsByTagName('script')[0]; ss.parentNode.insertBefore(s, ss);}if(d.readyState=='complete'){l();}else{if(w.attachEvent){w.attachEvent('onload',l);}else{w.addEventListener('load',l,false);}}})();
		</script>
		<!-- {/literal} END JIVOSITE CODE -->
    </body>
</html>