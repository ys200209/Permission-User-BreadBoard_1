<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import = "jdbc.connection.ConnectionProvider" %>
<%@ page import = "java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Connection Test</title>
</head>
<body>
<%
	try (Connection conn = ConnectionProvider.getConnection()) {
		out.println("커넥션 연결 성공");
	} catch (SQLException e) {
		out.println("커넥션 연결 실패 : " + e.getMessage());
		application.log("커넥션 연결 실패", e);
	}
	
	
%>
</body>
</html>