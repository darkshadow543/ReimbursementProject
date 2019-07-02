package com.revature.project1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.revature.project1.reimbursement.Request;
import com.revature.project1.reimbursement.Status;
import com.revature.project1.util.ConnectionUtil;

public class RequestOracle implements RequestDAO{

	private static final Logger log = (Logger) LogManager.getLogger(UserOracle.class);
	
	public RequestOracle() {
		
	}

	public ArrayList<Request> getAll() throws Exception {
		
		Connection con = ConnectionUtil.getServer();

		if (con == null) {
			log.error("The Connection was null");
			throw new Exception("Unable to connect to Database");
		}
		
		String sql;
		PreparedStatement query;
		ArrayList<Request> list = new ArrayList<Request>();
		try {
			sql = "SELECT * FROM REQUEST";
			query = con.prepareStatement(sql);
			ResultSet ret = query.executeQuery();
			
			while (ret.next()) {
				list.add(new Request(ret.getInt("REQUEST_ID"), ret.getInt("EMP_ID"), 
						ret.getTimestamp("TIME_REQUESTED"), ret.getFloat("AMOUNT"), 
						ret.getString("REASON"), Request.getStatusFromString(ret.getString("RESOLVED"))));
			}
			return list;
		} catch (SQLException e) {
			log.error("Unable to execute sql query", e);
			throw new Exception("Unable to connect to database");
		}
	}

	public void insertInto(Request object) throws Exception {
		Connection con = ConnectionUtil.getServer();

		if (con == null) {
			log.error("The Connection was null");
			throw new Exception("Unable to connect to Database");
		}
		
		String sql;
		PreparedStatement query;
		try {
			Request req = this.getByID(Integer.toString(object.getID()));
			if(req != null) {
				sql = "UPDATE REQUEST SET AMOUNT = ?, REASON = ?, RESOLVED = ? "
						+ "WHERE REQUEST_ID = ?";
				query = con.prepareStatement(sql);
				query.setFloat(1, object.getAmount());
				query.setString(2, object.getReason());
				query.setString(3, Request.statusToString(object.getStatus()));
				query.setInt(4, object.getID());
				query.execute();
			} else {
				sql = "INSERT INTO REQUEST (AMOUNT, REASON, RESOLVED, EMP_ID) "
						+ "VALUES(?,?,?,?)";
				query = con.prepareStatement(sql);
				query.setFloat(1, object.getAmount());
				query.setString(2, object.getReason());
				query.setString(3, Request.statusToString(object.getStatus()));
				query.setInt(4, object.getEMP_ID());
				query.execute();
			}
			
		} catch (SQLException e) {
			log.error("Unable to execute sql query", e);
			throw new Exception("Unable to connect to database");
		}
		
	}

	public Request getByID(String key) throws Exception {
		Connection con = ConnectionUtil.getServer();

		if (con == null) {
			log.error("The Connection was null");
			throw new Exception("Unable to connect to Database");
		}
		
		String sql = "";
		PreparedStatement query;
		try {
			sql = "SELECT * FROM REQUEST WHERE REQUEST_ID = ?";
			query = con.prepareStatement(sql);
			query.setInt(1, Integer.parseInt(key));
			ResultSet ret = query.executeQuery();
			if (ret.next()){
				return new Request(ret.getInt("REQUEST_ID"), ret.getInt("EMP_ID"), 
						ret.getTimestamp("TIME_REQUESTED"), ret.getFloat("AMOUNT"), 
						ret.getString("REASON"), Request.getStatusFromString(ret.getString("RESOLVED"))); 
			}
			return null;
		} catch (SQLException e) {
			log.error("Unable to execute sql query", e);
			throw new Exception("Unable to connect to database");
		}
	}

	public ArrayList<Request> queryByTag(String fk) throws Exception {
		Connection con = ConnectionUtil.getServer();

		if (con == null) {
			log.error("The Connection was null");
			throw new Exception("Unable to connect to Database");
		}
		
		String sql = "";
		PreparedStatement query;
		ArrayList<Request> list = new ArrayList<Request>();
		try {
			sql = "SELECT * FROM REQUEST WHERE EMP_ID = ?";
			query = con.prepareStatement(sql);
			query.setInt(1, Integer.parseInt(fk));
			ResultSet ret = query.executeQuery();
			
			while (ret.next()) {
				list.add(new Request(ret.getInt("REQUEST_ID"), ret.getInt("EMP_ID"), 
						ret.getTimestamp("TIME_REQUESTED"), ret.getFloat("AMOUNT"), 
						ret.getString("REASON"), Request.getStatusFromString(ret.getString("RESOLVED"))));
			}
			return list;
		} catch (SQLException e) {
			log.error("Unable to execute sql query", e);
			throw new Exception("Unable to connect to database");
		}
	}
	
