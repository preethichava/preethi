<%@page import="com.gcit.lms.service.LibrarianService"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.gcit.lms.domain.Branch" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%
	Branch branch = (Branch)request.getAttribute("branch");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GCIT LMS</title>

</head>
<body>

${message}
<h1>Branch Management</h1>
<h2>You have chosen to manage Branch: <%=branch.getBranchName() %> </h2>

<p>
<a href="updatebranch?branchId=<%=branch.getBranchId()%>">Update Branch Details</a><br/>
<a href="updatecopies?branchId=<%=branch.getBranchId()%>">Update Book Copies</a><br/>
<a href="librarian">Previous page</a><br/>
</p>
</body>
</html>