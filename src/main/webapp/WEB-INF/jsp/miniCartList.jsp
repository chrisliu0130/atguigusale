<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";%>
<base href="<%=basePath %>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
		function f(){

		}
</script>
</head>
<body>
				<h6>最新加入的商品</h6>
				<c:forEach items="${list_cart }" var="cart">
					<div class="one">
						<img src="upload/image/${cart.shp_tp }"/>
						<span class="one_name">
							${cart.sku_mch }
						</span>
						<span class="one_prece">
							<b>￥${cart.sku_jg }</b><br />
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;删除
						</span>
					</div>
				</c:forEach>
				<div class="gobottom">
					共<span>0</span>件商品&nbsp;&nbsp;&nbsp;&nbsp;
					共计￥<span>20000</span>
					<button class="goprice">去购物车</button>
				</div>
</body>
</html>