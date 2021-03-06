<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>index</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/css/bootstrap.min.css"
	type="text/css" />
<script
	src="${pageContext.request.contextPath }/js/jquery-1.11.3.min.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/js/bootstrap.min.js"
	type="text/javascript"></script>
</head>

<body>
	<div class="container-fluid">

		<jsp:include page="/jsp/head.jsp" />

		<!-- 轮播条 -->
		<div class="container-fluid">
			<div id="carousel-example-generic" class="carousel slide"
				data-ride="carousel">
				<!-- Indicators -->
				<ol class="carousel-indicators">
					<li data-target="#carousel-example-generic" data-slide-to="0"
						class="active"></li>
					<li data-target="#carousel-example-generic" data-slide-to="1"></li>
					<li data-target="#carousel-example-generic" data-slide-to="2"></li>
				</ol>

				<!-- Wrapper for slides -->
				<div class="carousel-inner" role="listbox">
					<div class="item active">
						<img
							src="${pageContext.request.contextPath }/img2/banner/lb_1.jpg"
							width="100%">
						<div class="carousel-caption"></div>
					</div>
					<div class="item">
						<img
							src="${pageContext.request.contextPath }/img2/banner/lb_2.jpg"
							width="100%">
						<div class="carousel-caption"></div>
					</div>
					<div class="item">
						<img
							src="${pageContext.request.contextPath }/img2/banner/lb_3.jpg"
							width="100%">
						<div class="carousel-caption"></div>
					</div>
				</div>

				<!-- Controls -->
				<a class="left carousel-control" href="#carousel-example-generic"
					role="button" data-slide="prev"> <span
					class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
					<span class="sr-only">Previous</span>
				</a> <a class="right carousel-control" href="#carousel-example-generic"
					role="button" data-slide="next"> <span
					class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
					<span class="sr-only">Next</span>
				</a>
			</div>
		</div>

		<!-- 商品显示 -->
		<div class="container-fluid">
			<div class="col-md-12">
				<h2>热门商品</h2>
			</div>
			<div class="col-md-2"
				style="border: 1px solid #E7E7E7; border-right: 0; padding: 0;">
				<img src="${pageContext.request.contextPath }/img2/banner/shu.jpg"
					width="205" height="404" style="display: inline-block;" />
			</div>
			<div class="col-md-10">
				<div class="col-md-6"
					style="text-align: center; height: 200px; padding: 0px;">
					<a href="product_info.htm"> <img
						src="${pageContext.request.contextPath }/img2/banner/heng.jpg"
						width="516px" height="200px" style="display: inline-block;">
					</a>
				</div>
				<c:forEach items="${hotList }" var="hl">
					<div class="col-md-2"
						style="text-align: center; height: 200px; padding: 10px 0px;">
						<a
							href="${pageContext.request.contextPath }/product?method=getProById&pid=${hl.pid}">
							<img src="${hl.pimage }" width="130" height="130"
							style="display: inline-block;">
						</a>
						<p>
							<a
								href="${pageContext.request.contextPath }/product?method=getProById&pid=${hl.pid}"
								style='color: #666'>${hl.pname }</a>
						</p>
						<p>
							<font color="#E4393C" style="font-size: 16px">&yen;${hl.shop_price}</font>
						</p>
					</div>
				</c:forEach>
			</div>
		</div>


		<!-- 广告部分 -->
		<div class="container-fluid">
			<img src="${pageContext.request.contextPath }/img2/product/ad.png"
				width="100%" />
		</div>


		<!-- 商品显示 -->
		<c:forEach items="${newList }" var="nl">
			<div class="col-md-2"
				style="text-align: center; height: 200px; padding: 10px 0px;">
				<a
					href="${pageContext.request.contextPath }/product?method=getProById&pid=${nl.pid}">
					<img src="${nl.pimage }" width="130" height="130"
					style="display: inline-block;">
				</a>
				<p>
					<a
						href="${pageContext.request.contextPath }/product?method=getProById&pid=${nl.pid}"
						style='color: #666'>${nl.pname }</a>
				</p>
				<p>
					<font color="#E4393C" style="font-size: 16px">&yen;${nl.shop_price }</font>
				</p>
			</div>
		</c:forEach>



		<!-- 页脚部分 -->
		<div class="container-fluid">
			<div class="container-fluid">
				<img src="${pageContext.request.contextPath }/img2/product/ad2.png"
					width="100%" />
			</div>

			<div style="text-align: center; margin-top: 5px;">
				<ul class="list-inline">
					<li><a href="info.html">关于我们</a></li>
					<li><a>联系我们</a></li>
					<li><a>招贤纳士</a></li>
					<li><a>法律声明</a></li>
					<li><a>友情链接</a></li>
					<li><a>支付方式</a></li>
					<li><a>配送方式</a></li>
					<li><a>服务声明</a></li>
					<li><a>广告声明</a></li>
				</ul>
			</div>
			<div
				style="text-align: center; margin-top: 5px; margin-bottom: 20px;">
				Copyright &copy; 2013-2017 怀致商城 版权所有</div>
		</div>
	</div>
</body>
</html>