<%@page import="com.gcit.lms.domain.Borrower"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.gcit.lms.domain.Branch"%>
<%@ page import="com.gcit.lms.domain.Borrower"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%
    Borrower br = (Borrower)request.getAttribute("borrower");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GCIT LMS</title>
</head>
<body>

<h1> Welcome <%=br.getName() %> !!</h1>
<p>
<form action="bookLoan" method="post">
	<input type="hidden" name="cardNum" value="<%=br.getCardNo() %>" >
	<input type="radio" name="loan" value="checkout"> Check out Books<br>
  	<input type="radio" name="loan" value="checkin"> Return Books<br>
	</p>
    <input type="submit" value="Submit" />
</form>
</body>
</html>