package com.revature.project1.user;

import java.util.ArrayList;

import com.revature.project1.dao.DAO;
import com.revature.project1.dao.RequestDAO;
import com.revature.project1.dao.RequestOracle;
import com.revature.project1.dao.UserOracle;
import com.revature.project1.reimbursement.Request;
import com.revature.project1.reimbursement.Status;
import com.revature.project1.util.exception.NoAccessException;
import com.revature.project1.validator.RequestValidator;

/**
 * @author sbeng
 *
 */
public class Employee extends User {
	private static final RequestDAO dao = new RequestOracle();
	private static final DAO<User> UserDAO = new UserOracle();
	// employee ID
	private int id;
	// for JSON use
	public final String type = "EMP";
	

	public String getType() {
		return type;
	}

	/**
	 * Returns a list of the employees resolved requests
	 * 
	 * @return an arraylist of the employees not pending requests
	 * @throws NoAccessException
	 */
	public ArrayList<Request> getResolved() throws NoAccessException {
		ArrayList<Request> all;
		try {
			all = dao.getAllResolvedByKey(this.id);
		} catch (Exception e) {
			throw new NoAccessException();
		}
		return all;
	}

	/**
	 * @return an ArrayList of the employees pending Requests
	 * @throws NoAccessException
	 */
	public ArrayList<Request> getPending() throws NoAccessException {
		ArrayList<Request> all;
		try {
			all = dao.getAllPendingByKey(this.id);
		} catch (Exception e) {
			throw new NoAccessException();
		}
		return all;
	}

	public void makeRequest(float ammount, String reason) throws IllegalArgumentException, NoAccessException {
		Request req = new Request(0, this.id, null, ammount, reason, Status.PENDING);
		RequestValidator.validateRequest(req);
		try {
			dao.insertInto(req);
		} catch (Exception e) {
			throw new NoAccessException("Trouble while connecting to Database");
		}

	}
	
	public void update() throws NoAccessException {
		try {
			UserDAO.insertInto(this);
		} catch (Exception e) {
			throw new NoAccessException("Trouble while connecting to Database");
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Employee(String email, String password, String fName, String lName, int id) {
		super(email, password, fName, lName);
		this.id = id;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", " + super.toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + id;
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
		Employee other = (Employee) obj;
		if (id != other.id)
			return false;
		return true;
	}


}
