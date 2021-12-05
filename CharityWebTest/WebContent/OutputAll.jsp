<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.*" %> 
<%@ page import="model.*" %> 
<html> 
<body> 
<% java.util.Date d = new java.util.Date(); %>
<h1 align="center">CharityLooker Output</h1> 

<h2>
Today's date is <%= d.toString() %>. Welcome to CharityLooker.
</h2>
<p> 
<%  
Charity output[] = (Charity[])request.getAttribute("styles"); 
for(int i =0; i < output.length; i++)
{
	out.print(i+".  "+output[i].getName()+"  |  "+output[i].getBnum());
	out.print("<br>");
}

%> 
<br>
<br>
<br>
<br>
<br>
<a href="Home.html">Return to home page</a>
</body> 
</html> 