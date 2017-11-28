<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Catalog of games</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <!--[if ie]>
    <meta content='IE=8' http-equiv='X-UA-Compatible'/><![endif]-->
    <!-- bootstrap -->
    <link href="${pageContext.request.contextPath}/resources/themes/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/themes/bootstrap/css/bootstrap-responsive.min.css"
          rel="stylesheet">

    <link href="${pageContext.request.contextPath}/resources/themes/css/bootstrappage.css" rel="stylesheet"/>

    <!-- global styles -->
    <link href="${pageContext.request.contextPath}/resources/themes/css/flexslider.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/resources/themes/css/main.css" rel="stylesheet"/>

    <!-- scripts -->

    <script src="${pageContext.request.contextPath}/resources/themes/js/jquery-1.7.2.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/themes/js/jquery.fancybox.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $('#cart_form').submit(function () {
                if ($('#btnCart').attr("value") != 'In cart') {
                    var quantity = $('#quantity').val();
                    var id = ${goods.id};
                    $.ajax({
                        type: 'GET',
                        url: "/catalog/goods/cart/" + id + "/add/" + quantity,
                        dataType: 'json',
                        success: function (size) {
                            $('#my_cart_new').html(size);
                            $('#btnCart').prop('value', 'In cart').prop('class', 'btn');
                            document.getElementById("my_cart").style.display = 'none';
                            document.getElementById("my_cart_li").style.display = 'block';
                            document.getElementById("my_cart_li").style.display = 'inline';
                            document.getElementById("quantity").style.display = 'none';
                            document.getElementById("qty").style.display = 'none';

                        }
                    });
                } else {
                    redirectToCart();
                }
            });
        });
    </script>
    <script type="text/javascript">
        $(document).ready(function () {

            $('#review_form').submit(function () {
                var id = ${goods.id};
                $.post('/catalog/goods/review/' + id, $(this).serialize(), function (review) {
                    if (review.client === undefined) {
                        document.getElementById("review_error").style.display = 'block';
                    } else {
                        document.getElementById("review_msg").style.display = 'block';

                        if (review.client.imgBase64 === null) {
                            $('#reviews').last().append(
                                '<div class="media">' +
                                '<div class="media pull-left">' +
                                '<img src="/resources/themes/images/img_avatar.png" class="media-object" style="width:60px">' +
                                '</div>'+
                                '<div class="media-body">' +
                                '<h4 class="media-heading">' + review.client.name + '</h4>' +
                                '<p>' + review.content + '</p>' +
                                '</div>' +
                                '</div>' +
                                '<div class="pull-right">' +
                                '<img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAB4AAAAeCAYAAAA7MK6iAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAF/SURBVEhL7dbNKwRxGMDx8Z63uJCLCOWgXISTixQlF6L4C5xdlLg4cFFukpc/QPkDtpxIuXOQ3ETKWw6SvH+f3ZmaHc+OmfntTCnf+tTutPs8a+3OjvUXq0FR5mZyleMGq+l7CTaGL8jyRP/qHchiMSAHkqgST3AWbyKRxuEsFbcoQeztwr1YDCLWqvEM7+JtxNokvEvFA0oRqVq0KDrQZUtBWyym4TyuDd45jShDVhPQhuXbHbKqxxG0B+fLB+bxI/lKrOAT2hNNXKMfvg1D3hJtQBR7aECg5INwAG1QUO9YQCFCVYwlyP9GG+znEn0wagTa8FxOUAfjhqAtyOUCBTBuHdoCP3ISMUo+GFfQhvtZhFG90AaLF+WY4xhGLUMbfIhmTOHRPubVisidwj3sDXLac19nNUH7zs8gUu1wDzpHD7TkhczhFc7j9xGpWThDtlCF3+rGGeQ5cuaSH5/QreEeo+l7wZOLwQ3Ij02nHAibvH0VmZuRksuk/+ws6xvtQBEuX1e9tQAAAABJRU5ErkJggg==" >' +
                                '<strong>' + '<big>' + '<big>' + review.rating + '</big>' + '</big>' + '</strong>' +
                                '</div>' +
                                '<hr>')
                        } else {
                            $('#reviews').last().append(
                                '<div class="media">' +
                                '<div class="media pull-left" style="display: inline">' +
                                '<img src="data:image/jpeg;base64,' + review.client.imgBase64 + '" class="media-object" style="width:60px">' +
                                '</div>' +
                                '<div class="media-body">' +
                                '<h4 class="media-heading">' + review.client.name + '</h4>' +
                                '<p>' + review.content + '</p>' +
                                '</div>' +
                                '</div>' +
                                '<div class="pull-right">' +
                                '<img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAB4AAAAeCAYAAAA7MK6iAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAF/SURBVEhL7dbNKwRxGMDx8Z63uJCLCOWgXISTixQlF6L4C5xdlLg4cFFukpc/QPkDtpxIuXOQ3ETKWw6SvH+f3ZmaHc+OmfntTCnf+tTutPs8a+3OjvUXq0FR5mZyleMGq+l7CTaGL8jyRP/qHchiMSAHkqgST3AWbyKRxuEsFbcoQeztwr1YDCLWqvEM7+JtxNokvEvFA0oRqVq0KDrQZUtBWyym4TyuDd45jShDVhPQhuXbHbKqxxG0B+fLB+bxI/lKrOAT2hNNXKMfvg1D3hJtQBR7aECg5INwAG1QUO9YQCFCVYwlyP9GG+znEn0wagTa8FxOUAfjhqAtyOUCBTBuHdoCP3ISMUo+GFfQhvtZhFG90AaLF+WY4xhGLUMbfIhmTOHRPubVisidwj3sDXLac19nNUH7zs8gUu1wDzpHD7TkhczhFc7j9xGpWThDtlCF3+rGGeQ5cuaSH5/QreEeo+l7wZOLwQ3Ij02nHAibvH0VmZuRksuk/+ws6xvtQBEuX1e9tQAAAABJRU5ErkJggg==" >' +
                                '<strong>' + '<big>' + '<big>' + review.rating + '</big>' + '</big>' + '</strong>' +
                                '</div>' +
                                '<hr>')
                        }

                    }
                });
            });
        });
    </script>
    <script>
        function redirectToCart() {
            window.location.href = '/catalog/goods/cart';
        }
    </script>
    <script src="${pageContext.request.contextPath}/resources/themes/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/themes/js/superfish.js"></script>
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <script src="/resources/themes/js/respond.min.js"></script>
    <script type="text/javascript" src="js/bootstrap-filestyle.min.js"> </script>
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

        #rate {
            width: 50px; /* Ширина поля в пикселах */
        }


    </style>
