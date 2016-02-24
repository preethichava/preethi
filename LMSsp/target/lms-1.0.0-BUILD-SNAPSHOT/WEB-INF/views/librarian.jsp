<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ page import="com.gcit.lms.domain.Branch"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%
	List<Branch> branches = new ArrayList<Branch>();
	branches =  (List<Branch>)request.getAttribute("branches");
	
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
<h2>Select Branch: </h2>

<table>
	<tr>
	<th>Branch Name</th>
	<th>select Branch</th>
	</tr>
	<%for(Branch a: branches){ %>
		<tr>
			<td align="center"><%=a.getBranchName() %></td>
			<td align="center"><a href="branch?branchId=<%=a.getBranchId()%>">SELECT</a></td>
			</tr>
	<%} %>
	<tr>
	<td colspan="3" align="center"><a href="index.html">Previous page</a>
	</td>
	</tr>
	
</table>
</body>
</html>