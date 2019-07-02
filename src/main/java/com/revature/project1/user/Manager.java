package com.revature.project1.user;

import java.util.ArrayList;

import com.revature.project1.dao.EmployeeAODAO;
import com.revature.project1.dao.EmployeeOracleAO;
import com.revature.project1.dao.RequestDAO;
import com.revature.project1.dao.RequestOracle;
import com.revature.project1.reimbursement.Request;
import com.revature.project1.reimbursement.Status;
import com.revature.project1.util.exception.NoAccessException;

public class Manager extends User {
	//for json Use
	private final String type = "MAN";
	
	public String getType() {
		return type;
	}

	private static final RequestDAO dao = new RequestOracle();
	private static final EmployeeAODAO empDAO = new EmployeeOracleAO();
	private int ID;
	

	public Manager(String email, String password, String fName, String lName, int id) {
		super(email, password, fName, lName);
		this.ID = id;
		// TODO Auto-generated constructor stub
	}

	public int getManagerID() {
		return ID;
	}

	public void setManagerID(int managerID) {
		ID = managerID;
	}

	@Override
	public String toString() {
		return "Manager [ManagerID=" + ID + ", " + super.toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Manager other = (Manager) obj;
		if (ID != other.ID)
			return false;
		return true;
	}

	@Override
	public ArrayList<Request> getPending() throws NoAccessException {
		ArrayList<Request> all;
		try {
			all = dao.getAllPending();
		} catch (Exception e) {
			throw new NoAccessException();
		}
		return all;
	}

	@Override
	public ArrayList<Request> getResolved() throws NoAccessException {
		ArrayList<Request> all;
		try {
			all = dao.getAllResolved();
		} catch (Exception e) {
			throw new NoAccessException();
		}
		return all;
	}
	
	public void approve(Request req) throws NoAccessException{
		req.setStatus(Status.APPROVED);
		try {
			dao.insertInto(req);
		} catch (Exception e) {
			throw new NoAccessException();
		}
	}
	
	public void deny(Request req) throws NoAccessException {
		req.setStatus(Status.DENIED);
		try {
			dao.insertInto(req);
		} catch (Exception e) {
			throw new NoAccessException();
		}
	}
	
	public ArrayList<Employee> getAllEmployees() throws Exception {
		return empDAO.getAll();
	}
	
	public ArrayList<Request> getEmployeeRequests(int emp_id) throws Exception {
		return dao.queryByTag(Integer.toString(emp_id));
	}
}
