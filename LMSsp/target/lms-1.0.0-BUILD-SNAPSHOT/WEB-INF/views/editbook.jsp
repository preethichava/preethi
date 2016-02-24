<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.gcit.lms.domain.Branch"%>
<%@ page import="com.gcit.lms.domain.Book"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%
	Book book = (Book)request.getAttribute("book");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LMS</title>
</head>
<body>

<div class="modal-body">
	<form action="editBook" method="post">
		<input type="text" name="bookId" value="<%=book.getBookId()%>" style="display:none">
		<table>
		<tr>
		<td>Book Title:</td><td> <input type="text" name="bookName" value="<%=book.getTitle()%>"><br/>
		</td></tr><tr><td></td><td><input type="submit" value="Submit"></td>
		</tr>
		</table>
	</form>
</div>

</body>
</html>