	public static void main(String[] args) throws Exception {
		ChildTableDAO<Request> dao = new RequestOracle();
		dao.insertInto(new Request(1, 1, null, 199.99f, "OCA Test", Status.PENDING));
		dao.insertInto(new Request(2, 1, null, 4.99f, "Chocolate", Status.PENDING));
		System.out.println(dao.getAll());
		System.out.println(dao.getByID(Integer.toString(1)));
		System.out.println(dao.getByID(Integer.toString(2)));
		System.out.println(dao.getByID(Integer.toString(3)));
		System.out.println(dao.queryByTag(Integer.toString(1)));
		System.out.println(dao.queryByTag(Integer.toString(2)));
	}

	@Override
	public ArrayList<Request> getAllPending() throws Exception{
		Connection con = ConnectionUtil.getServer();

		if (con == null) {
			log.error("The Connection was null");
			throw new Exception("Unable to connect to Database");
		}
		
		String sql;
		PreparedStatement query;
		ArrayList<Request> list = new ArrayList<Request>();
		try {
			sql = "SELECT * FROM REQUEST WHERE RESOLVED = 'PENDING'";
			query = con.prepareStatement(sql);
			ResultSet ret = query.executeQuery();
			
			while (ret.next()) {
				list.add(new Request(ret.getInt("REQUEST_ID"), ret.getInt("EMP_ID"), 
						ret.getTimestamp("TIME_REQUESTED"), ret.getFloat("AMOUNT"), 
						ret.getString("REASON"), Request.getStatusFromString(ret.getString("RESOLVED"))));
			}
			return list;
		} catch (SQLException e) {
			log.error("Unable to execute sql query", e);
			throw new Exception("Unable to connect to database");
		}
	}

	@Override
	public ArrayList<Request> getAllResolved() throws Exception {
		Connection con = ConnectionUtil.getServer();

		if (con == null) {
			log.error("The Connection was null");
			throw new Exception("Unable to connect to Database");
		}
		
		String sql;
		PreparedStatement query;
		ArrayList<Request> list = new ArrayList<Request>();
		try {
			sql = "SELECT * FROM REQUEST  WHERE RESOLVED != 'PENDING'";
			query = con.prepareStatement(sql);
			ResultSet ret = query.executeQuery();
			
			while (ret.next()) {
				list.add(new Request(ret.getInt("REQUEST_ID"), ret.getInt("EMP_ID"), 
						ret.getTimestamp("TIME_REQUESTED"), ret.getFloat("AMOUNT"), 
						ret.getString("REASON"), Request.getStatusFromString(ret.getString("RESOLVED"))));
			}
			return list;
		} catch (SQLException e) {
			log.error("Unable to execute sql query", e);
			throw new Exception("Unable to connect to database");
		}
	}

	@Override
	public ArrayList<Request> getAllPendingByKey(int key) throws Exception {
		Connection con = ConnectionUtil.getServer();

		if (con == null) {
			log.error("The Connection was null");
			throw new Exception("Unable to connect to Database");
		}
		
		String sql = "";
		PreparedStatement query;
		ArrayList<Request> list = new ArrayList<Request>();
		try {
			sql = "SELECT * FROM REQUEST WHERE EMP_ID = ? AND  WHERE RESOLVED = 'PENDING'";
			query = con.prepareStatement(sql);
			query.setInt(1, key);
			ResultSet ret = query.executeQuery();
			
			while (ret.next()) {
				list.add(new Request(ret.getInt("REQUEST_ID"), ret.getInt("EMP_ID"), 
						ret.getTimestamp("TIME_REQUESTED"), ret.getFloat("AMOUNT"), 
						ret.getString("REASON"), Request.getStatusFromString(ret.getString("RESOLVED"))));
			}
			return list;
		} catch (SQLException e) {
			log.error("Unable to execute sql query", e);
			throw new Exception("Unable to connect to database");
		}
	}

	@Override
	public ArrayList<Request> getAllResolvedByKey(int key) throws Exception{
		Connection con = ConnectionUtil.getServer();

		if (con == null) {
			log.error("The Connection was null");
			throw new Exception("Unable to connect to Database");
		}
		
		String sql = "";
		PreparedStatement query;
		ArrayList<Request> list = new ArrayList<Request>();
		try {
			sql = "SELECT * FROM REQUEST WHERE EMP_ID = ? AND RESOLVED != 'PENDING'";
			query = con.prepareStatement(sql);
			query.setInt(1, key);
			ResultSet ret = query.executeQuery();
			
			while (ret.next()) {
				list.add(new Request(ret.getInt("REQUEST_ID"), ret.getInt("EMP_ID"), 
						ret.getTimestamp("TIME_REQUESTED"), ret.getFloat("AMOUNT"), 
						ret.getString("REASON"), Request.getStatusFromString(ret.getString("RESOLVED"))));
			}
			return list;
		} catch (SQLException e) {
			log.error("Unable to execute sql query", e);
			throw new Exception("Unable to connect to database");
		}
	}

}
