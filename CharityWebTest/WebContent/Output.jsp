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

Charity output = (Charity)request.getAttribute("styles"); 
out.println("<br>"+"Organization: "+output.getName());
out.println("<br>"+"Business Number: "+output.getBnum());
out.println("<br>"+"Decription:"+"<br>"+output.getDesc());

%> 
<br>

<form method="POST" action="ControllerCharity.do">       
	What would you like to view?:<p>   
	1. Basic Data <br>
	2. Operating Data <br>
	3. Financial Data <br>
	4. Miscellaneous Data <br>   
	Commands:       
	<select name="input" size="1">        
 		<option>1</option>         
 		<option>2</option>      
 		<option>3</option>     
 		<option>4</option>            
 	</select>       
<input type = hidden name = "object" value = "<% out.print(output.getName()); %>"> 
 	<br>       
 	<br>       
 	<center>         
 		<input type="SUBMIT">      
  	</center>     

 </form>

<br>
<br>
<br>
<br>
<a href="Home.html">Return to home page</a>
</body> 
</html> 