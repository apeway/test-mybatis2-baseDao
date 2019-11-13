package org.liws.mybatis.dao;

import java.util.List;

import org.liws.mybatis.model.Address;

public interface IAddressDao {
	
	public void add(Address address, int userId);
	public void delete(int id);
	public void update(Address address);
	
	/**
	 * load的结果需要关联地址所属的用户
	 */
	public Address load(int id);
	public Address load_(int id);
	
	/**
	 * 一个用户没多少地址，不需要分页。
	 * 结果需要关联地址所属的用户
	 */
	public List<Address> list(int userId); 
	public List<Address> list_(int userId); 
}
