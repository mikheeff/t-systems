<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Best Sellers</title>
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
</head>
<body>

<div id="top-bar" class="container">
    <div class="row">
        <div class="span4">
            <form action="/catalog/search?page=1" method="POST" command class="search_form">
                <input name="searchStr" type="text" class="input-block-level search-query" maxlength="15" Placeholder="Search games">
            </form>
        </div>
        <%--input-block-level search-query--%>
        <div class="span8">
            <div class="account pull-right">
                <ul class="user-menu">
                    <c:url value="/j_spring_security_logout" var="logoutUrl" />
                    <script>
                        function formSubmit() {
                            document.getElementById("logoutForm").submit();
                        }
                        //							</script>
                    <c:if test="${client.role.name=='ROLE_EMPLOYEE'}">
                        <li><a href="/employee/administration">Administration</a></li>
                    </c:if>
                    <li><a href="${pageContext.request.contextPath}/clients/profile">My Account</a></li>
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
        <img class="pageBanner" src="/resources/themes/images/pageBanner.png" alt="New products" >
        <h4><span>Best Sellers</span></h4>
    </section>
    <section class="main-content">
        <div class="row">
            <div class="span12">
                <c:forEach var="categoryVar" items="${listCategory}">
                    <div class="row">
                        <div class="span12">
                            <h4 class="title">
                                <span class="pull-left"><span class="text"><span class="line">Best of <strong>${categoryVar.name}</strong></span></span></span>
                            </h4>
                            <div id="myCarousel" class="myCarousel carousel slide">
                                <div class="carousel-inner">
                                    <div class="active item">
                                        <ul class="thumbnails">
                                            <c:set var="bestSellers" value="bestSellersOf${categoryVar.id}"/>

                                            <c:forEach var="goodsVar"  begin = "0" end = "3" items="${requestScope[bestSellers]}">
                                                <li class="span3">
                                                    <div class="product-box">
                                                        <span class="sale_tag"></span>
                                                        <a href="${pageContext.request.contextPath}/catalog/goods/${goodsVar.id}"><img alt="" src="/catalog/goods/image?id=${goodsVar.id}&number=0"></a><br/>
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
                </c:forEach>
                <br/>
            </div>
        </div>
    </section>
    <section class="our_client">
        <h4 class="title"><span class="text">Manufactures</span></h4>
        <div class="row">
            <div class="span2">
                <a href="#"><img alt="" src="/resources/themes/images/clients/14.png"></a>
            </div>
            <div class="span2">
                <a href="#"><img alt="" src="/resources/themes/images/clients/35.png"></a>
            </div>
            <div class="span2">
                <a href="#"><img alt="" src="/resources/themes/images/clients/1.png"></a>
            </div>
            <div class="span2">
                <a href="#"><img alt="" src="/resources/themes/images/clients/2.png"></a>
            </div>
            <div class="span2">
                <a href="#"><img alt="" src="/resources/themes/images/clients/3.png"></a>
            </div>
            <div class="span2">
                <a href="#"><img alt="" src="/resources/themes/images/clients/4.png"></a>
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
        <span>Copyright 2017  All right reserved.</span>
    </section>
</div>
<script src="/resources/themes/js/common.js"></script>
<script src="/resources/themes/js/jquery.flexslider-min.js"></script>
<script type="text/javascript">
    $(function() {
        $(document).ready(function() {
            $('.flexslider').flexslider({
                animation: "fade",
                slideshowSpeed: 4000,
                animationSpeed: 600,
                controlNav: false,
                directionNav: true,
                controlsContainer: ".flex-container" // the container that holds the flexslider
            });
        });
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