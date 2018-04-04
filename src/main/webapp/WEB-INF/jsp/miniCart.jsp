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
		function show_cart(){
			$.post("miniCart.do",function(data){
				$("#cart_list").html(data);
			});
			$("#cart_list").show();
		}
		
		function hide_cart(){
			$("#cart_list").hide();
		}
</script>
</head>
<body>
		<div class="card">
			<a href="goto_list_cart.do" onmouseover="show_cart()" onmouseout="hide_cart()">购物车<div class="num">0</div></a>
			
			<!--购物车商品-->
			<div id="cart_list" class="cart_pro" style="display:none">
				<jsp:include page="miniCartList.jsp"></jsp:include>
			</div>
		
		</div>
</body>
</html>