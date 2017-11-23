<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Administration Page</title>
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
            <form action="/catalog/search" method="POST" command class="search_form">
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
                    <c:if test="${client.role.name!=null}">

                        <form action="${logoutUrl}" method="post" id="logoutForm" style="display: inline;">

                            <input type="hidden" size="0"
                                   name="${_csrf.parameterName}"
                                   value="${_csrf.token}"/>
                        </form>
                        <li><a href="javascript:formSubmit()">Logout</a></li>
                    </c:if>
                    <c:if test="${client.role.name==null}">
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
                    <li><a href="goods.jsp">How To Buy</a></li>
                    <li><a href="goods.jsp">F.A.Q</a></li>
                    <li><a href="goods.jsp">About us</a></li>
                </ul>
            </nav>
        </div>
    </section>
    <section class="header_text sub">
        <img class="pageBanner" src="/resources/themes/images/pageBanner.png" alt="Login or register">
        <h4><span>ADMINISTRATION PAGE</span></h4>
    </section>
    </section>
    <section class="main-content">
        <div class="row">
            <div class="span12">
                <div class="accordion" id="accordion2">
                    <div class="accordion-group">
                        <div class="accordion-heading">
                            <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2"
                               href="#collapseOne">Add new Goods or Category</a>
                        </div>
                        <div id="collapseOne" class="accordion-body collapse">
                            <div class="accordion-inner">
                                <div class="row-fluid">
                                    <div class="span5">
                                        <h4 class="title"><span class="text"><strong>New Category</strong> Form</span>
                                        </h4>
                                        <c:if test="${not empty msgC}">
                                            <div class="msg">${msgC}</div>
                                        </c:if>
                                        <c:if test="${not empty error2}">
                                            <div class="error">${error2}</div>
                                        </c:if>
                                        <spring:form action="/catalog/employee/add/category" method="post"
                                                     commandName="category" class="form-stacked">
                                            <fieldset>
                                                <div class="control-group">
                                                    <label class="control-label">Name:</label>
                                                    <div class="controls">
                                                        <spring:input path="name" type="text"
                                                                      placeholder="Enter name of new category"
                                                                      class="input-xlarge"/>
                                                        <spring:errors path="name" cssClass="error"/>
                                                    </div>
                                                </div>
                                                <hr>
                                                <div class="actions"><input tabindex="9" class="btn btn-inverse large"
                                                                            type="submit" value="Add new category">
                                                </div>
                                            </fieldset>
                                        </spring:form>
                                    </div>
                                    <%--</div>--%>
                                    <div class="span7">
                                        <h4 class="title"><span class="text"><strong>New Goods</strong> Form</span></h4>
                                        <c:if test="${not empty error1}">
                                            <div class="error">${error1}</div>
                                        </c:if>
                                        <c:if test="${not empty msgG}">
                                            <div class="msg">${msgG}</div>
                                        </c:if>
                                        <spring:form action="/catalog/employee/add/goods" method="post"
                                                     commandName="goods" class="form-stacked">
                                            <fieldset>
                                                <div class="control-group">
                                                    <label class="control-label">Name:</label>
                                                    <div class="controls">
                                                        <spring:input path="name" type="text"
                                                                      placeholder="Enter name of goods"
                                                                      class="input-xlarge"/>
                                                        <fieldset>
                                                            <spring:errors path="name" cssClass="error"/>
                                                        </fieldset>
                                                    </div>
                                                </div>
                                                <div class="control-group">
                                                    <label class="control-label">Price:</label>
                                                    <div class="controls">
                                                        <spring:input path="price" type="text" placeholder="Enter price"
                                                                      class="input-xlarge" pattern="\d+(\.\d{2})?"
                                                                      title="Price must be a number"/>
                                                        <fieldset>
                                                            <spring:errors path="price" cssClass="error"/>
                                                        </fieldset>
                                                    </div>
                                                </div>
                                                <div class="control-group">
                                                    <label class="control-label">Number Of Players:</label>
                                                    <div class="controls">
                                                        <spring:select path="numberOfPlayers" class="input-xlarge">
                                                            <c:forEach begin="1" end="10" varStatus="count">
                                                                <spring:option
                                                                        value="${count.index}">${count.index}</spring:option>
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
                                                                      placeholder="Enter the quantity of goods"
                                                                      class="input-xlarge" pattern="^[ 0-9]+$"
                                                                      title="Amount must be a integer"/>
                                                        <fieldset>
                                                            <spring:errors path="amount" cssClass="error"/>
                                                        </fieldset>
                                                    </div>
                                                </div>
                                                <div class="control-group">
                                                    <label class="control-label">Is Visible:</label>
                                                    <div class="controls">
                                                        <spring:select path="visible" class="input-xlarge">
                                                            <spring:option value="1">Yes</spring:option>
                                                            <spring:option value="0">No</spring:option>
                                                        </spring:select>
                                                            <%--<spring:input path="visible" type="text" placeholder="Show goods at the store?" class="input-xlarge"/>--%>
                                                    </div>
                                                </div>
                                                <div class="control-group">
                                                    <label class="control-label">Description:</label>
                                                    <div class="controls">
                                                        <spring:textarea path="description" cols="20" rows="5"/>
                                                        <fieldset>
                                                            <spring:errors path="description" cssClass="error"/>
                                                        </fieldset>
                                                    </div>
                                                </div>
                                                <div class="control-group">
                                                    <label class="control-label">Complexity Of Rules:</label>
                                                    <div class="controls">
                                                        <spring:select path="rule.name" class="input-xlarge">
                                                            <spring:option value="${'easy'}">Easy</spring:option>
                                                            <spring:option
                                                                    value="${'very easy'}">Very Easy</spring:option>
                                                            <spring:option value="${'medium'}">Medium</spring:option>
                                                            <spring:option value="${'hard'}">Hard</spring:option>
                                                            <spring:option
                                                                    value="${'very hard'}">Very Hard</spring:option>
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
                                                <div class="control-group">
                                                    <label class="control-label">Image (1x1):</label>
                                                    <div class="controls">
                                                        <spring:input path="img" type="text"
                                                                      placeholder="Put URL of image here"
                                                                      class="input-xlarge"/>
                                                        <fieldset>
                                                            <spring:errors path="img" cssClass="error"/>
                                                        </fieldset>
                                                    </div>
                                                </div>
                                                <hr>
                                                <div class="actions"><input tabindex="9" class="btn btn-inverse large"
                                                                            type="submit" value="Add new goods"></div>
                                            </fieldset>
                                            <%--<input type="hidden"--%>
                                            <%--name="${_csrf.parameterName}"--%>
                                            <%--value="${_csrf.token}"/>--%>
                                        </spring:form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="accordion-group">
                        <div class="accordion-heading">
                            <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2"
                               href="#collapseTwo">Show All Orders</a>
                        </div>
                        <div id="collapseTwo" class="accordion-body collapse">
                            <div class="accordion-inner">

                                <h4>All Orders</h4>
                                <label class="checkbox">
                                    <input type="checkbox" onclick="hideStuff('closed-row')" id="hide-check"> Hide
                                    closed orders
                                </label>
                                <%--<input type="submit" onclick="hideStuff('closed-row')" id="hide">--%>
                                <%--<div id="msg"></div>--%>
                                <%--<form id="all-orders" action="" method="GET"></form>--%>
                                <c:if test="${clientOrdersList.size()!=0}">

                                    <table class="table table-striped">
                                        <thead>
                                        <tr>
                                            <th><big>Order ID:</big></th>
                                            <th><big>Client ID:</big></th>
                                            <th><big>Phone number:</big></th>
                                            <th><big>Pay Status:</big></th>
                                            <th><big>Order Status:</big></th>
                                            <th><big>Date:</big></th>
                                            <th><big>Order Details:</big></th>
                                        </tr>
                                        </thead>

                                        <c:forEach var="clientOrder" items="${clientOrdersList}">
                                            <tbody>
                                                <%--<c:if test="${not(clientOrder.status.name=='closed' and hide == true)}">--%>
                                            <tr
                                                    <c:if test="${clientOrder.status.name=='closed'}">
                                                        id="closed-row${clientOrder.id}"
                                                    </c:if>
                                            >
                                                <td><big>${clientOrder.id}</big></td>
                                                <td><big>${clientOrder.client.id}</big></td>
                                                <td><big>${clientOrder.client.phone}</big></td>
                                                <c:if test="${clientOrder.payStatus==1}">
                                                    <td><big>Paid</big></td>
                                                </c:if>
                                                <c:if test="${clientOrder.payStatus==0}">
                                                    <td><big>Pending payment</big></td>
                                                </c:if>
                                                <td>
                                                    <c:if test="${clientOrder.status.name=='canceled'}">
                                                        <span class="label label-danger">Canceled</span>
                                                    </c:if>
                                                    <c:if test="${clientOrder.status.name=='awaiting shipment'}">
                                                        <span class="label label-default">Awaiting Shipment</span>
                                                    </c:if>
                                                    <c:if test="${clientOrder.status.name=='shipped'}">
                                                        <span class="label label-info">Shipped</span>
                                                    </c:if>
                                                    <c:if test="${clientOrder.status.name=='closed'}">
                                                        <span class="label label-success">Closed</span>
                                                    </c:if>
                                                </td>
                                                <td><big>${clientOrder.date}</big></td>
                                                <td>
                                                    <a href="/order/employee/details/${clientOrder.id}"><big>Details</big></a>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </c:forEach>

                                    </table>
                                </c:if>

                            </div>
                        </div>
                    </div>
                    <div class="accordion-group">
                        <div class="accordion-heading">
                            <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2"
                               href="#collapseThree">Show Statistic</a>
                        </div>
                        <div id="collapseThree" class="accordion-body collapse">
                            <div class="accordion-inner">
                                <div class="row-fluid">
                                    <div class="span5">
                                        <h4 class="title"><span class="text"><strong>TOP 10</strong> Goods</span></h4>
                                        <table class="table table-striped" border="3">
                                            <thead>
                                            <tr>
                                                <th><big>ID:</big></th>
                                                <th><big>Name:</big></th>
                                                <th><big>Price:</big></th>
                                                <th><big>Sales Counter:</big></th>
                                                <th></th>
                                            </tr>
                                            </thead>

                                            <c:forEach var="goodsVar" items="${bestSellersList}" varStatus="count">
                                                <tbody>
                                                <tr>
                                                    <td><big>${goodsVar.id}</big></td>
                                                    <td><big>${goodsVar.name}</big></td>
                                                    <td><big>${goodsVar.price} &#8381;</big></td>
                                                    <td><big style="margin-left: 30%">${goodsVar.salesCounter}</big>
                                                    </td>
                                                    <td><a href="/catalog/goods/${goodsVar.id}"><big>Details</big></a>
                                                    </td>
                                                </tr>
                                                </tbody>
                                            </c:forEach>

                                        </table>
                                    </div>
                                    <div class="span6">
                                        <h4 class="title"><span class="text"><strong>TOP 10</strong> Clients</span></h4>
                                        <table class="table table-striped" border="3">
                                            <thead>
                                            <tr>
                                                <th><big>ID:</big></th>
                                                <th><big>Name:</big></th>
                                                <th><big>E-mail:</big></th>
                                                <th><big>Amount of Orders:</big></th>
                                                <th><big>Total:</big></th>
                                            </tr>
                                            </thead>

                                            <c:forEach var="clientVar" items="${bestClientsList}" varStatus="count">
                                                <tbody>
                                                <tr>
                                                    <td><big>${clientVar.id}</big></td>
                                                    <td><big>${clientVar.name}</big></td>
                                                    <td><big>${clientVar.email}</big></td>
                                                    <td><big
                                                            style="margin-left: 30%">${amountOfOrders.get(count.index)}</big>
                                                    </td>
                                                    <td><big>${clientVar.orderCounter} &#8381;</big></td>
                                                </tr>
                                                </tbody>
                                            </c:forEach>

                                        </table>
                                        <hr>
                                        <h4 class="title"><span class="text"><strong>Monthly </strong>revenue</span>
                                        </h4>
                                        <table class="table">
                                            <thead>
                                            <tr>
                                                <th><big>Monthly Revenue: ${monthlyRevenue} &#8381;</big></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <th><strong><big>Day Revenue: ${todayMoney} &#8381;</big></strong></th>
                                            </tr>
                                            </tbody>
                                        </table>
                                        <div id="chart_div"></div>
                                    </div>
                                </div>
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
                    <li><a href="./contact.html">Contact Us</a></li>
                    <li><a href="./register.jsp">Login</a></li>
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
                <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. the Lorem Ipsum has been
                    the industry's standard dummy text ever since the you.</p>
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
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script>
    $(document).ready(function () {
        $('#checkout').click(function (e) {
            document.location.href = "register.jsp";
        })
    });

