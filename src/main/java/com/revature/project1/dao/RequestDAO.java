package com.revature.project1.dao;

import java.util.ArrayList;

import com.revature.project1.reimbursement.Request;

public interface RequestDAO extends ChildTableDAO<Request> {
	public ArrayList<Request> getAllPending() throws Exception;
	public ArrayList<Request> getAllResolved() throws Exception;
	public ArrayList<Request> getAllPendingByKey(int key) throws Exception;
	public ArrayList<Request> getAllResolvedByKey(int key) throws Exception;
}
