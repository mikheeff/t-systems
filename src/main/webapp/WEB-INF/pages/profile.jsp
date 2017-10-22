<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>My Account</title>
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
		<img class="pageBanner" src="/resources/themes/images/pageBanner.png" alt="" >
				<h4><span>${client.name.toUpperCase()}, its your profile</span></h4>
				<c:if test="${client.role.name!='ROLE_CLIENT'}" >
					<br>
					<h4><span>Your role is: ${client.role.name}</span></h4>
				</c:if>
			</section>
			<c:if test="${not empty error}">
				<div class="error">${error}</div>
			</c:if>
			<c:if test="${not empty errorMatch}">
				<div class="error">${errorMatch}</div>
			</c:if>
			<c:if test="${not empty errorInvalidPass}">
				<div class="error">${errorInvalidPass}</div>
			</c:if>
	<c:if test="${not empty msg}">
				<div class="msg">${msg}</div>
			</c:if>
			<section class="main-content">
				<div class="row">
					<div class="span12">
						<div class="accordion" id="accordion2">
							<div class="accordion-group">
								<div class="accordion-heading">
									<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseOne">Account Details</a>
								</div>
								<div id="collapseOne" class="accordion-body collapse">
									<div class="accordion-inner">
										<spring:form action="edit" method="post" modelAttribute="client" class="form-stacked">
										<div class="row-fluid">
											<div class="span6">
												<h4>Your Personal Details</h4>
													<fieldset>
														<div class="control-group">
															<label class="control-label">Your ID</label>
															<div class="controls">
																<spring:input path="id" readonly="true" type="text" placeholder="" class="input-xlarge"/>
															</div>
														</div>
														<div class="control-group">
															<label class="control-label">First Name*</label>
															<div class="controls">
																<spring:input path="name" type="text" placeholder="" class="input-xlarge"/>
															</div>
														</div>
														<div class="control-group">
															<label class="control-label">Last Name</label>
															<div class="controls">
																<spring:input path="surname" type="text" placeholder="" class="input-xlarge"/>
															</div>
														</div>
														<%--<div class="control-group">--%>
															<%--<label class="control-label">Birthday</label>--%>
															<%--<div class="controls">--%>
																<%--<spring:input path="birthdate" type="date" placeholder="" class="input-xlarge"/>--%>
															<%--</div>--%>
														<%--</div>--%>
														<div class="control-group">
															<label class="control-label">Email Address*</label>
															<div class="controls">
																<spring:input path="email" type="text" placeholder="" class="input-xlarge"/>
															</div>
														</div>

														<div class="control-group">
															<label class="control-label">Phone number</label>
															<div class="controls">
																<spring:input path="phone" type="text" placeholder="" class="input-xlarge"/>
															</div>
														</div>
													</fieldset>
											</div>
											<div class="span6">
												<h4>Your Address</h4>
												<fieldset>
													<div class="control-group">
														<label class="control-label"><span class="required"></span> Country:</label>
														<div class="controls">
															<spring:select path="clientAddress.country" class="input-xlarge">
																<spring:option value="">---Please Select---</spring:option>
																<spring:option value="AFG">Afghanistan</spring:option>
																<spring:option value="ALA">Åland Islands</spring:option>
																<spring:option value="ALB">Albania</spring:option>
																<spring:option value="DZA">Algeria</spring:option>
																<spring:option value="ASM">American Samoa</spring:option>
																<spring:option value="AND">Andorra</spring:option>
																<spring:option value="AGO">Angola</spring:option>
																<spring:option value="AIA">Anguilla</spring:option>
																<spring:option value="ATA">Antarctica</spring:option>
																<spring:option value="ATG">Antigua and Barbuda</spring:option>
																<spring:option value="ARG">Argentina</spring:option>
																<spring:option value="ARM">Armenia</spring:option>
																<spring:option value="ABW">Aruba</spring:option>
																<spring:option value="AUS">Australia</spring:option>
																<%--<spring:option value="AUT">Austria</spring:option>--%>
																<%--<spring:option value="AZE">Azerbaijan</spring:option>--%>
																<%--<spring:option value="BHS">Bahamas</spring:option>--%>
																<%--<spring:option value="BHR">Bahrain</spring:option>--%>
																<%--<spring:option value="BGD">Bangladesh</spring:option>--%>
																<%--<spring:option value="BRB">Barbados</spring:option>--%>
																<%--<spring:option value="BLR">Belarus</spring:option>--%>
																<%--<spring:option value="BEL">Belgium</spring:option>--%>
																<%--<spring:option value="BLZ">Belize</spring:option>--%>
																<%--<spring:option value="BEN">Benin</spring:option>--%>
																<%--<spring:option value="BMU">Bermuda</spring:option>--%>
																<%--<spring:option value="BTN">Bhutan</spring:option>--%>
																<%--<spring:option value="BOL">Bolivia, Plurinational State of</spring:option>--%>
																<%--<spring:option value="BES">Bonaire, Sint Eustatius and Saba</spring:option>--%>
																<spring:option value="BIH">Bosnia and Herzegovina</spring:option>
																<spring:option value="BWA">Botswana</spring:option>
																<spring:option value="BVT">Bouvet Island</spring:option>
																<spring:option value="BRA">Brazil</spring:option>
																<spring:option value="IOT">British Indian Ocean Territory</spring:option>
																<spring:option value="BRN">Brunei Darussalam</spring:option>
																<spring:option value="BGR">Bulgaria</spring:option>
																<spring:option value="BFA">Burkina Faso</spring:option>
																<spring:option value="BDI">Burundi</spring:option>
																<spring:option value="KHM">Cambodia</spring:option>
																<spring:option value="CMR">Cameroon</spring:option>
																<spring:option value="CAN">Canada</spring:option>
																<spring:option value="CPV">Cape Verde</spring:option>
																<spring:option value="CYM">Cayman Islands</spring:option>
																<spring:option value="CAF">Central African Republic</spring:option>
																<spring:option value="TCD">Chad</spring:option>
																<spring:option value="CHL">Chile</spring:option>
																<spring:option value="CHN">China</spring:option>
																<spring:option value="CXR">Christmas Island</spring:option>
																<spring:option value="CCK">Cocos (Keeling) Islands</spring:option>
																<spring:option value="COL">Colombia</spring:option>
																<spring:option value="COM">Comoros</spring:option>
																<spring:option value="COG">Congo</spring:option>
																<spring:option value="COD">Congo, the Democratic Republic of the</spring:option>
																<spring:option value="COK">Cook Islands</spring:option>
																<spring:option value="CRI">Costa Rica</spring:option>
																<spring:option value="CIV">Côte d'Ivoire</spring:option>
																<spring:option value="HRV">Croatia</spring:option>
																<spring:option value="CUB">Cuba</spring:option>
																<spring:option value="CUW">Curaçao</spring:option>
																<spring:option value="CYP">Cyprus</spring:option>
																<spring:option value="CZE">Czech Republic</spring:option>
																<spring:option value="DNK">Denmark</spring:option>
																<spring:option value="DJI">Djibouti</spring:option>
																<spring:option value="DMA">Dominica</spring:option>
																<spring:option value="DOM">Dominican Republic</spring:option>
																<spring:option value="ECU">Ecuador</spring:option>
																<spring:option value="EGY">Egypt</spring:option>
																<spring:option value="SLV">El Salvador</spring:option>
																<spring:option value="GNQ">Equatorial Guinea</spring:option>
																<spring:option value="ERI">Eritrea</spring:option>
																<spring:option value="EST">Estonia</spring:option>
																<spring:option value="ETH">Ethiopia</spring:option>
																<spring:option value="FLK">Falkland Islands (Malvinas)</spring:option>
																<spring:option value="FRO">Faroe Islands</spring:option>
																<spring:option value="FJI">Fiji</spring:option>
																<spring:option value="FIN">Finland</spring:option>
																<spring:option value="FRA">France</spring:option>
																<spring:option value="GUF">French Guiana</spring:option>
																<spring:option value="PYF">French Polynesia</spring:option>
																<spring:option value="ATF">French Southern Territories</spring:option>
																<spring:option value="GAB">Gabon</spring:option>
																<spring:option value="GMB">Gambia</spring:option>
																<spring:option value="GEO">Georgia</spring:option>
																<spring:option value="DEU">Germany</spring:option>
																<spring:option value="GHA">Ghana</spring:option>
																<spring:option value="GIB">Gibraltar</spring:option>
																<%--<spring:option value="GRC">Greece</spring:option>--%>
																<%--<spring:option value="GRL">Greenland</spring:option>--%>
																<%--<spring:option value="GRD">Grenada</spring:option>--%>
																<%--<spring:option value="GLP">Guadeloupe</spring:option>--%>
																<%--<spring:option value="GUM">Guam</spring:option>--%>
																<%--<spring:option value="GTM">Guatemala</spring:option>--%>
																<%--<spring:option value="GGY">Guernsey</spring:option>--%>
																<%--<spring:option value="GIN">Guinea</spring:option>--%>
																<%--<spring:option value="GNB">Guinea-Bissau</spring:option>--%>
																<%--<spring:option value="GUY">Guyana</spring:option>--%>
																<%--<spring:option value="HTI">Haiti</spring:option>--%>
																<%--<spring:option value="HMD">Heard Island and McDonald Islands</spring:option>--%>
																<%--<spring:option value="VAT">Holy See (Vatican City State)</spring:option>--%>
																<%--<spring:option value="HND">Honduras</spring:option>--%>
																<%--<spring:option value="HKG">Hong Kong</spring:option>--%>
																<%--<spring:option value="HUN">Hungary</spring:option>--%>
																<%--<spring:option value="ISL">Iceland</spring:option>--%>
																<%--<spring:option value="IND">India</spring:option>--%>
																<%--<spring:option value="IDN">Indonesia</spring:option>--%>
																<%--<spring:option value="IRN">Iran, Islamic Republic of</spring:option>--%>
																<%--<spring:option value="IRQ">Iraq</spring:option>--%>
																<%--<spring:option value="IRL">Ireland</spring:option>--%>
																<%--<spring:option value="IMN">Isle of Man</spring:option>--%>
																<%--<spring:option value="ISR">Israel</spring:option>--%>
																<%--<spring:option value="ITA">Italy</spring:option>--%>
																<%--<spring:option value="JAM">Jamaica</spring:option>--%>
																<%--<spring:option value="JPN">Japan</spring:option>--%>
																<%--<spring:option value="JEY">Jersey</spring:option>--%>
																<%--<spring:option value="JOR">Jordan</spring:option>--%>
																<%--<spring:option value="KAZ">Kazakhstan</spring:option>--%>
																<%--<spring:option value="KEN">Kenya</spring:option>--%>
																<%--<spring:option value="KIR">Kiribati</spring:option>--%>
																<%--<spring:option value="PRK">Korea, Democratic People's Republic of</spring:option>--%>
																<%--<spring:option value="KOR">Korea, Republic of</spring:option>--%>
																<%--<spring:option value="KWT">Kuwait</spring:option>--%>
																<%--<spring:option value="KGZ">Kyrgyzstan</spring:option>--%>
																<%--<spring:option value="LAO">Lao People's Democratic Republic</spring:option>--%>
																<%--<spring:option value="LVA">Latvia</spring:option>--%>
																<%--<spring:option value="LBN">Lebanon</spring:option>--%>
																<%--<spring:option value="LSO">Lesotho</spring:option>--%>
																<%--<spring:option value="LBR">Liberia</spring:option>--%>
																<%--<spring:option value="LBY">Libya</spring:option>--%>
																<%--<spring:option value="LIE">Liechtenstein</spring:option>--%>
																<%--<spring:option value="LTU">Lithuania</spring:option>--%>
																<%--<spring:option value="LUX">Luxembourg</spring:option>--%>
																<%--<spring:option value="MAC">Macao</spring:option>--%>
																<%--<spring:option value="MKD">Macedonia, the former Yugoslav Republic of</spring:option>--%>
																<%--<spring:option value="MDG">Madagascar</spring:option>--%>
																<%--<spring:option value="MWI">Malawi</spring:option>--%>
																<%--<spring:option value="MYS">Malaysia</spring:option>--%>
																<%--<spring:option value="MDV">Maldives</spring:option>--%>
																<%--<spring:option value="MLI">Mali</spring:option>--%>
																<%--<spring:option value="MLT">Malta</spring:option>--%>
																<%--<spring:option value="MHL">Marshall Islands</spring:option>--%>
																<%--<spring:option value="MTQ">Martinique</spring:option>--%>
																<%--<spring:option value="MRT">Mauritania</spring:option>--%>
																<%--<spring:option value="MUS">Mauritius</spring:option>--%>
																<%--<spring:option value="MYT">Mayotte</spring:option>--%>
																<%--<spring:option value="MEX">Mexico</spring:option>--%>
																<%--<spring:option value="FSM">Micronesia, Federated States of</spring:option>--%>
																<%--<spring:option value="MDA">Moldova, Republic of</spring:option>--%>
																<%--<spring:option value="MCO">Monaco</spring:option>--%>
																<%--<spring:option value="MNG">Mongolia</spring:option>--%>
																<%--<spring:option value="MNE">Montenegro</spring:option>--%>
																<%--<spring:option value="MSR">Montserrat</spring:option>--%>
																<%--<spring:option value="MAR">Morocco</spring:option>--%>
																<%--<spring:option value="MOZ">Mozambique</spring:option>--%>
																<%--<spring:option value="MMR">Myanmar</spring:option>--%>
																<%--<spring:option value="NAM">Namibia</spring:option>--%>
																<%--<spring:option value="NRU">Nauru</spring:option>--%>
																<%--<spring:option value="NPL">Nepal</spring:option>--%>
																<%--<spring:option value="NLD">Netherlands</spring:option>--%>
																<spring:option value="NCL">New Caledonia</spring:option>
																<spring:option value="NZL">New Zealand</spring:option>
																<spring:option value="NIC">Nicaragua</spring:option>
																<spring:option value="NER">Niger</spring:option>
																<spring:option value="NGA">Nigeria</spring:option>
																<spring:option value="NIU">Niue</spring:option>
																<spring:option value="NFK">Norfolk Island</spring:option>
																<spring:option value="MNP">Northern Mariana Islands</spring:option>
																<spring:option value="NOR">Norway</spring:option>
																<spring:option value="OMN">Oman</spring:option>
																<spring:option value="PAK">Pakistan</spring:option>
																<spring:option value="PLW">Palau</spring:option>
																<spring:option value="PSE">Palestinian Territory, Occupied</spring:option>
																<spring:option value="PAN">Panama</spring:option>
																<spring:option value="PNG">Papua New Guinea</spring:option>
																<spring:option value="PRY">Paraguay</spring:option>
																<spring:option value="PER">Peru</spring:option>
																<spring:option value="PHL">Philippines</spring:option>
																<spring:option value="PCN">Pitcairn</spring:option>
																<spring:option value="POL">Poland</spring:option>
																<spring:option value="PRT">Portugal</spring:option>
																<spring:option value="PRI">Puerto Rico</spring:option>
																<spring:option value="QAT">Qatar</spring:option>
																<spring:option value="REU">Réunion</spring:option>
																<spring:option value="ROU">Romania</spring:option>
																<spring:option value="RUS">Russian Federation</spring:option>
																<spring:option value="RWA">Rwanda</spring:option>
																<spring:option value="BLM">Saint Barthélemy</spring:option>
																<spring:option value="SHN">Saint Helena, Ascension and Tristan da Cunha</spring:option>
																<spring:option value="KNA">Saint Kitts and Nevis</spring:option>
																<spring:option value="LCA">Saint Lucia</spring:option>
																<spring:option value="MAF">Saint Martin (French part)</spring:option>
																<spring:option value="SPM">Saint Pierre and Miquelon</spring:option>
																<spring:option value="VCT">Saint Vincent and the Grenadines</spring:option>
																<%--<spring:option value="WSM">Samoa</spring:option>--%>
																<%--<spring:option value="SMR">San Marino</spring:option>--%>
																<%--<spring:option value="STP">Sao Tome and Principe</spring:option>--%>
																<%--<spring:option value="SAU">Saudi Arabia</spring:option>--%>
																<%--<spring:option value="SEN">Senegal</spring:option>--%>
																<%--<spring:option value="SRB">Serbia</spring:option>--%>
																<%--<spring:option value="SYC">Seychelles</spring:option>--%>
																<%--<spring:option value="SLE">Sierra Leone</spring:option>--%>
																<%--<spring:option value="SGP">Singapore</spring:option>--%>
																<%--<spring:option value="SXM">Sint Maarten (Dutch part)</spring:option>--%>
																<%--<spring:option value="SVK">Slovakia</spring:option>--%>
																<%--<spring:option value="SVN">Slovenia</spring:option>--%>
																<%--<spring:option value="SLB">Solomon Islands</spring:option>--%>
																<%--<spring:option value="SOM">Somalia</spring:option>--%>
																<%--<spring:option value="ZAF">South Africa</spring:option>--%>
																<%--<spring:option value="SGS">South Georgia and the South Sandwich Islands</spring:option>--%>
																<%--<spring:option value="SSD">South Sudan</spring:option>--%>
																<%--<spring:option value="ESP">Spain</spring:option>--%>
																<%--<spring:option value="LKA">Sri Lanka</spring:option>--%>
																<%--<spring:option value="SDN">Sudan</spring:option>--%>
																<%--<spring:option value="SUR">Suriname</spring:option>--%>
																<%--<spring:option value="SJM">Svalbard and Jan Mayen</spring:option>--%>
																<%--<spring:option value="SWZ">Swaziland</spring:option>--%>
																<%--<spring:option value="SWE">Sweden</spring:option>--%>
																<%--<spring:option value="CHE">Switzerland</spring:option>--%>
																<%--<spring:option value="SYR">Syrian Arab Republic</spring:option>--%>
																<%--<spring:option value="TWN">Taiwan, Province of China</spring:option>--%>
																<%--<spring:option value="TJK">Tajikistan</spring:option>--%>
																<%--<spring:option value="TZA">Tanzania, United Republic of</spring:option>--%>
																<%--<spring:option value="THA">Thailand</spring:option>--%>
																<%--<spring:option value="TLS">Timor-Leste</spring:option>--%>
																<%--<spring:option value="TGO">Togo</spring:option>--%>
																<%--<spring:option value="TKL">Tokelau</spring:option>--%>
																<%--<spring:option value="TON">Tonga</spring:option>--%>
																<%--<spring:option value="TTO">Trinidad and Tobago</spring:option>--%>
																<%--<spring:option value="TUN">Tunisia</spring:option>--%>
																<spring:option value="TUR">Turkey</spring:option>
																<spring:option value="TKM">Turkmenistan</spring:option>
																<spring:option value="TCA">Turks and Caicos Islands</spring:option>
																<spring:option value="TUV">Tuvalu</spring:option>
																<spring:option value="UGA">Uganda</spring:option>
																<spring:option value="UKR">Ukraine</spring:option>
																<spring:option value="ARE">United Arab Emirates</spring:option>
																<spring:option value="GBR">United Kingdom</spring:option>
																<spring:option value="USA">United States</spring:option>
																<spring:option value="UMI">United States Minor Outlying Islands</spring:option>
																<spring:option value="URY">Uruguay</spring:option>
																<spring:option value="UZB">Uzbekistan</spring:option>
																<spring:option value="VUT">Vanuatu</spring:option>
																<spring:option value="VEN">Venezuela, Bolivarian Republic of</spring:option>
																<spring:option value="VNM">Viet Nam</spring:option>
																<spring:option value="VGB">Virgin Islands, British</spring:option>
																<spring:option value="VIR">Virgin Islands, U.S.</spring:option>
																<spring:option value="WLF">Wallis and Futuna</spring:option>
																<spring:option value="ESH">Western Sahara</spring:option>
																<spring:option value="YEM">Yemen</spring:option>
																<spring:option value="ZMB">Zambia</spring:option>
																<spring:option value="ZWE">Zimbabwe</spring:option>
															</spring:select>
														</div>
													</div>
													<div class="control-group">
														<label class="control-label"><span class="required"></span> Region / State:</label>
														<div class="controls">
															<spring:input path="clientAddress.city" type="text" placeholder="" class="input-xlarge"/>
														</div>
													</div>
													<div class="control-group">
														<label class="control-label"><span class="required"></span> Post Code:</label>
														<div class="controls">
															<spring:input path="clientAddress.postcode" type="text" placeholder="" class="input-xlarge" pattern="^[ 0-9]+$"/>
														</div>
													</div>
													<div class="control-group">
														<label class="control-label"><span class="required"></span> Street:</label>
														<div class="controls">
															<spring:input path="clientAddress.street" type="text" placeholder="" class="input-xlarge"/>
														</div>
													</div>
													<div class="control-group">
														<label class="control-label"><span class="required"></span> House:</label>
														<div class="controls">
															<spring:input path="clientAddress.house" type="text" placeholder="" class="input-xlarge"/>
														</div>
													</div>
													<div class="control-group">
														<label class="control-label"><span class="required"></span> Flat:</label>
														<div class="controls">
															<spring:input path="clientAddress.flat" type="text" placeholder="" class="input-xlarge"/>
														</div>
													</div>
													<div class="control-group">
														<label class="control-label"><span class="required"></span> Additional Information:</label>
														<div class="controls">
															<spring:input path="clientAddress.addition" type="text" placeholder="" class="input-xlarge"/>
														</div>
													</div>
												</fieldset>
												<div class="actions"><input tabindex="9" class="btn btn-inverse large" type="submit" value="Edit your profile"></div>
											</div>
										</div>
											<hr>
										</spring:form>
										<div class="row-fluid">
											<div class="span6">
												<h4>Set New Password</h4>
												<spring:form action="/clients/profile/edit/password" method="post" modelAttribute="passwordField" class="form-stacked">
													<fieldset>
														<div class="control-group">
															<label class="control-label">Old Password*</label>
															<div class="controls">
																<spring:input path="password" type="password" placeholder="" class="input-xlarge" />
															</div>
														</div>
														<hr>
														<div class="control-group">
															<label class="control-label">New Password*</label>
															<div class="controls">
																<spring:input path="newPasswordFirst" type="password" placeholder="" class="input-xlarge" pattern="^[a-zA-Z0-9]+$"/>
															</div>
														</div>
														<div class="control-group">
															<label class="control-label">Repeat New Password*</label>
															<div class="controls">
																<spring:input path="newPasswordSecond" type="password" placeholder="" class="input-xlarge" pattern="^[a-zA-Z0-9]+$"/>
															</div>
														</div>
													</fieldset>
													<div class="actions"><input tabindex="9" class="btn btn-inverse large" type="submit" value="Edit your password"></div>
												</spring:form>
											</div>

										</div>
											<%--<input type="hidden"--%>
												   <%--name="${_csrf.parameterName}"--%>
												   <%--value="${_csrf.token}"/>--%>

									</div>
								</div>
							</div>
							<c:if test="${client.role.name=='ROLE_CLIENT'}">
								<div class="accordion-group">
									<div class="accordion-heading">
										<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">Orders History</a>
									</div>
									<div id="collapseTwo" class="accordion-body collapse">
										<div class="accordion-inner">

												<h4>Your Orders History</h4>
												<c:if test="${clientOrdersList.size()!=0}">
												<table class="table table-striped">
													<thead>
													<tr>
														<th><big>Order ID:</big></th>
														<th><big>Delivery Method</big></th>
														<th><big>Payment Type</big></th>
														<th><big>Pay Status</big></th>
														<th><big>Order Status:</big></th>
														<th><big>Order Details</big></th>
													</tr>
													</thead>
													<c:forEach var="clientOrder"  items="${clientOrdersList}">
													<tbody>
													<tr>
														<td><big>${clientOrder.id}</big></td>
														<td><big>${clientOrder.deliveryMethod.name}</big></td>
														<td><big>${clientOrder.paymentType.name}</big></td>
														<c:if test="${clientOrder.payStatus==1}">
														<td><big>paid</big></td>
														</c:if>
														<c:if test="${clientOrder.payStatus==0}">
														<td><big>pending payment</big></td>
														</c:if>
														<td><big>${clientOrder.status.name}</big></td>
														<td><a href="">Details</a></td>
													</tr>
													</tbody>
														<%--<hr>--%>
														<%--<br>--%>
													</c:forEach>
												</table>
												</c:if>

										</div>
									</div>
								</div>
							</c:if>
							<c:if test="${client.role.name!='ROLE_CLIENT'}">
								<div class="accordion-group">
									<div class="accordion-heading">
										<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseThree">Show All Orders</a>
									</div>
									<div id="collapseThree" class="accordion-body collapse">
										<div class="accordion-inner">

											<h4>All Orders</h4>
											<c:if test="${clientOrdersList.size()!=0}">
												<table class="table table-striped">
													<thead>
													<tr>
														<th><big>Order ID:</big></th>
														<th><big>Client ID:</big></th>
														<th><big>Payment Type:</big></th>
														<th><big>Pay Status:</big></th>
														<th><big>Order Status:</big></th>
														<th><big>Additional Information:</big></th>
														<th><big>Order Details:</big></th>
													</tr>
													</thead>

													<c:forEach var="clientOrder"  items="${clientOrdersList}">
														<tbody>
														<tr>
															<td><big>${clientOrder.id}</big></td>
															<td><big>${clientOrder.client.id}</big></td>
															<td><big>${clientOrder.paymentType.name}</big></td>
															<c:if test="${clientOrder.payStatus==1}">
																<td><big>paid</big></td>
															</c:if>
															<c:if test="${clientOrder.payStatus==0}">
																<td><big>pending payment</big></td>
															</c:if>
															<td><big>${clientOrder.status.name}</big></td>
															<td><big>${clientOrder.comment}</big></td>
															<td><a href="/catalog/profile/employee/details/order/${clientOrder.id}"><big>Details</big></a></td>
														</tr>
														<%--<hr>--%>
														<%--<br>--%>
													</tbody>
													</c:forEach>

												</table>
											</c:if>

										</div>
									</div>
								</div>
							</c:if>
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
							<li><a href="cart.jsp">Your Cart</a></li>
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
    </body>
</html>