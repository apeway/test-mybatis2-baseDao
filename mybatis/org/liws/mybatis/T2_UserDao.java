package org.liws.mybatis;

import org.junit.Test;
import org.liws.mybatis.dao.IUserDao;
import org.liws.mybatis.dao.base.DAOFactory;
import org.liws.mybatis.model.User;
import org.liws.mybatis.util.Pager;
import org.liws.mybatis.util.ShopException;
import org.liws.mybatis.util.SystemContext;

public class T2_UserDao {
	
	private IUserDao userDao = DAOFactory.getUserDao();
	
	@Test
	public void testLoadById() {
		System.out.println(userDao.load(1));
	}

	@Test
	public void testLoadByUsername() {
		System.out.println(userDao.loadByUsername("admin"));
	}

	@Test
	public void testLogin() {
		// login 1
		String userId = "admin";
		String password = "123";
		User u = userDao.login(userId, password);
		System.out.println("登录成功，登录用户为：" + u.getNickname());
		
		// login 2
		userId = "adminm";
		password = "123";
		try{
			u = userDao.login(userId, password);
		}catch(ShopException s){
			System.out.println(s.getMessage());
		}
		
		// login 3
		userId = "admin";
		password = "123456";
		try{
			u = userDao.login("admin", password);
		}catch(ShopException s){
			System.out.println(s.getMessage());
		}
	}

	@Test
	public void test_Add_update_delete(){
		User user = new User();
		user.setUsername("ccc");
		user.setPassword("123");
		user.setNickname("曹操");
		user.setType(1);

		// add
		userDao.add(user);
		int autoId = user.getId();
		System.out.println("生成的id=" + autoId);
		user = userDao.load(autoId);
		System.out.println("新增的user=" + user);
		
		// update 
		user.setPassword("123456");
		user.setNickname("曹操double fuck");
		user.setType(0);
		userDao.update(user);
		System.out.println("修改后的user=" + userDao.load(autoId));
		
		// delete
		userDao.delete(autoId);
		System.out.println("delete后的user=" + userDao.load(autoId));
	}
	
	/**
	 * 先执行UsersInitMain准备测试数据
	 */
	@Test
	public void testFind() {
		SystemContext.setPageOffset(0);
		SystemContext.setPageSize(15);
		SystemContext.setSort("user_name");
		SystemContext.setOrder("desc");
		
		Pager<User> ps = userDao.find("超");
		System.out.println("找到的users数量: " + ps.getTotalRecord());
		System.out.println("找到的users: " + ps.getDatas());
	
		ps = userDao.find("超", 1);
		System.out.println("找到的users数量: " + ps.getTotalRecord());
		System.out.println("找到的users: " + ps.getDatas());
	}

}