</head>
<body>
<div id="top-bar" class="container">
    <div class="row">
        <div class="span4">
            <form action="/catalog/search?page=1" method="POST" command class="search_form">
                <input name="searchStr" type="text" class="input-block-level search-query" maxlength="15"
                       Placeholder="Search games">
            </form>
        </div>
        <div class="span8">
            <div class="account pull-right">
                <ul class="user-menu">
                    <c:url value="/j_spring_security_logout" var="logoutUrl"/>
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
                            <li id="my_cart"><a href="/catalog/goods/cart">Your Cart(0)</a></li>
                        </c:if>
                        <c:if test="${cartList!=null}">
                            <li id="my_cart"><a href="/catalog/goods/cart">Your Cart(${cartList.size()})</a></li>
                            <%--<li><a href="/catalog/goods/cart">Your Cart(<span id="my_cart">${cartList.size()})</span></a></li>--%>
                        </c:if>
                        <li id="my_cart_li" style="display: none; "><a href="/catalog/goods/cart">Your Cart(<span
                                id="my_cart_new"></span>)</a></li>
                    </c:if>

                    <c:if test="${client.role.name!=null}">

                        <form action="${logoutUrl}" method="post" id="logoutForm" style="display: inline;">

                        </form>
                        <li><a href="javascript:formSubmit()">Logout</a></li>
                    </c:if>
                    <c:if test="${client.role.name==null}">
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
            <a href="${pageContext.request.contextPath}/" class="logo pull-left"><img
                    src="/resources/themes/images/logo.png" class="site_logo" alt=""></a>
            <nav id="menu" class="pull-right">
                <ul>
                    <li><a href="${pageContext.request.contextPath}/catalog/page/${1}">Catalog</a>
                        <ul>
                            <c:forEach var="categoryVar" items="${listCategory}">
                                <li>
                                    <a href="${pageContext.request.contextPath}/catalog/${categoryVar.name}/page/${1}">${categoryVar.name}</a>
                                </li>
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
        <img class="pageBanner" src="/resources/themes/images/pageBanner.png" alt="Product Detail">
        <h3><span>Product Detail</span></h3>
    </section>
    <section class="main-content">
        <div class="row">
            <div class="span9">
                <div class="row">
                    <c:if test="${client.role.name == 'ROLE_EMPLOYEE'}">
                        <div class="buttonHolder pull-left">
                            <a href="/catalog/employee/goods/image/delete?id=${imgList.get(0).id}&goodsId=${goods.id}" class="button cross" onclick="return confirm('Do you want to delete this photo?')"></a>
                        </div>
                    </c:if>
                    <div class="span4">
                        <a href="/catalog/goods/image?id=${goods.id}&number=0" class="thumbnail" data-fancybox-group="group1" title="Description 1"><img alt="" src="/catalog/goods/image?id=${goods.id}&number=0"></a>
                        <ul class="thumbnails small">
                            <c:forEach var="imgVar" begin="1" end="4" items="${imgList}" varStatus="counter">
                                <li class="span1">
                                    <a href="/catalog/goods/image?id=${goods.id}&number=${counter.index}" class="thumbnail" data-fancybox-group="group1" title="Description 2"><img src="/catalog/goods/image?id=${goods.id}&number=${counter.index}" alt=""></a>
                                </li>
                                <c:if test="${client.role.name == 'ROLE_EMPLOYEE'}">
                                <div class="buttonHolder" style="display: inline">
                                    <a href="/catalog/employee/goods/image/delete?id=${imgVar.id}&goodsId=${goods.id}" class="button cross" onclick="return confirm('Do you want to delete this photo?')"></a>
                                </div>
                                </c:if>
                            </c:forEach>
                        </ul>
                        <c:if test="${client.role.name == 'ROLE_EMPLOYEE'}">
                            <h4>Set Goods Images</h4>
                            <c:if test="${not empty msg_img}">
                                <div class="error">${msg_img}</div>
                            </c:if>
                            <form method="post" action="/catalog/employee/goods/upload/${goods.id}" enctype="multipart/form-data">
                                <table border="0">
                                    <tr>
                                        <td>Pick image #1:</td>
                                        <td><input type="file" name="fileUpload" size="50" /></td>
                                    </tr>
                                    <tr>
                                        <td>Pick image #2:</td>
                                        <td><input type="file" name="fileUpload" size="50" /></td>
                                    </tr>
                                    <tr>
                                        <td>Pick image #3:</td>
                                        <td><input type="file"  name="fileUpload" size="50" /></td>
                                    </tr>
                                    <tr>
                                        <td>Pick image #4:</td>
                                        <td><input type="file"  name="fileUpload" size="50" /></td>
                                    </tr>
                                    <tr>
                                        <td>Pick image #5:</td>
                                        <td><input type="file" name="fileUpload" size="50" /></td>
                                    </tr>
                                </table>
                                <div class="actions"><input class="btn btn-inverse pull-left" type="submit" value="Upload" /></div>
                            </form>
                        </c:if>
                    </div>
                    <c:if test="${client.role.name=='ROLE_EMPLOYEE'}">
                    <div class="span5" id="user">
                        <c:if test="${not empty msg}">
                            <div class="msg">${msg}</div>
                        </c:if>

                        <c:if test="${not empty error}">
                            <div class="error">${error}</div>
                        </c:if>
                        <spring:form action="/catalog/employee/edit" method="post" commandName="goods"
                                     class="form-stacked">
                            <fieldset>
                                <div class="control-group">
                                    <label class="control-label">Id:</label>
                                    <div class="controls">
                                        <spring:input path="id" readonly="true" type="text"
                                                      placeholder="Enter name of goods" class="input-xlarge"/>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label">Name:</label>
                                    <div class="controls">
                                        <spring:input path="name" type="text" placeholder="Enter name of goods"
                                                      class="input-xlarge"/>
                                        <spring:errors path="name" cssClass="error"/>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label">Goods receipt date:</label>
                                    <div class="controls">
                                        <spring:input path="date" readonly="true" type="text"
                                                      placeholder="Enter name of goods" class="input-xlarge"/>
                                        <spring:errors path="name" cssClass="error"/>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label">Price:</label>
                                    <div class="controls">
                                        <spring:input path="price" type="text" placeholder="Enter price"
                                                      class="input-xlarge" maxlength="7" pattern="\d+(\.\d{1})?"/>
                                        <spring:errors path="price" cssClass="error"/>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label">Number Of Players:</label>
                                    <div class="controls">
                                        <spring:select path="numberOfPlayers" class="input-xlarge">
                                            <c:forEach begin="1" end="10" varStatus="count">
                                                <spring:option value="${count.index}">${count.index}</spring:option>
                                            </c:forEach>
                                        </spring:select>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label">Duration Of The Game:</label>
                                    <div class="controls">
                                        <spring:select path="duration" class="input-xlarge">
                                            <c:forEach begin="1" end="8" varStatus="count">
                                                <spring:option
                                                        value="${count.index/2.0}">${count.index/2.0}</spring:option>
                                            </c:forEach>
                                        </spring:select>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label">Amount:</label>
                                    <div class="controls">
                                        <spring:input path="amount" type="text"
                                                      placeholder="Enter the quantity of goods" class="input-xlarge"
                                                      maxlength="7" pattern="^[ 0-9]+$"/>
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
                                            <spring:option value="${'very easy'}">Very Easy</spring:option>
                                            <spring:option value="${'easy'}">Easy</spring:option>
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
                                            <c:forEach var="categoryVar" items="${listCategory}">
                                                <spring:option
                                                        value="${categoryVar.name}">${categoryVar.name}</spring:option>
                                            </c:forEach>
                                        </spring:select>
                                    </div>
                                </div>
                                <hr>
                                <div class="actions"><input tabindex="9" class="btn btn-inverse large" type="submit"
                                                            value="Edit goods"></div>
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
                <c:if test="${client.role.name!='ROLE_EMPLOYEE'}">
                <div class="span5">
                    <address>
                        <el style="text-decoration: underline"><h3><strong>${goods.name}</strong></h3></el>
                        <td><span class="label label-info">${goodsPlace} place in the rating of shop</span></td>
                        <br>
                        <table class="table table-striped">
                            <tr>
                                <td><strong>Number of players:</strong></td>
                                <td><big><big><strong>${goods.numberOfPlayers}</strong></big></big></td>
                            </tr>
                            <tr>
                                <td><strong>Duration of the game:</strong></td>
                                <td><big><big><strong>${goods.duration}</strong></big></big></td>
                            </tr>
                            <tr>
                                <td><strong>Complexity of the rules:</strong></td>
                                <td><big><big><strong>${goods.rule.name}</strong></big></big></td>
                            </tr>
                            <tr>
                                <td><strong>Availability:</strong></td>
                                <c:if test="${goods.amount==0}">
                                    <td><big><big><strong>Out Of Stock</strong></big></big></td>
                                </c:if>
                                <c:if test="${goods.amount!=0}">
                                    <td><big><big><strong>Available</strong></big></big></td>
                                </c:if>
                            </tr>
                            <tr>
                                <td><strong>Rating:</strong></td>
                                <td><span class="label label-info">${goods.rating}</span></td>
                            </tr>
                        </table>
                    </address>
                    <h4><strong>Price: ${goods.price} &#8381;</strong></h4>
                </div>
                <c:if test="${goods.amount>0}">
                    <div class="span5">
                        <form id="cart_form" class="form-inline" action="javascript:void(0);">
                            <c:if test="${!isCartContainsGoods}">
                                <p>&nbsp;</p>
                                <label id="qty">Qty:</label>
                                <input path="quantity" id="quantity" type="text" class="span1" value="1"
                                       pattern="^[1-9][0-9]*$" title="Amount must be a integer and more then zero"/>
                                <input class="btn btn-inverse" id="btnCart" type="submit" value="Add to cart">
                            </c:if>
                            <c:if test="${isCartContainsGoods}">
                                <button class="btn" onclick="redirectToCart()">In cart</button>
                            </c:if>
                        </form>
                    </div>
                </c:if>
                <c:if test="${goods.amount<=0}">
                    <div class="span5">
                        <span style="width: 115px; height: 20px; font-size: 150%; margin-top: 3px;"
                              class="label label-warning">Out Of Stock</span>
                    </div>
                </c:if>
            </div>
            </c:if>
            <div class="row">
                <div class="span9">
                    <ul class="nav nav-tabs" id="myTab">
                        <li class="active"><a href="#home">Description</a></li>
                        <li class=""><a href="#profile">Give Feedback</a></li>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane active" id="home">${goods.description}</div>
                        <div class="tab-pane" id="profile">
                            <div id="reviews">
                                <c:forEach var="review" items="${listReviews}">
                                    <div class="media">
                                        <c:if test="${review.client.img == null}">
                                            <div class="media pull-left">
                                                <img src="/resources/themes/images/img_avatar.png" class="media-object"
                                                     style="width:60px">
                                            </div>
                                        </c:if>
                                        <c:if test="${review.client.img != null}">
                                            <div class="media pull-left">
                                                <img src="data:image/jpeg;base64,${review.client.imgBase64}"
                                                     class="media-object"
                                                     style="width:60px">
                                            </div>
                                        </c:if>
                                        <div class="media-body">
                                            <h4 class="media-heading">${review.client.name} ${review.client.surname}</h4>
                                            <p>${review.content}</p>
                                        </div>
                                    </div>
                                    <div class="pull-right">
                                        <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAB4AAAAeCAYAAAA7MK6iAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAF/SURBVEhL7dbNKwRxGMDx8Z63uJCLCOWgXISTixQlF6L4C5xdlLg4cFFukpc/QPkDtpxIuXOQ3ETKWw6SvH+f3ZmaHc+OmfntTCnf+tTutPs8a+3OjvUXq0FR5mZyleMGq+l7CTaGL8jyRP/qHchiMSAHkqgST3AWbyKRxuEsFbcoQeztwr1YDCLWqvEM7+JtxNokvEvFA0oRqVq0KDrQZUtBWyym4TyuDd45jShDVhPQhuXbHbKqxxG0B+fLB+bxI/lKrOAT2hNNXKMfvg1D3hJtQBR7aECg5INwAG1QUO9YQCFCVYwlyP9GG+znEn0wagTa8FxOUAfjhqAtyOUCBTBuHdoCP3ISMUo+GFfQhvtZhFG90AaLF+WY4xhGLUMbfIhmTOHRPubVisidwj3sDXLac19nNUH7zs8gUu1wDzpHD7TkhczhFc7j9xGpWThDtlCF3+rGGeQ5cuaSH5/QreEeo+l7wZOLwQ3Ij02nHAibvH0VmZuRksuk/+ws6xvtQBEuX1e9tQAAAABJRU5ErkJggg==">
                                        <strong><big><big>${review.rating}</big></big></strong>
                                    </div>
                                    <hr>
                                </c:forEach>
                            </div>
                            <c:if test="${client==null}">
                                <div class="msg">To leave a review please log in</div>
                            </c:if>
                            <c:if test="${client.role.name=='ROLE_CLIENT'}">
                                <div class="row-fluid">
                                    <form id="review_form" action="javascript:void(0);" method="post">
                                        <div class="control-group">
                                            <label for="textarea" class="control-label">Comments</label>
                                            <div class="controls">
                                                <textarea name="content" rows="3" id="textarea"
                                                          class="span12"></textarea>
                                            </div>
                                        </div>
                                        <select name="rating" class="input-xlarge" id="rate">
                                            <c:forEach begin="1" end="10" varStatus="count">
                                                <option value="${count.index}">${count.index}</option>
                                            </c:forEach>
                                        </select>
                                        <big><strong> Rate product</strong></big>
                                        <input class="btn btn-inverse pull-right" type="submit" value="Leave review">
                                    </form>
                                </div>
                                <div id="review_msg" style="display: none" class="msg">Your review has been successfully
                                    added! Thank you!
                                </div>
                                <div id="review_error" style="display: none" class="error">Operation is not available!
                                    You have already left review
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
                <c:if test="${client.role.name!='ROLE_EMPLOYEE'}">
                    <div class="span9">
                        <br>
                        <h4 class="title">
                            <span class="pull-left"><span class="text"><strong>Related</strong> Products</span></span>
                        </h4>
                        <div id="myCarousel-1" class="carousel slide">
                            <div class="carousel-inner">
                                <div class="active item">
                                    <ul class="thumbnails listing-products">
                                        <c:if test="${relatedGoodsList.size()!= 0}">
                                            <c:forEach var="goodsVar" begin="0" end="2" items="${relatedGoodsList}">
                                                <li class="span3">
                                                    <div class="product-box">
                                                        <span class="sale_tag"></span>
                                                        <a href="${pageContext.request.contextPath}/catalog/goods/${goodsVar.id}"><img
                                                                alt="" src="/catalog/goods/image?id=${goodsVar.id}&number=0"></a><br/>
                                                        <a href="${pageContext.request.contextPath}/catalog/goods/${goodsVar.id}">${goodsVar.name}</a><br/>
                                                        <a href="#" class="category">${goodsVar.category.name}</a>
                                                        <p class="price">${goodsVar.price} &#8381;</p>
                                                    </div>
                                                </li>
                                            </c:forEach>
                                        </c:if>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
        <div class="span3 col">
            <div class="block">
                <c:if test="${client.role.name=='ROLE_EMPLOYEE'}">
                    <form action="/employee/administration">
                        <button type="submit" class="btn btn-inverse">Add new Goods or Category</button>
                    </form>
                </c:if>
                <ul class="nav nav-list">
                    <li class="nav-header">SUB CATEGORIES</li>
                    <li><a href="${pageContext.request.contextPath}/catalog/page/${1}">All games</a></li>
                    <c:forEach var="categoryVar" items="${listCategory}">
                        <li
                                <c:if test="${categoryVar.name == categoryName}">
                                    class="active"
                                </c:if>
                        >
                            <a href="${pageContext.request.contextPath}/catalog/${categoryVar.name}/page/${1}">${categoryVar.name}</a>
                        </li>
                    </c:forEach>
                </ul>
                <br/>
            </div>
            <div class="block">
                <h4 class="title">
                    <span class="pull-left"><span class="text">Randomize</span></span>
                    <span class="pull-right">
									<a class="left button" href="#myCarousel" data-slide="prev"></a><a
                            class="right button" href="#myCarousel" data-slide="next"></a>
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
                                    <a href="${pageContext.request.contextPath}/catalog/goods/${goodsVar.id}"><img
                                            alt="" src="/catalog/goods/image?id=${goodsVar.id}&number=0"></a><br/>
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
                        <a href="${pageContext.request.contextPath}/catalog/goods/${goodsVar.id}"
                           title="${goodsVar.name}">
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
            <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. the Lorem Ipsum has been the
                industry's standard dummy text ever since the you.</p>
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
    $(document).ready(function () {
        $('.thumbnail').fancybox({
            openEffect: 'none',
            closeEffect: 'none'
        });

        $('#myCarousel-2').carousel({
            interval: 2500
        });
    });
</script>

</body>
</html>