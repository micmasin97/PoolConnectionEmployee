package it.advancia.michele.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.advancia.michele.entity.Employee;

@WebServlet("/OperationServlet")
public class OperationServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		DataSource dataSource = null;
		try
		{
			InitialContext initialContext = new InitialContext();
			Context envContext = (Context) initialContext.lookup("java:comp/env");
			dataSource = (DataSource) envContext.lookup("jdbc/jpaddsb");
			List<Employee> employees = operations(request, dataSource);
			request.setAttribute("list", employees);
			request.getRequestDispatcher("show.jsp").forward(request, response);
		} catch (Exception e)
		{
			request.setAttribute("TypeError", e.getClass().getSimpleName());
			request.setAttribute("explaination", e.getMessage());
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
	
	private List<Employee> operations(HttpServletRequest request, DataSource dataSource) throws NamingException, SQLException
	{
		String operations = request.getParameter("submit");
		List<Employee> employees = null;
		try
		{
			if (operations.equals("search"))
			{
				employees = search(dataSource, request);
			} else
			{
				if (operations.equals("update"))
				{
					update(dataSource, request);
				} else if (operations.equals("create"))
				{
					create(dataSource, request);
				} else
				{
					delete(dataSource, request);
				}
				employees = getEmployee(dataSource);
			}
			return employees;
		} catch (Exception e)
		{
			throw e;
		}
	}

	private List<Employee> getEmployee(DataSource dataSource) throws NamingException, SQLException
	{
		Connection connection = null;
		Statement statement = null;
		List<Employee> employeesList = new ArrayList<Employee>();
		try
		{
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			String query = "SELECT EID, DEG, ENAME, SALARY from employee";
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next())
			{
				Employee employee = new Employee();
				employee.setEid(resultSet.getInt("EID"));
				employee.setDeg(resultSet.getString("DEG"));
				employee.setEname(resultSet.getString("ENAME"));
				employee.setSalary(Double.parseDouble(resultSet.getString("SALARY")));
				employeesList.add(employee);
			}
			statement.close();
			connection.close();
		} catch (Exception e)
		{
			statement.close();
			connection.close();
		}
		return employeesList;
	}

	private List<Employee> search(DataSource dataSource, HttpServletRequest request) throws NamingException, SQLException
	{
		Connection connection = null;
		PreparedStatement statement = null;
		List<Employee> employeesList = new ArrayList<Employee>();
		try
		{
			connection = dataSource.getConnection();
			statement = connection.prepareStatement("SELECT EID, DEG, ENAME, SALARY from employee where EID=?");
			statement.setInt(1, Integer.parseInt((String) request.getParameter("idSearch")));
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				Employee employee = new Employee();
				employee.setEid(resultSet.getInt("EID"));
				employee.setDeg(resultSet.getString("DEG"));
				employee.setEname(resultSet.getString("ENAME"));
				employee.setSalary(Double.parseDouble(resultSet.getString("SALARY")));
				employeesList.add(employee);
			}
			statement.close();
			connection.close();
		} catch (Exception e)
		{
			statement.close();
			connection.close();
		}
		return employeesList;
	}

	private void update(DataSource dataSource, HttpServletRequest request) throws SQLException
	{
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = dataSource.getConnection();
			statement = connection.prepareStatement("SELECT EID, DEG, ENAME, SALARY from employee where EID=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			statement.setInt(1, Integer.parseInt(request.getParameter("idUpdate")));
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				resultSet.updateString("DEG", (String) request.getParameter("DegUpdate"));
				resultSet.updateString("ENAME", (String) request.getParameter("NameUpdate"));
				resultSet.updateInt("Salary", Integer.parseInt(request.getParameter("SalaryUpdate")));
				resultSet.updateRow();
			}
			statement.close();
			connection.close();
		} catch (Exception e)
		{
			statement.close();
			connection.close();
			throw e;
		}
	}

	private void create(DataSource dataSource, HttpServletRequest request) throws SQLException
	{
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = dataSource.getConnection();
			statement = connection.prepareStatement("Insert into employee values(?,?,?,?)");
			statement.setInt(1, Integer.parseInt(request.getParameter("idCreate")));
			statement.setString(2, request.getParameter("DegCreate"));
			statement.setString(3, request.getParameter("NameCreate"));
			statement.setInt(4, Integer.parseInt(request.getParameter("SalaryCreate")));
			statement.executeUpdate();
			statement.close();
			connection.close();
		} catch (Exception e)
		{
			e.printStackTrace();
			statement.close();
			connection.close();
		}
	}

	private void delete(DataSource dataSource, HttpServletRequest request) throws NamingException, SQLException
	{
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = dataSource.getConnection();
			statement = connection.prepareStatement("Delete from employee where eid=?");
			statement.setInt(1, Integer.parseInt(request.getParameter("idDelete")));
			statement.executeUpdate();
			statement.close();
			connection.close();
		} catch (Exception e)
		{
			e.printStackTrace();
			statement.close();
			connection.close();
		}
	}


}
