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
        ${flbh2 }
		<hr>
		<jsp:include page="attrList.jsp"></jsp:include>
		<hr>
		<div id="skuListInner">
		    <jsp:include page="skuList.jsp"></jsp:include>
		</div>
</body>
</html>