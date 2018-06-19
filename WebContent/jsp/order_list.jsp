<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>会员登录</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />

		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
			}
			
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
		</style>
	</head>

	<body>

			<!--菜单栏-->
			<jsp:include page="/jsp/head.jsp"></jsp:include>

		

		<div class="container">
			<div class="row">

				<div style="margin:0 auto; margin-top:10px;width:950px;">
					<strong>我的订单</strong>
					<table class="table table-bordered">
						<c:forEach items="${pb.list }" var="o">
						<tbody>
							<tr class="success">
								<th colspan="5">订单编号:${o.oid } 订单总额：${o.total }
									<c:if test="${o.state == 0 }">
										<a href="${pageContext.request.contextPath }/order?method=getOrderByOid&oid=${o.oid}">
											付款
										</a>
									</c:if>
									<c:if test="${o.state == 1 }">已付款</c:if>
									<c:if test="${o.state == 2 }"><a href="#">确认收获</a></c:if>
									<c:if test="${o.state == 3 }">订单已完成</c:if></th>
							</tr>
							<tr class="warning">
								<th>图片</th>
								<th>商品</th>
								<th>价格</th>
								<th>数量</th>
								<th>小计</th>
							</tr>
							<c:forEach items="${o.list }" var="oi">
								<tr class="active">
									<td width="60" width="40%">
										<input type="hidden" name="id" value="22">
										<img src="${pageContext.request.contextPath}/${oi.product.pimage}" width="70" height="60">
									</td>
									<td width="30%">
										<a target="_blank"> ${oi.product.pname}</a>
									</td>
									<td width="20%">
										￥${oi.product.shop_price}
									</td>
									<td width="10%">
										${oi.count}
									</td>
									<td width="15%">
										<span class="subtotal">￥${oi.subtotal}</span>
									</td>
								</tr>
							</c:forEach>
						</tbody>
						</c:forEach>
						
					</table>
				</div>
			</div>
			<div style="text-align: center;">
				<ul class="pagination">
					<c:if test="${pb.currPage!=1 }">
						<a href="${pageContext.request.contextPath }/order?method=findAllByPage&currPage=1">【首页】</a>
						<a href="${pageContext.request.contextPath }/order?method=findAllByPage&currPage=${pb.currPage-1}">【上一页】</a>
					</c:if>
					<c:forEach begin="1" end="${pb.totalPage }" var="n">
						
						<c:if test="${pb.currPage==n }">
							${n }
						</c:if>
						<c:if test="${pb.currPage!=n }">
							<a href="${pageContext.request.contextPath }/order?method=findAllByPage&currPage=${n}">${n }</a>
						</c:if>
					</c:forEach>
					<c:if test="${pb.currPage!=pb.totalPage }">
						<a href="${pageContext.request.contextPath }/order?method=findAllByPage&currPage=${pb.currPage+1}">【下一页】</a>
						<a href="${pageContext.request.contextPath }/order?method=findAllByPage&currPage=${pb.totalPage}">【尾页】</a>
					</c:if>
				</ul>
			</div>
		</div>

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
		<div style="text-align: center; margin-top: 5px; margin-bottom: 20px;">
			Copyright &copy; 2013-2017 怀致商城 版权所有</div>
	</div>
	</body>

</html>