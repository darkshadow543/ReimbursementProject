package com.revature.project1.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.project1.servlet.controler.RequestController;
import com.revature.project1.servlet.controler.UserController;

public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public FrontController() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String context = request.getContextPath();
		String path = requestURI.substring(context.length());
		System.out.println(path);
		switch(path) {
		case "/login.do":
			UserController.login(request, response);
			break;
		case "/logout.do":
			UserController.logout(request, response);
			break;
		case "/updateinfo.do":
			UserController.updateInfo(request, response);
			break;
		case "/getemployees.do":
			UserController.getEmployees(request, response);
			break;
		case "/getemprequests.do":
			RequestController.getEmpRequests(request, response);
			break;
		case "/getpending.do":
			RequestController.getPending(request, response);
			break;
		case "/getresolved.do":
			RequestController.getResolved(request, response);
			break;
		case "/makerequest.do":
			RequestController.makeRequest(request, response);
			break;
		case "/resolve.do" :
			RequestController.resolve(request, response);
			break;
		default:
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.html");
			dispatcher.forward(request, response);
			break;
		}
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
