package com.revature.project1.dao;

import java.util.ArrayList;

public interface DAO<T> {
	ArrayList<T> getAll() throws Exception;
	void insertInto(T object) throws Exception;
	T getByID(String key) throws Exception;
}
