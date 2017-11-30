<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en" >
<head>
    <meta charset="utf-8">
    <title>Recover Page</title>
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
        <h4><span>RECOVER PASSWORD OR CONFIRM EMAIL</span></h4>
    </section>
    </section>
    <section class="main-content">
        <div class="row">
            <div class="span9" >
                <c:if test="${not empty error}">
                    <div class="error">${error}</div>
                </c:if>
                <c:if test="${not empty msg}">
                    <div class="msg">${msg}</div>
                </c:if>
                <div class="row">
                    <div class="span4">
                        <h4 class="title"><span class="text"><strong>Recover</strong> Password</span></h4>

                        <form action="/send/recover" method="post" >
                            <fieldset>
                                <div class="control-group">
                                    <label class="control-label">Email address:</label>
                                    <div class="controls">
                                        <input name="email" type="text" class="input-xlarge" maxlength="15"placeholder="Enter your email" pattern="^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]+$">
                                    </div>
                                </div>
                                <div class="control-group">
                                    <input tabindex="3" class="btn btn-inverse large" type="submit" value="Recover password">
                                </div>
                            </fieldset>
                        </form>
                    </div>
                    <div class="span5">
                        <h4 class="title"><span class="text"><strong>Confirm</strong> Email</span></h4>
                        <%--<c:if test="${not empty error1}">--%>
                            <%--<div class="error">${error1}</div>--%>
                        <%--</c:if>--%>
                        <%--<c:if test="${not empty msg}">--%>
                            <%--<div class="msg">${msg}</div>--%>
                        <%--</c:if>--%>

                        <form action="/email/send" method="post" >
                            <fieldset>
                                <div class="control-group">
                                    <label class="control-label">Email address:</label>
                                    <div class="controls">
                                        <input name="email" type="text" class="input-xlarge" placeholder="Enter your email" pattern="^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]+$">
                                    </div>
                                </div>
                                <div class="control-group">
                                    <input tabindex="3" class="btn btn-inverse large" type="submit" value="Send confirm e-mail">
                                </div>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
            <div class="span3 col pull-right">
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
</body>
</html>