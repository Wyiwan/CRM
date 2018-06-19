<%@page import="com.yoiit.utils.CookUtils, com.yoiit.service.*,com.yoiit.service.impl.*, com.yoiit.domain.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
				width: 100%;
			}
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
		</style>
	</head>

	<body>
		
		<jsp:include page="/jsp/head.jsp"></jsp:include>


		<div class="row" style="width:1210px;margin:0 auto;">
			<div class="col-md-12">
				<ol class="breadcrumb">
					<li><a href="#">首页</a></li>
				</ol>
			</div>
			
			<c:forEach items="${pb.list }" var="pl">
				<div class="col-md-2">
					<a href="${pageContext.request.contextPath }/product?method=getProById&pid=${pl.pid}">
						<img src="${pageContext.request.contextPath}/${pl.pimage}" width="170" height="170" style="display: inline-block;">
					</a>
					<p align="center">
						<a href="${pageContext.request.contextPath }/product?method=getProById&pid=${pl.pid}" style='color:green'>${pl.pname }</a>
					</p>
					<p align="center">
						<font color="#FF0000">商城价：&yen;${pl.shop_price }</font>
					</p>
				</div>
			</c:forEach>

		</div>

		<!--分页 -->
		<div style="width:380px;margin:0 auto;margin-top:50px;">
			<ul class="pagination" style="text-align:center; margin-top:10px;">
				<c:if test="${pb.currPage!=1 }">
					<a href="${pageContext.request.contextPath }/product?method=findProByPage&cid=${param.cid}&currPage=1">【首页】</a>
					<a href="${pageContext.request.contextPath }/product?method=findProByPage&cid=${param.cid}&currPage=${pb.currPage-1}">【上一页】</a>
					
				</c:if>
				<c:forEach begin="1" end="${pb.totalPage }" var="n">
					
					<c:if test="${pb.currPage==n }">
						${n }
					</c:if>
					<c:if test="${pb.currPage!=n }">
						<a href="${pageContext.request.contextPath }/product?method=findProByPage&cid=${param.cid}&currPage=${n}">${n }</a>
					</c:if>
					
				</c:forEach>
				<c:if test="${pb.currPage!=totalPage }">
					<a href="${pageContext.request.contextPath }/product?method=findProByPage&cid=${param.cid}&currPage=${pb.currPage+1}">【下一页】</a>
					
					<a href="${pageContext.request.contextPath }/product?method=findProByPage&cid=${param.cid}&currPage=${pb.totalPage}">【尾页】</a>
				</c:if>
				第${pb.currPage }页/共${pb.totalPage }页

			</ul>
		</div>
		<!-- 分页结束=======================        -->

		<!--
       		商品浏览记录:
        -->
		<div style="width:1210px;margin:0 auto; padding: 0 9px;border: 1px solid #ddd;border-top: 2px solid #999;height: 246px;">

			<h4 style="width: 50%;float: left;font: 14px/30px " 微软雅黑 ";">浏览记录</h4>
			
			<div style="width: 50%;float: right;text-align: right;">
				<%-- 此处的 param 就等于 request.getParameter() 方法 --%>
				<a href="${pageContext.request.contextPath }/product?method=clear&cid=${param.cid}&currpage=${pb.currPage}">清除浏览记录</a>
			</div>
			
			<div style="clear: both;"></div>

			<div style="overflow: hidden;">

				<ul style="list-style: none;">
				<%
					// 通过 CookieUtils 获取指定的 cookie
					Cookie c = CookUtils.getCookieByName("ids", request.getCookies());
				
					if(c == null){
				%>
						<h2>现在还没有浏览记录，快去选购吧！</h2>
				<%
					}else {
						// 剔除 cookie 中的 - 分隔符
						String[] arr = c.getValue().split("-"); // 1-2-3-4-5
						
						// 循环遍历
						for(String pid : arr){
							
							ProductService ps = new ProductServiceImpl();
							Product pro = null;
							
							try {
								// 通过根据 id 获取对应的商品
								pro = ps.getProById(pid);
								
							} catch (Exception e) {
								e.printStackTrace();
							}
							
							request.setAttribute("pro", pro);
				%>
						<li style="width: 150px;height: 216;float: left;margin: 0 8px 0 0;padding: 0 18px 15px;text-align: center;">
							<img src="${pageContext.request.contextPath}/${pro.pimage }" width="130px" height="130px" />
						</li>
				<%
						}
					}
				%>
					
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