<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.gcit.lms.domain.Loans"%>
<%@ page import="com.gcit.lms.domain.Branch"%>
<%@ page import="com.gcit.lms.domain.Borrower"%>
<%@ page import="com.gcit.lms.service.AdministratorService"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%
	Borrower br = (Borrower)request.getAttribute("borrower");
	List<Branch> branches = (List<Branch>)request.getAttribute("branches");
	String selected = (String)request.getAttribute("selectedOption");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GCIT LMS</title>
<script>
function myFunction() {
    var branch = document.getElementById("branchId").value;
	if(branch == "select"){
		return false;
	}
}
</script>
</head>
<body>
${message}


<form action="selectBranch"  onsubmit="myFunction()" method="post">
<input type="hidden" name="cardNum" value="<%=br.getCardNo() %>" >
<input type="hidden" name="selectedOpt" value="<%=selected %>" >
Select Branch:
<select id="branchId" name="branchId" >
<option value="select"> ----Select----</option>
<%for(Branch a: branches){ %>
	<option value="<%=a.getBranchId() %>" ><%=a.getBranchName() %></option>	
<%} %>
</select>
<input type="submit" value="Select" />
</form>
<a href="bindex">Previous Page</a><br/>
</body>
</html>