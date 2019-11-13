package org.liws.mybatis;

import org.junit.Test;
import org.liws.mybatis.dao.IUserDao;
import org.liws.mybatis.dao.base.DAOFactory;
import org.liws.mybatis.model.User;
import org.liws.mybatis.util.Pager;
import org.liws.mybatis.util.ShopException;
import org.liws.mybatis.util.SystemContext;

public class T2_UserDao {
	
	private IUserDao ud = DAOFactory.getUserDao();
	
	@Test
	public void testLoadById() {
		System.out.println(ud.load(1));
	}

	@Test
	public void testLoadByUsername() {
		System.out.println(ud.loadByUsername("admin"));
	}

	@Test
	public void testLogin() {
		User u = ud.login("admin", "123");
		System.out.println(u.getNickname());
		
		try{
			u = ud.login("adminmmm", "123");
		}catch(ShopException s){
			System.out.println(s.getMessage());
		}
		
		try{
			u = ud.login("admin", "12333");
		}catch(ShopException s){
			System.out.println(s.getMessage());
		}
	}

	@Test
	public void testAdd(){
		User u = new User();
		u.setUsername("ccc");
		u.setPassword("123");
		u.setNickname("曹操");
		u.setType(1);
		
		ud.add(u);
		System.out.println(ud.loadByUsername("ccc"));
	}
	
	@Test
	public void testUpdate() {
		User u = ud.loadByUsername("ccc");
		if(u != null) {
			System.out.println("update前：" + u);
			
			u.setPassword("2222");
			u.setNickname("曹操double fuck");
			u.setType(0);
			ud.update(u);
			System.out.println("update后：" + ud.loadByUsername("ccc"));
		}
		
	}

	@Test
	public void testFind() {
		SystemContext.setPageOffset(0);
		SystemContext.setPageSize(15);
		SystemContext.setSort("user_name");
		SystemContext.setOrder("desc");
		
		Pager<User> ps = ud.find("超");
		System.out.println("找到的users数量: " + ps.getTotalRecord());
		System.out.println("找到的users: " + ps.getDatas());
	
		ps = ud.find("超", 1);
		System.out.println("找到的users数量: " + ps.getTotalRecord());
		System.out.println("找到的users: " + ps.getDatas());
	}

	@Test
	public void testDelete() {
		User u = ud.loadByUsername("ccc");
		if(u != null) {
			System.out.println("delete前：" + u);
			
			ud.delete(u.getId());
			System.out.println("delete后：" + ud.loadByUsername("ccc")); // null
		}
	}

}
