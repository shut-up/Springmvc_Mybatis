<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>json测试</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript">
	//请求json，输出json
	function requestJson(){
		$.ajax({
			type:'post',
			url:'${pageContext.request.contextPath }/requestJson.action',
			contentType:'application/json;charset=utf-8',
			//数据格式是json串
			data:'{"name":"手机","price":999}',
			success:function(data){
				alert(data);
			}
		});
	}
	//请求key/value，输出json
	function responseJson(){
	  $.ajax({
			type:'post',//这里改为get也可以正常执行
			url:'${pageContext.request.contextPath}/responseJson.action',
			//ContentType没指定将默认为：application/x-www-form-urlencoded(key/value类型)
			data:'name=测试商品&price=99.9',
			success:function(data){
				alert(data.name);
			}
		}	
	);
}
	</script>
	
  </head>
  
  <body>
    <input type="button" onclick="requestJson()" value="请求json，输出json"/>
    <input type="button" onclick="responseJson()" value="请求key/value，输出json"/>
  </body>
</html>
