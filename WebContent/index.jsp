<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Start connPool</title>
</head>
<body>
	<h3>Show all employees</h3>
	<form action="OperationServlet" method="get">
		<input type="submit" name="submit" value="show"/>
	</form>
	
	
	<h3>Search employee by id</h3>
	<form action="OperationServlet" method="get">
		<input type="number" name="idSearch" placeholder="id impiegato"/>
		<input type="submit" name="submit" value="search"/>
	</form>
	
	
	<h3>New employee</h3>
	<form action="OperationServlet" method="get">
		<input type="number" name="idCreate" placeholder="id impiegato"/>
		<input type="text" name="DegCreate" placeholder="Deg"/>
		<input type="text" name="NameCreate" placeholder="Nome impiegato"/>
		<input type="number" name="SalaryCreate" placeholder="Salario"/>
		<input type="submit" name="submit" value="create"/>
	</form>
	
	
	<h3>Update employee</h3>
	<form action="OperationServlet" method="get">
		<input type="number" name="idUpdate" placeholder="id impiegato"/>
		<input type="text" name="DegUpdate" placeholder="Deg"/>
		<input type="text" name="NameUpdate" placeholder="Nome impiegato"/>
		<input type="number" name="SalaryUpdate" placeholder="Salario"/>
		<input type="submit" name="submit" value="update"/>
	</form>
	
	
	<h3>Delete employee by id</h3>
	<form action="OperationServlet" method="get">
		<input type="number" name="idDelete" placeholder="id impiegato"/>
		<input type="submit" name="submit" value="delete"/>
	</form>
</body>
</html>