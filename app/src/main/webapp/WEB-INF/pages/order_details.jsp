<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Order Details</title>
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

        #orderInput {
            width: 150px; /* Ширина поля в пикселах */
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
                        //							</script>
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
                    <li><a href="#">How To Buy</a></li>
                    <li><a href="#">F.A.Q</a></li>
                    <li><a href="#">About us</a></li>
                </ul>
            </nav>
        </div>
    </section>
    <section class="header_text sub">
        <img class="pageBanner" src="/resources/themes/images/pageBanner.png" alt="New products" >
        <h4><span>Order Details</span></h4>
    </section>
    <section class="main-content">
        <div class="row">
            <c:if test="${client.role.name=='ROLE_CLIENT'}">
                <div class="span9">
                    <h4 class="title"><span class="text"><strong>Order</strong> Details</span></h4>
                    <c:if test="${not empty msg}">
                        <div class="msg">${msg}</div>
                    </c:if>
                    <table class="table table-striped" >
                        <thead>
                        <tr>
                            <th><big><big>Order ID: ${order.id}</big></big></th>
                            <th>&nbsp;</th>
                            <th>&nbsp;</th>
                            <th>&nbsp;</th>
                            <th>&nbsp;</th>
                            <%--<th>&nbsp;</th>--%>
                        </tr>
                        </thead>

                    </table>
                    <table class="table table-striped" border="2">
                        <thead>
                        <tr>
                            <th><big>&#8470;</big></th>
                            <th><big>Product Name</big></th>
                            <th><big>Quantity</big></th>
                            <th><big>Unit Price</big></th>
                            <th><big>Unit Total</big></th>
                            <%--<th>&nbsp;</th>--%>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="cartItem"  items="${orderItemsList}" varStatus="count">
                            <tr>
                                <th><big>${count.count}</big></th>
                                <td><big>${cartItem.goods.name}</big></td>
                                <td><big>${cartItem.quantity}</big></td>
                                <td><big>${cartItem.goods.price} &#8381;</big></td>
                                <td><big>${cartItem.goods.price*cartItem.quantity} &#8381;</big></td>
                                <%--<th>&nbsp;</th>--%>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <table class="table table-striped" border="2">
                        <thead>
                        <tr>
                            <th><big>Delivery method</big></th>
                            <th><big>Payment type</big></th>
                            <th><big>Order status</big></th>
                            <th><big>Pay status</big></th>
                            <th><big>Additional information</big></th>
                            <%--<th>&nbsp;</th>--%>
                        </tr>
                        </thead>

                        <tbody>
                        <tr>
                            <td><big>${order.deliveryMethod.name}</big></td>
                            <td><big>${order.paymentType.name}</big</td>
                            <td><big>${order.status.name}</big</td>
                            <td>

                                <c:if test="${order.payStatus == 0}">
                                    <big>Unpaid</big>
                                </c:if>
                                <c:if test="${order.payStatus == 1}">
                                    <big>Paid</big>
                                </c:if>
                            </td>
                            <td><textarea cols="20" rows="3" readonly="true">${order.comment}</textarea></td>
                            <%--<td>&nbsp;</td>--%>
                        </tr>
                        </tbody>
                    </table>
                    <table class="table table-striped" >
                        <tbody>
                        <tr>
                            <td><big>Date: ${order.date}</big></td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td><strong><big><big>Total: ${sum} &#8381;</big></big></strong></td>
                        </tr>
                        </tbody>
                    </table>
                    <hr>
                    <c:if test="${order.paymentType.name=='card' and order.payStatus == 0}">

                    <%--<form action="/order/pay/${order.id}" method="get">--%>
                    <form action="/order/pay/${order.id}" method="get">
                        <span class="pull-right">
                            <button type="submit" class="btn btn-inverse">Pay an order</button>
                        </span>
                    </form>

                    </c:if>
                    <c:if test="${not empty msg}">
                        <form action="/clients/profile" method="get">
                            <span class="pull-right">
                                <button type="submit" class="btn btn-inverse">Back to account</button>
                            </span>
                        </form>
                    </c:if>
                </div>
            </c:if>

            <c:if test="${client.role.name=='ROLE_EMPLOYEE'}">

            <div class="span9">
                <h4 class="title"><span class="text"><strong>Order</strong> Details</span></h4>
                <c:if test="${not empty msg}">
                    <div class="msg">${msg}</div>
                </c:if>
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>&nbsp;</th>
                        <th><big>Client ID</big></th>
                        <th><big>Client Name</big></th>
                        <th><big>Client Email</big></th>
                        <th><big>Phone:</big></th>
                        <th>&nbsp;</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>&nbsp;</td>
                        <td><big>${order.client.id}</big></td>
                        <td><big>${order.client.name}</big></td>
                        <td><big>${order.client.email}</big></td>
                        <td><big>${order.client.phone}</big></td>
                        <td>&nbsp;</td>
                    </tr>
                    </tbody>
                </table>
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th><big>&#8470;</big></th>
                        <th><big>Product Name</big></th>
                        <th><big>Quantity</big></th>
                        <th><big>Unit Price</big></th>
                        <th><big>Total</big></th>
                        <th>&nbsp;</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="cartItem"  items="${orderItemsList}" varStatus="count">
                        <tr>
                            <th>${count.count}</th>
                            <td><big>${cartItem.goods.name}</big></td>
                            <td><big>${cartItem.quantity}</big></td>
                            <td><big>${cartItem.goods.price}</big></td>
                            <td><big>${cartItem.goods.price*cartItem.quantity}</big></td>
                            <th>&nbsp;</th>
                        </tr>
                    </c:forEach>
                    <spring:form action="/employee/order/edit/${order.id}" method="post" commandName="order" class="form-inline">
                    </tbody>
                </table>
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th><big>Edit the delivery method</big></th>
                        <th><big>Edit the payment type</big></th>
                        <th><big>Edit order status</big></th>
                        <th><big>Pay status</big></th>
                        <th><big>Additional information</big></th>
                        <th>&nbsp;</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>
                            <spring:select path="deliveryMethod.name" class="input-xlarge" id ="orderInput">
                                <c:forEach var="methodVar"  items="${listDeliveryMethod}">
                                    <spring:option value="${methodVar.name}">${methodVar.name}</spring:option>
                                </c:forEach>
                            </spring:select>
                        </td>
                        <td>
                            <spring:select path="paymentType.name" class="input-xlarge" id ="orderInput">
                                <c:forEach var="payTypeVar"  items="${listPaymentType}">
                                    <spring:option value="${payTypeVar.name}">${payTypeVar.name}</spring:option>
                                </c:forEach>
                            </spring:select>
                        </td>
                        <td>
                            <spring:select path="status.name" class="input-xlarge" id ="orderInput">
                                <c:forEach var="statusVar"  items="${listStatus}">
                                    <spring:option value="${statusVar.name}">${statusVar.name}</spring:option>
                                </c:forEach>
                            </spring:select>
                        </td>
                        <td>
                            <c:if test="${order.payStatus == 0}">
                                <big>Unpaid</big>
                            </c:if>
                            <c:if test="${order.payStatus == 1}">
                                <big>Paid</big>
                            </c:if>

                        </td>
                        <td><spring:textarea path="comment" cols="25" rows="3" readonly="true"/></td>
                        <td>&nbsp;</td>
                    </tr>
                    <tr>
                        <td><big>Date: ${order.date}</big></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td><strong><big><big>Total: ${sum}</big></big></strong></td>
                        <td>&nbsp;</td>
                    </tr>
                    </tbody>
                </table>
                <hr>
                         <span class="pull-right">
                            <button type="submit" class="btn btn-inverse">Edit Order</button>
                         </span>

                </spring:form>
            </div>
            </c:if>
            <div class="span3 col">
                <div class="block">
                    <ul class="nav nav-list">
                        <li class="nav-header">SUB CATEGORIES</li>
                        <li><a href="${pageContext.request.contextPath}/catalog">All games</a></li>
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
                    <li><a href="/catalog/goods/cart">Your Cart</a></li>
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
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script>
    $(document).ready(function() {
        $('#checkout').click(function (e) {
            document.location.href = "register.jsp";
        })
    });
</script>
<script type="text/javascript" >
    function showStuff(id) {
            document.getElementById(id).style.display = 'block';
    }
</script>

</body>
</html>