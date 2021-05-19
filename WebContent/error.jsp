<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Error on operations</title>
</head>
<body>
<%
	String errorType = (String)request.getAttribute("TypeError");
	if(errorType==null)
	{
		out.println("<h3 style='color:red'>Attenzione, questa pagina è designata a mostrarti possibili errori</h3>");
	}
	else if(errorType.equals("noResults"))
	{
		out.println("<h3 style='color:red'>Attenzione, hai visitato la pagina errata</h3>");
		
	}
	else
	{
		out.println("<h3 style='color:red'>"+ request.getAttribute("TypeError") + "</h3>");
		out.println(request.getAttribute("explaination"));
	}
%>
</body>
</html>