package com.revature.project1.servlet.controler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.revature.project1.dao.DAO;
import com.revature.project1.dao.UserOracle;
import com.revature.project1.user.Employee;
import com.revature.project1.user.Manager;
import com.revature.project1.user.User;
import com.revature.project1.util.exception.IncorrectLoginException;
import com.revature.project1.util.exception.NoAccessException;

public class UserController {

	private static final Gson JSONParser = new Gson();
	private static final DAO<User> dao = new  UserOracle(); 
	
	public UserController() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public static void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		User ret;
		PrintWriter out = response.getWriter();
		try {
			ret = User.login(email, password);
			String json = JSONParser.toJson(ret);
			
			HttpSession session = request.getSession(true);
			session.setAttribute("USER", ret);
			session.setMaxInactiveInterval(30 * 60);
	        
			out.write(json.toString());
	        out.flush();
		} catch (IncorrectLoginException e) {
			out.write(e.getMessage());
			out.flush();
		} catch (NoAccessException e) {
			out.write(e.getMessage());
			out.flush();
		}
	}
	
	public static void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			
		} else {
			session.invalidate();
		}
	}
	
	public static void updateInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.getWriter().write("You must be logged in to use this function");
			response.getWriter().flush();
		} else {
			String fName = request.getParameter("fname");
			String lName = request.getParameter("lname");
			Employee old_emp = (Employee) session.getAttribute("USER");
			if (fName != "" && fName != null) {
				old_emp.setFName(fName);
			}
			if (lName != "" && lName != null) {
				old_emp.setLName(lName);
			}
			try {
				dao.insertInto(old_emp);
			} catch (Exception e) {
				response.getWriter().write(e.getMessage());
				response.getWriter().flush();
			}
			response.getWriter().write("Information updated sucessfully.");
			response.getWriter().flush();
		}
	}
	
	public static void getEmployees(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.getWriter().write("You must be logged in to continue");
			response.getWriter().flush();
			return;
		}
		Manager emp;
		try {
			emp = (Manager) session.getAttribute("USER");
		} catch (Exception e){
			response.getWriter().write("You must be an Manager to perform this action");
			response.getWriter().flush();
			return;
		}
		ArrayList<Employee> list; 
		try {
			list = emp.getAllEmployees();
		} catch (Exception e) {
			response.getWriter().write("There was trouble in reaching the employees");
			response.getWriter().flush();
			return;
		}
		String json = JSONParser.toJson(list);
		response.getWriter().write(json);
		response.getWriter().flush();
	}
}
