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
		function abc(){
			alert(13);
		}
</script>
</head>
<body>
		
		<c:forEach items="${list_sku }" var="sku">
			<div style="float:left;border:1px solid red;width:100px;height:100px">
				<%-- <img  src="images/${sku.spu.shp_tp }">  --%>
				<a href="goto_sku_detail.do?sku_id=${sku.id }&spu_id=${sku.shp_id}" target="_blank">${sku.sku_mch }</a><br>
				${sku.jg }<br>
				${sku.sku_xl }<br>
			</div>
		</c:forEach>
</body>
</html>