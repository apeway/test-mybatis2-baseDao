package org.liws.mybatis;

import java.util.List;

import org.junit.Test;
import org.liws.mybatis.dao.IAddressDao;
import org.liws.mybatis.dao.base.DAOFactory;
import org.liws.mybatis.model.Address;
import org.liws.mybatis.util.ShopException;

public class T3_AddressDao {

	private IAddressDao addressDao = DAOFactory.getAddressDao();
	
	@Test
	public void test_add_update_delete() {
		Address addr = new Address("addr_add","phone_add","postcode_add");
		int userId = 1; // userId不存在时会进异常
		
		try {
			// add
			addressDao.add(addr, userId);
			int autoAddrId = addr.getId();
			System.out.println("生成的id=" + autoAddrId);
			addr = addressDao.load(autoAddrId);
			System.out.println("新增的addr=" + addr);
			
			//update 
			addr.setName("addr_update1");
			addr.setPhone("phone_update1");
			addr.setPostcode("postcode_update1");
			addressDao.update(addr);
			System.out.println("修改的addr=" + addressDao.load(autoAddrId));
			
			// delete
			addressDao.delete(autoAddrId);
			System.out.println("修改的addr=" + addressDao.load(autoAddrId));
		} catch (ShopException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * 可将log4j日记级别调整为DEBUG(log4j.rootLogger=DEBUG, stdout)，看两种方式各执行了多少条sql
	 */
	@Test
	public void testLoad() {  
		int addrId = 1;
		
		/* 
		 * 1条sql:
		 * 
		 * select *, t1.id as 'a_id' from t_address t1 
		 * 		left join t_user t2 on t1.user_id = t2.id where t1.id = ?【1】;
		 */
		System.out.println("load: " + addressDao.load(addrId));		
		
		System.out.println("==========================================");
		
		/*
		 * XXX 不推荐， sql条数：(N+1)=1+1=2：
		 * 
		 * 首先，第一条sql取id为1的1行address信息：
		 * select * from t_address where id = ?【1】; 
		 * 
		 * 随后，对该行address结果，再执行1条sql来根据其user_id取关联的user信息。
		 * select *, t1.id as u_id, t2.id as a_id from t_user t1 
		 * 		left join t_address t2 on(t1.id = t2.user_id) where t1.id = ?【1】; 
		 */
		System.out.println("load_: " + addressDao.load_(addrId)); 	
	}

	@Test
	public void testList() {   
		int userId = 1;
		
		/*
		 * 1条sql:
		 * 
		 * select *, t1.id as 'a_id' from t_address t1 
		 * 		left join t_user t2 on t1.user_id = t2.id where t2.id = ?【1】 ;
		 */
		List<Address> list = addressDao.list(userId);
		System.out.println("listSize=" + list.size() + ", list=" + list);	
		
		System.out.println("==========================================");
		
		/*
		 * XXX 不推荐 ， sql条数：(N+1)条，由于sql缓存，最后结果为2：
		 * 
		 * 首先，第一条sql取user_id为1的N行address信息：
		 * select * from t_address where user_id = ?【1】;
		 * 
		 * 随后，对第一条sql的每行address结果，再执行1条sql来根据user_id取关联的user信息，就需要N条sql。
		 * 		但由于这N行address的user_id相同，导致要执行的sql一致，会命中sql的缓存，所以这N条sql变1条。
		 * select *, t1.id as u_id, t2.id as a_id from t_user t1 
		 * 		left join t_address t2 on(t1.id = t2.user_id) where t1.id = ?【1】;
		 * 
		 * XXX 分析：
		 * 		如果第一步的那条sql的执行的结果中，每行address信息的user_id不一定相同，
		 * 那么结果中总共有多少个不同的user_id值，第二步就需要执行多少条sql。
		 */
		list = addressDao.list_(userId);
		System.out.println("listSize=" + list.size() + ", list=" + list); 	
	}
	
}
