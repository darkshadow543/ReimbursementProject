package com.revature.project1.user;

import java.util.ArrayList;

import com.revature.project1.dao.DAO;
import com.revature.project1.dao.UserOracle;
import com.revature.project1.reimbursement.Request;
import com.revature.project1.util.exception.IncorrectLoginException;
import com.revature.project1.util.exception.NoAccessException;

public abstract class User {
	
	private String email;
	private transient String password;
	private String fName;
	private String lName;
	public static final DAO<User> dao = new UserOracle();
	
	
	public abstract ArrayList<Request> getPending() throws NoAccessException;
	public abstract ArrayList<Request> getResolved() throws NoAccessException;
	
	public User(String email, String password, String fName, String lName) {
		super();
		this.email = email;
		this.password = password;
		this.fName = fName;
		this.lName = lName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fName == null) ? 0 : fName.hashCode());
		result = prime * result + ((lName == null) ? 0 : lName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (fName == null) {
			if (other.fName != null)
				return false;
		} else if (!fName.equals(other.fName))
			return false;
		if (lName == null) {
			if (other.lName != null)
				return false;
		} else if (!lName.equals(other.lName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", fName=" + fName + ", lName=" + lName + "]";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFName() {
		return fName;
	}

	public void setFName(String fName) {
		this.fName = fName;
	}

	public String getLName() {
		return lName;
	}

	public void setLName(String lName) {
		this.lName = lName;
	}

	public static User login(String email, String password) throws NoAccessException, IncorrectLoginException{
		User user;
		try {
			user = dao.getByID(email);
		} catch (Exception e) {
			throw new NoAccessException("There was an error accessing the Database");
		}
		if (user == null) {
			throw new IncorrectLoginException("Incorrect Username");
		}
		if (user.getPassword().equals(password)) {
			return user;
		} else {
			throw new IncorrectLoginException("Inncorrect Password");
		}
		
	}

	
}
