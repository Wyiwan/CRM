<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.11.3.min.js"></script>
	
<script type="text/javascript">

	// 页面加载函数
	$(function(){
		
		// 发送请求，去服务端获取数据
		$.get("${pageContext.request.contextPath}/category?method=findAll", function(data){
			// alert(data);
			
			// 动态添加 ul 列表的 li 项，然后显示对应的数据
			var $ul = $("#menuId");
			
			// 遍历
			$(data).each(function(){
				// 动态添加 li 标签
				$ul.append($("<li><a href='${pageContext.request.contextPath}/product?method=findProByPage&cid="+ this.cid +"&currPage=1'>" + this.cname + "</a></li>"));
			});
		},"json");
		
	})

</script>

</head>
<body>
	<!-- 菜单栏  -->
	<div class="container-fluid">
		<div class="col-md-4">
			<img src="${pageContext.request.contextPath }/img2/logo/logo.png" />
		</div>
		<div class="col-md-5">
			<img src="${pageContext.request.contextPath }/img2/logo/header.png" />
		</div>
		<div class="col-md-3" style="padding-top: 20px">
			<ol class="list-inline">
				<c:if test="${empty user }">
					<li><a
						href="${pageContext.request.contextPath }/user?method=loginUI">登录</a></li>
					<li><a
						href="${pageContext.request.contextPath }/user?method=registerUI">注册</a></li>
				</c:if>
				<c:if test="${not empty user }">
						${user.name }，欢迎回来！
						<li><a
						href="${pageContext.request.contextPath }/user?method=logout">退出登录</a></li>
					<li><a href="${pageContext.request.contextPath }/">我的购物车</a></li>
					<li><a href="${pageContext.request.contextPath }/order?method=findAllByPage&currPage=1">我的订单</a></li>
				</c:if>
			</ol>
		</div>
	</div>

	<!-- 导航条 -->
	<div class="container-fluid">
		<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
					aria-expanded="false">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">首页</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav" id="menuId">
					<%--<c:forEach items="${clist}" var="c">
						<li><a href="#">${c.cname}</a></li>
					</c:forEach>--%>
				</ul>
				<form class="navbar-form navbar-right" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="请输入关键字">
					</div>
					<button type="submit" class="btn btn-default">搜索一下</button>
				</form>

			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid --> </nav>
	</div>
</body>
</html>