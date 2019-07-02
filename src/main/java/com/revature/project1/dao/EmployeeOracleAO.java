package com.revature.project1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.revature.project1.user.Employee;
import com.revature.project1.util.ConnectionUtil;

public class EmployeeOracleAO implements EmployeeAODAO{

	private static final Logger log = (Logger) LogManager.getLogger(UserOracle.class);
	
	public EmployeeOracleAO() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<Employee> getAll() throws Exception{
		Connection con = ConnectionUtil.getServer();

		if (con == null) {
			log.error("The Connection was null");
			throw new Exception("Unable to connect to Database");

		}

		ArrayList<Employee> users;
		Employee temp;

		try {
			String sql = "SELECT * FROM EMPLOYEES";
			PreparedStatement query = con.prepareStatement(sql);

			ResultSet ret = query.executeQuery();
			users = new ArrayList<Employee>();
			while (ret.next()) {
				temp = new Employee(ret.getString("EMAIL"), ret.getString("PASSWORD"), ret.getString("FNAME"),
						ret.getString("LNAME"), ret.getInt("EMP_ID"));
				users.add(temp);
			}
		} catch (SQLException e) {
			log.error("Unable to execute sql query", e);
			throw new Exception("Unable to connect to database");
		}
		return users;
	}

	@Override
	public Employee getByID(int i) throws Exception{
		Connection con = ConnectionUtil.getServer();

		if (con == null) {
			log.error("The Connection was null");
			throw new Exception("Unable to connect to Database");

		}

		Employee temp;

		try {
			String sql = "SELECT * FROM EMPLOYEES WHERE EMP_ID = ?";
			PreparedStatement query = con.prepareStatement(sql);
			query.setInt(1, i);
			ResultSet ret = query.executeQuery();
			if (ret.next()) {
				temp = new Employee(ret.getString("EMAIL"), ret.getString("PASSWORD"), ret.getString("FNAME"),
						ret.getString("LNAME"), ret.getInt("EMP_ID"));
				return temp;
			} else {
				return null;
			}
		} catch (SQLException e) {
			log.error("Unable to execute sql query", e);
			throw new Exception("Unable to connect to database");
		}
	}

}
