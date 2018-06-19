<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table width="100%">
	<tr>
		<td>商品图片</td>
		<td>商品名字</td>
		<td>商品数量</td>
		<td>商品小计</td>
	</tr>
	<c:forEach items="${oList }" var="oi">
		<tr>
		<td><img width="40" height="45" src="${ pageContext.request.contextPath }/${oi.product.pimage }"></td>
		<td>${oi.product.pname }</td>
		<td>${oi.count }</td>
		<td>${oi.subtotal }</td>
		</tr>
	</c:forEach>
	
</table>