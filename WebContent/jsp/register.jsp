<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
	<head></head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>会员登录</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>

<style>
  body{
   margin-top:20px;
   margin:0 auto;
 }
 .carousel-inner .item img{
	 width:100%;
	 height:300px;
 }
 .container .row div{ 
	 /* position:relative;
	 float:left; */
 }
 
font {
    color: #3164af;
    font-size: 18px;
    font-weight: normal;
    padding: 0 10px;
}
 </style>
 <script type="text/javascript">
 
 </script>
</head>
<body>
	<jsp:include page="/jsp/head.jsp"></jsp:include>



	<div class="container" style="width:100%;background:url('${pageContext.request.contextPath}/img2/banner/register_bg.jpg');">

		<div class="row"> 
			<div class="col-md-2"></div>

			<!-- 设置半透明 -->
			<!-- <div class="col-md-8" style="background:#fff;padding:40px 80px;margin:30px;border:7px solid #ccc; filter:alpha(Opacity=80);-moz-opacity:0.5;opacity: 0.5;">
			 -->	
				<font>会员注册</font>USER REGISTER
				<font>${msg }</font>
				<form class="form-horizontal" style="margin-top:5px;" action="${pageContext.request.contextPath }/user?method=register" method="post">
				 	
				 	<div class="form-group">
					    <label for="username" class="col-sm-2 control-label">用户名</label>
					    <div class="col-sm-6">
					    	<input type="text" class="form-control" id="username" name="username" placeholder="请输入用户名" value="xuelian" >
					    	<span id="usernameId" ></span>
					    </div>
				  	</div>
				  	
				   	<div class="form-group">
					    <label for="inputPassword3" class="col-sm-2 control-label">密码</label>
					    <div class="col-sm-6">
					      	<input type="password" class="form-control" name="password" id="inputPassword3" placeholder="请输入密码" value="123">
					    </div>
				  	</div>
				  	
				   	<div class="form-group">
				    	<label for="confirmpwd" class="col-sm-2 control-label">确认密码</label>
				    	<div class="col-sm-6">
				      		<input type="password" class="form-control" id="confirmpwd" name="repassword" placeholder="请输入确认密码" value="123">
				    	</div>
				  	</div>
				  	
				  	<div class="form-group">
				    	<label for="inputEmail3" class="col-sm-2 control-label">Email</label>
				    	<div class="col-sm-6">
				      		<input type="email" class="form-control" id="inputEmail3" name="email" placeholder="Email" value="xuelian@jss.com">
				    	</div>
				  	</div>
				  	
				 	<div class="form-group">
				    	<label for="usercaption" class="col-sm-2 control-label">姓名</label>
				    	<div class="col-sm-6">
				      		<input type="text" class="form-control" id="usercaption" name="name" placeholder="请输入姓名" value="xuexue">
				    	</div>
				  	</div>
				  	
				  	<div class="form-group opt">  
				  		<label for="inlineRadio1" class="col-sm-2 control-label">性别</label>  
				  		<div class="col-sm-6">
				    		<label class="radio-inline">
				  				<input type="radio" name="sex" id="inlineRadio1" value="option1" checked="checked"> 男
							</label>
							<label class="radio-inline">
				  				<input type="radio" name="sex" id="inlineRadio2" value="option2"> 女
							</label>
						</div>
				  	</div>
				  
				  	<div class="form-group">
				    	<label for="date" class="col-sm-2 control-label">出生日期</label>
				    	<div class="col-sm-6">
				      		<input type="date" class="form-control" name="birthday" >		      
				    	</div>
				  	</div>
				  
				  	<div class="form-group">
				    	<label for="date" class="col-sm-2 control-label">验证码</label>
				    	<div class="col-sm-3">
				      		<input type="text" class="form-control" name="code" value="1234" >
				    	</div>
				    	<div class="col-sm-2">
				    		<img src="${pageContext.request.contextPath }/code"/>
				    	</div>
				  	</div>
				  	
				 
				  	<div class="form-group">
				    	<div class="col-sm-offset-2 col-sm-10">
				      		<input type="submit"  width="100" value="注册" name="submit" border="0"
					    style="background: url('${pageContext.request.contextPath}/img2/other/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
					    height:35px;width:100px;color:white;">
				    	</div>
				  </div>
				  
				</form>
			</div>
	
			<div class="col-md-2"></div>
  
		</div>
	</div>

	  
	
	<!-- 页脚部分 -->
	<div class="container-fluid">
		<div class="container-fluid">
			<img src="${pageContext.request.contextPath}/img2/product/ad2.png" width="100%"/>
		</div>

		<div style="text-align: center;margin-top: 5px;">
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
		<div style="text-align: center;margin-top: 5px;margin-bottom:20px;">
			Copyright &copy; 2013-2017 怀致商城 版权所有
		</div>
</body></html>




