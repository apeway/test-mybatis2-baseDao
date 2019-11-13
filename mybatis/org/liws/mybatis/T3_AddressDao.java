package org.liws.mybatis;

import java.util.List;

import org.junit.Test;
import org.liws.mybatis.dao.IAddressDao;
import org.liws.mybatis.dao.IUserDao;
import org.liws.mybatis.dao.base.DAOFactory;
import org.liws.mybatis.model.Address;
import org.liws.mybatis.util.ShopException;

public class T3_AddressDao {

	private IAddressDao addressDao = DAOFactory.getAddressDao();
	private IUserDao userDao = DAOFactory.getUserDao();
	
	@Test
	public void testAdd() {
		Address addr = new Address("addr_add","phone_add","postcode_add");
		
		int userId = 1;
		System.out.println("添加前，用户" + userId + "的地址列表：" + addressDao.list(userId));
		try {
			addressDao.add(addr, userId);
			System.out.println("添加后，用户" + userId + "的地址列表：" + addressDao.list(userId));
		} catch (ShopException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("============================");
		
		userId = 10000;
		System.out.println("添加前，用户" + userId + "的地址列表：" + addressDao.list(userId));
		try {
			addressDao.add(addr, userId);
			System.out.println("添加后，用户" + userId + "的地址列表：" + addressDao.list(userId));
		} catch (ShopException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testUpdate() {
		int userId = 1;
		List<Address> addresses = userDao.load(userId).getAddresses();
		Address addressToUpdate = addresses.get(addresses.size()-1);
		System.out.println("update前：" + addressDao.load(addressToUpdate.getId()));
		
		addressToUpdate.setName("addr_update1");
		addressToUpdate.setPhone("phone_update1");
		addressToUpdate.setPostcode("postcode_update1");
		addressDao.update(addressToUpdate);
		System.out.println("update后：" + addressDao.load(addressToUpdate.getId()));
	}
	
	@Test
	public void testDelete() {
		int userId = 1;
		List<Address> addresses = userDao.load(userId).getAddresses();
		Address addressToDelete = addresses.get(addresses.size()-1);
		System.out.println("delete前：" + addressDao.load(addressToDelete.getId()));
		
		addressDao.delete(addressToDelete.getId());
		System.out.println("delete后：" + addressDao.load(addressToDelete.getId()));
	}
	
	@Test
	public void testLoad() {  
		int userId = 1;
		List<Address> addresses = userDao.load(userId).getAddresses();
		int addressIdToLoad = addresses.get(addresses.size()-1).getId();
		
		System.out.println("load: " + addressDao.load(addressIdToLoad));
		System.out.println("==========================================");
		System.out.println("load_: " + addressDao.load_(addressIdToLoad)); // XXX 不推荐 (N+1)
	}

	@Test
	public void testList() {   
		int userId = 1;
		System.out.println("list: " + addressDao.list(userId));
		System.out.println("==========================================");
		System.out.println("list_: " + addressDao.list_(userId)); // XXX 不推荐 (N+1)
	}
	
}
