<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>회원제 게시판</title>
</head>
<body>

<u:isLogin>
	CT: ${authUser.name}님 안녕하세여.
	<a href="logout.do">[로그아웃하기]</a>
	<a href="changePwd.do">[비밀번호변경]</a>
</u:isLogin>
<u:notLogin>
	CT: <a href="join.do">[회원가입하기]</a>
	<a href="login.do">[로그인하기]</a>
</u:notLogin>
<br/>
</body>
</html>