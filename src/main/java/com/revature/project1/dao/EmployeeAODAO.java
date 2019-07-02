package com.revature.project1.dao;

import java.util.ArrayList;

import com.revature.project1.user.Employee;

public interface EmployeeAODAO {
	public ArrayList<Employee> getAll() throws Exception;
	public Employee getByID(int i) throws Exception;
}
