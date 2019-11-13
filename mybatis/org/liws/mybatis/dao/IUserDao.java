package org.liws.mybatis.dao;

import org.liws.mybatis.model.User;
import org.liws.mybatis.util.Pager;

public interface IUserDao {
	
	public User login(String username, String password);
	public void add(User user);
	public void update(User user);
	public void delete(int id);
	
	/**
	 * load操作返回的User对象中应包含地址对象列表信息
	 */
	public User load(int id);
	public User loadByUsername(String username);
	
	/**
	 * 	find分页操作，需要考虑按某一个字段排序，按名称搜索<br/>
	 * 	注意：user列表不需要列出地址，所以不用关联地址集合
	 */
	public Pager<User> find(String name);
	public Pager<User> find(String name, int type);
	
}
