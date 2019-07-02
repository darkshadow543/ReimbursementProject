package com.revature.project1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.revature.project1.user.*;
import com.revature.project1.util.ConnectionUtil;

public class UserOracle implements DAO<User>{

	private static final Logger log = (Logger) LogManager.getLogger(UserOracle.class);

	public UserOracle() {

	}

	public ArrayList<User> getAll() throws Exception {
		Connection con = ConnectionUtil.getServer();

		if (con == null) {
			log.error("The Connection was null");
			throw new Exception("Unable to connect to Database");

		}

		ArrayList<User> users;
		User temp;

		try {

			String sql = "SELECT E.EMP_ID, U.FNAME, U.LNAME, U.EMAIL, U.PASSWORD "
					+ "FROM EMPLOYEE E INNER JOIN USERS U " + "ON E.EMAIL = U.EMAIL";
			PreparedStatement query = con.prepareStatement(sql);

			ResultSet ret = query.executeQuery();
			users = new ArrayList<User>();
			while (ret.next()) {
				temp = new Employee(ret.getString("EMAIL"), ret.getString("PASSWORD"), ret.getString("FNAME"),
						ret.getString("LNAME"), ret.getInt("EMP_ID"));
				users.add(temp);
			}

			sql = "SELECT M.MAN_ID, U.FNAME, U.LNAME, U.EMAIL, U.PASSWORD " + "FROM MANAGER M INNER JOIN USERS U "
					+ "ON M.EMAIL = U.EMAIL";
			query = con.prepareStatement(sql);
			ret = query.executeQuery();
			while (ret.next()) {
				temp = new Manager(ret.getString("EMAIL"), ret.getString("PASSWORD"), ret.getString("FNAME"),
						ret.getString("LNAME"), ret.getInt("MAN_ID"));
				users.add(temp);
			}
		} catch (SQLException e) {
			log.error("Unable to execute sql query", e);
			throw new Exception("Unable to connect to database");
		}
		return users;
	}

	/*
	 * This will insert into the Users Table in the Database If the user already
	 * exists it will update the user
	 */

	public void insertInto(User object) throws Exception {
		Connection con = ConnectionUtil.getServer();

		if (con == null) {
			log.error("The Connection was null");
			throw new Exception("Unable to connect to Database");

		}
		try {
			User usercomp = this.getByID(object.getEmail());
			String sql;
			PreparedStatement query;
			if (usercomp != null) {
				sql = "UPDATE USERS SET FNAME = ?, LNAME = ?, PASSWORD = ?" + "WHERE EMAIL = ?";
				query = con.prepareStatement(sql);
				query.setString(1, object.getFName());
				query.setString(2, object.getLName());
				query.setString(3, object.getPassword().toString());
				query.setString(4, object.getEmail());
				query.execute();
			} else {
				sql = "INSERT INTO USERS (EMAIL, FNAME, LNAME, PASSWORD) " + "VALUES (?, ?, ?, ?)";
				query = con.prepareStatement(sql);
				query.setString(1, object.getEmail());
				query.setString(2, object.getFName());
				query.setString(3, object.getLName());
				query.setString(4, object.getPassword().toString());
				query.execute();
				query.close();
				if (object instanceof Employee) {
					sql = "INSERT INTO EMPLOYEE (EMAIL) VALUES (?)";
					query = con.prepareStatement(sql);
					query.setString(1, object.getEmail());
					query.execute();
				} else if (object instanceof Manager) {
					sql = "INSERT INTO MANAGER (EMAIL) VALUES (?)";
					query = con.prepareStatement(sql);
					query.setString(1, object.getEmail());
					query.execute();
				}
			}

		} catch (SQLException e) {
			log.error("Unable to execute sql query", e);
			throw new Exception("Unable to connect to database");
		}

	}

	public User getByID(String key) throws Exception {

		Connection con = ConnectionUtil.getServer();

		if (con == null) {
			log.error("The Connection was null");
			throw new Exception("Unable to connect to Database");

		}

		try {

			String sql = "SELECT FNAME, LNAME, EMAIL, PASSWORD " + "FROM USERS " + "WHERE EMAIL = ?";
			PreparedStatement query = con.prepareStatement(sql);
			query.setString(1, key);
			ResultSet ret = query.executeQuery();
			if (!ret.next()) {
				return null;
			}
			sql = "SELECT EMP_ID, EMAIL " + "FROM EMPLOYEE " + "WHERE EMAIL = ?";
			query = con.prepareStatement(sql);
			query.setString(1, key);
			ResultSet emp = query.executeQuery();
			if (emp.next()) {
				User Cust = new Employee(ret.getString("EMAIL"), ret.getString("PASSWORD"), ret.getString("FNAME"),
						ret.getString("LNAME"), emp.getInt("EMP_ID"));
				return Cust;
			}
			sql = "SELECT MAN_ID, EMAIL " + "FROM MANAGER " + "WHERE EMAIL = ?";
			query = con.prepareStatement(sql);
			query.setString(1, key);
			ResultSet man = query.executeQuery();
			if (man.next()) {
				User empl = new Manager(ret.getString("EMAIL"), ret.getString("PASSWORD"), ret.getString("FNAME"),
						ret.getString("LNAME"), man.getInt("MAN_ID"));
				return empl;
			}
		} catch (SQLException e) {
			log.error("Unable to execute sql query", e);
			throw new Exception("Unable to connect to database");
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		DAO<User> test = new UserOracle();
		Manager u1 = new Manager("sbengel@hotmail.com", "20Dodger15", "Shannon", "Bengel", 0);
		Employee u2 = new Employee("starplatinum@gmail.com", "YareYareDaz3", "Jotaro", "Joestar", 1);
		test.insertInto(u1);
		test.insertInto(u2);
		System.out.println(test.getAll());
		System.out.println(test.getByID("sbengel@hotmail.com"));
		System.out.println(test.getByID("starplatinum@gmail.com"));
	}
	
}
