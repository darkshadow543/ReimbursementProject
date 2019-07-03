package com.revature.project1.servlet.controler;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.revature.project1.dao.DAO;
import com.revature.project1.dao.RequestOracle;
import com.revature.project1.request.Request;
import com.revature.project1.request.Status;
import com.revature.project1.user.Manager;
import com.revature.project1.user.User;
import com.revature.project1.util.exception.NoAccessException;
import com.revature.project1.validator.RequestValidator;

public class RequestController {

	private static final DAO<Request> dao = new RequestOracle();
	private static final Gson JsonParser = new Gson();
	public RequestController() {
		// TODO Auto-generated constructor stub
	}
	
	public static void makeRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int emp_id = Integer.parseInt(request.getParameter("id"));
		int ammount = Integer.parseInt(request.getParameter("ammount"));
		String reason = request.getParameter("reason");
		Request req = new Request(0, emp_id, null, ammount, reason, Status.PENDING);
		try {
			RequestValidator.validateRequest(req);
			dao.insertInto(req);
			response.getWriter().write("Request successfully submited");
			response.getWriter().flush();
		} catch (Exception e) {
			response.getWriter().write(e.getMessage());
			response.getWriter().flush();
		}
		
	}
	
	public static void getPending(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.getWriter().write("You must be logged in to continue");
			response.getWriter().flush();
			return;
		}
		User emp;
		try {
			emp = (User) session.getAttribute("USER");
		} catch (Exception e){
			response.getWriter().write("You must be an employee to perform this action");
			response.getWriter().flush();
			return;
		}
		ArrayList<Request> list;
		try {
			list = emp.getPending();
		} catch (NoAccessException e) {
			response.getWriter().write(e.getMessage());
			response.getWriter().flush();
			return;
		}
		String json = JsonParser.toJson(list);
		response.getWriter().write(json);
		response.getWriter().flush();
	}
	
	public static void getResolved(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.getWriter().write("You must be logged in to continue");
			response.getWriter().flush();
			return;
		}
		User emp;
		try {
			emp = (User) session.getAttribute("USER");
		} catch (Exception e){
			response.getWriter().write("You must be an employee to perform this action");
			response.getWriter().flush();
			return;
		}
		ArrayList<Request> list;
		try {
			list = emp.getResolved();
		} catch (NoAccessException e) {
			response.getWriter().write(e.getMessage());
			response.getWriter().flush();
			return;
		}
		String json = JsonParser.toJson(list);
		response.getWriter().write(json);
		response.getWriter().flush();
	}
	
	public static void resolve(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		int id = Integer.parseInt(request.getParameter("id"));
		try {
			Request req = dao.getByID(Integer.toString(id));
			String status = request.getParameter("status");
			req.setStatus(Request.getStatusFromString(status));
		} catch (Exception e) {
			response.getWriter().write(e.getMessage());
			response.getWriter().flush();
			return;
		}
		response.getWriter().write("Response successfully submited");
		response.getWriter().flush();
	}
	
	public static void getEmpRequests(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		int id = Integer.parseInt(request.getParameter("id"));
		ArrayList<Request> list;
		try {
			list = emp.getEmployeeRequests(id);
		} catch (Exception e) {
			response.getWriter().write(e.getMessage());
			response.getWriter().flush();
			return;
		}
		String json = JsonParser.toJson(list);
		response.getWriter().write(json);
		response.getWriter().flush();
	}
}
