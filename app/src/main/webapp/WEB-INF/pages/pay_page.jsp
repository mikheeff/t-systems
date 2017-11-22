<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pay page</title>
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

        /* Padding - just for asthetics on Bootsnipp.com */
        body { margin-top:20px; }

        /* CSS for Credit Card Payment form */
        .credit-card-box .panel-title {
            display: inline;
            font-weight: bold;
        }
        .credit-card-box .form-control.error {
            border-color: red;
            outline: 0;
            box-shadow: inset 0 1px 1px rgba(0,0,0,0.075),0 0 8px rgba(255,0,0,0.6);
        }
        .credit-card-box label.error {
            font-weight: bold;
            color: red;
            padding: 2px 8px;
            margin-top: 2px;
        }
        .credit-card-box .payment-errors {
            font-weight: bold;
            color: red;
            padding: 2px 8px;
            margin-top: 2px;
        }
        .credit-card-box label {
            display: block;
        }
        /* The old "center div vertically" hack */
        .credit-card-box .display-table {
            display: table;
        }
        .credit-card-box .display-tr {
            display: table-row;
        }
        .credit-card-box .display-td {
            display: table-cell;
            vertical-align: middle;
            width: 50%;
        }
        /* Just looks nicer */
        .credit-card-box .panel-heading img {
            min-width: 180px;
        }

        #user2 {
            margin-right: 180px;
            /*border: solid 1px black;*/
        }
        #user {
            width: 100px; /* Ширина поля в пикселах */
            /*padding: 30px;*/
            margin-left: 30px;
        }

    </style>
</head>
<body>
<div id="top-bar" class="container">
    <div class="row">
        <div class="span4">
            <form action="/catalog/search" method="POST" command class="search_form">
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
                    <li><a href="#">Best Sellers</a>
                    <li><a href="#">How To Buy</a></li>
                    <li><a href="#">F.A.Q</a></li>
                    <li><a href="#">About us</a></li>
                </ul>
            </nav>
        </div>
    </section>
    <section class="header_text sub">
        <img class="pageBanner" src="/resources/themes/images/pageBanner.png" alt="" >
    </section>
    <section class="main-content">
        <div class="row">
            <div class="span9" >
                <div class="span5 pull-right"  id="user2">
                    <div class="col-xs-12 col-md-4">
                        <div class="panel panel-default credit-card-box">
                                    <h4 align="center" >Payment Details</h4>
                            <div class="panel-body">
                                <form role="form" id="payment-form" method="POST" action="/order/pay/${id}">
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <div class="row display-tr" >
                                                <div class="form-group">
                                                    <label>CARD NUMBER</label>
                                                    <div class="input-group" >
                                                        <input
                                                                type="tel"
                                                                class="form-control"
                                                                <%--name="cardNumber"--%>
                                                                placeholder="Valid Card Number"
                                                                autocomplete="cc-number"
                                                                required autofocus
                                                        />
                                                        <span class="input-group-addon"><i class="fa fa-credit-card"></i></span>
                                                    </div>
                                                    <img class="img-responsive pull-right" src="http://i76.imgup.net/accepted_c22e0.png">
                                                </div>
                                                <div class="display-td"  >
                                                    <img class="img-responsive pull-right" src="http://static1.businessinsider.com/image/57fd4b7b4046dd36318b4e99-480/when-to-use-credit-2016lead.png" width="200" >
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-xs-7 col-md-7">
                                            <div class="row display-tr" >
                                                <div class="form-group">
                                                    <label><span class="hidden-xs">EXPIRATION</span><span class="visible-xs-inline">EXP</span> DATE</label>
                                                    <input
                                                            type="tel"
                                                            class="form-control"
                                                            <%--name="cardExpiry"--%>
                                                            placeholder="MM / YY"
                                                            autocomplete="cc-exp"
                                                            required
                                                    />
                                                </div>

                                                <div class="form-group display-td ">
                                                    <label style="margin-left: 30px;">CV CODE</label>
                                                    <input
                                                            type="tel"
                                                            class="form-control"
                                                            <%--name="cardCVC"--%>
                                                            placeholder="CVC"
                                                            autocomplete="cc-csc"
                                                            required
                                                            id="user"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <div class="row display-tr" >
                                                <div class="form-group">
                                                    <label>CARDHOLDER NAME</label>
                                                    <input type="text" class="form-control"  />
                                                </div>
                                                <div class="display-td">
                                                    <b style="margin-left: 30px;" ><big><big>Total: ${sum} &#8381;</big></big></b>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                            <div class="col-xs-12">
                                                <button class="subscribe btn btn-success btn-lg btn-block" type="submit">Pay</button>
                                            </div>
                                    </div>
                                    <div class="row" style="display:none;">
                                        <div class="col-xs-12">
                                            <p class="payment-errors"></p>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="span3 col">
                <div class="block">
                    <ul class="nav nav-list">
                        <li class="nav-header">SUB CATEGORIES</li>
                        <li><a href="${pageContext.request.contextPath}/catalog/page/${1}">All games</a></li>
                        <c:forEach var="categoryVar"  items="${listCategory}">

                            <li><a href="${pageContext.request.contextPath}/catalog/${categoryVar.name}/page/${1}">${categoryVar.name}</a>
                            </li>

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
                                        <a href="${pageContext.request.contextPath}/catalog/goods/${goodsVar.id}"><img alt="" src="${goodsVar.img}"></a><br/>
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
                <div class="block">
                    <h4 class="title"><strong>Best</strong> Seller</h4>
                    <ul class="small-product">
                        <c:forEach var="goodsVar" items="${bestSellersList}">
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
                    <li><a href="#">Your Cart</a></li>
                    <li><a href="#">Login</a></li>
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