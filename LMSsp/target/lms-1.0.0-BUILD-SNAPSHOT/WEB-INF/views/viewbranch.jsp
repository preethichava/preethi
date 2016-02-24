<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.gcit.lms.domain.Branch" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%
	List<Branch> branch = (List<Branch>)request.getAttribute("branches");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
${message}
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GCIT LMS</title>

</head>
<body>
<h2> View Existing Publishers</h2>
<table>
	<tr>
	
	<th>Branch Name</th>
	<th>Branch Address</th>
	<th>Edit Branch</th>
	<th>Delete Branch</th>
	</tr>
	<%for(Branch b: branch){ %>
		<tr>
			
			<td align="center" ><input type="text"  id="cardNo" name="cardNo" value="<%=b.getBranchId()%>" style="display:none">
			<%=b.getBranchName()%></td>
			<td align="center"><%=b.getBranchAddress() %></td>
			
			<td align="center"><button type="button" onclick="javascript:location.href='updatebranch?branchId=<%=b.getBranchId() %>'">EDIT</button></td>
			<td align="center"><button type="button" onclick="javascript:location.href='deleteBranch?branchId=<%=b.getBranchId()%>'">DELETE</button></td>
			
			</tr>
	<%} %>
	
</table>
<br/>
<a href="branchdetails">Previous Page</a><br/>
</body>
</html>