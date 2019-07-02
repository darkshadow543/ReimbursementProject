package com.revature.project1.dao;

import java.util.ArrayList;

public interface ChildTableDAO<T> extends DAO<T> {
	public ArrayList<T> queryByTag(String fk) throws Exception;
}