</script>
<script type="text/javascript">
    function hideStuff(id) {
        if ($('#hide-check').prop('checked')) {
            <c:forEach var="clientOrder"  items="${clientOrdersList}">
            <c:if test="${clientOrder.status.name=='closed'}">
            document.getElementById("" + id +${clientOrder.id}).style.display = 'none';
            </c:if>
            </c:forEach>
        } else {
            <c:forEach var="clientOrder"  items="${clientOrdersList}">
            <c:if test="${clientOrder.status.name=='closed'}">
            document.getElementById("" + id +${clientOrder.id}).style.display = 'table-row';
            </c:if>
            </c:forEach>
        }
    }
</script>
<script>
    google.charts.load('current', {packages: ['corechart', 'line']});
    google.charts.setOnLoadCallback(drawBasic);

    function drawBasic() {

        var data = new google.visualization.DataTable();
        data.addColumn('number', 'X');
        data.addColumn('number', 'Revenue');
        var table = [];
        <c:forEach var="dayRev" items="${dataArray}" varStatus="count">
                table.push([
                    ${count.count},
                    ${dayRev}
                ])
        </c:forEach>
        data.addRows(table);

        var options = {
            hAxis: {
                title: 'Days'
            },
            vAxis: {
                title: 'Revenue'
            }
        };


        var chart = new google.visualization.LineChart(document.getElementById('chart_div'));

        chart.draw(data, options);
    }
</script>
</body>
</html>