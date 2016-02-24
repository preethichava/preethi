<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    
<%@ page import="com.gcit.lms.domain.Book"%>
<%@ page import="com.gcit.lms.service.AdministratorService"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%  
	List<Book> books = (List<Book>)request.getAttribute("books");
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GCIT LMS</title>

<script>
function validateForm(a)
{
}
</script>
</head>
<body>
${message}

<table>
	<tr>
	<th>Book Title</th>
	<th>Edit title</th>
	<th>Delete book</th>
	</tr>
	<%for(Book b: books){ %>
		<tr>
			<td align="center"><input type="text"  id="bookId" name="bookId" value="<%=b.getBookId() %>" style="display:none"><%=b.getTitle() %></td>
			<td align="center"><button type="button" onclick="javascript:location.href='editbook?bookId=<%=b.getBookId()%>'">EDIT</button></td>
			<td align="center"><button type="button" onclick="javascript:location.href='deleteBook?bookId=<%=b.getBookId()%>'">DELETE</button></td>
			</tr>
	<%} %>
	
</table>
<br/>
<a href="book.jsp">Previous Page</a><br/>
</body>
</html>