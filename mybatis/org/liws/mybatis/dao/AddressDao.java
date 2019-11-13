package org.liws.mybatis.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.liws.mybatis.dao.base.BaseDao;
import org.liws.mybatis.dao.base.DAOFactory;
import org.liws.mybatis.model.Address;
import org.liws.mybatis.model.User;
import org.liws.mybatis.util.MyBatisUtil;
import org.liws.mybatis.util.ShopException;

/**
 * 由于没有写Service层，这里把业务相关的代码也写到Dao层了
 */
public class AddressDao extends BaseDao<Address> implements IAddressDao {
	
	private IUserDao userDao = DAOFactory.getUserDao();
	
	@Override
	public void add(Address address, int userId) {
		User u = userDao.load(userId);
		if (u == null)
			throw new ShopException("添加地址的用户不存在");
		address.setUser(new User(userId));

		super.add(address);
	}

	@Override
	public void update(Address address) {
		super.update(address);
	}

	@Override
	public void delete(int id) {
		super.delete(Address.class, id);
	}

	@Override
	public Address load(int id) {
		return super.load(Address.class, id);
	}
	
	@Override
	public List<Address> list(int userId) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		return super.list(Address.class, params);
	}

	@Override
	public Address load_(int id) {
		SqlSession session = null;
		Address addr = null;
		try {
			session = MyBatisUtil.openSession();
			addr = session.selectOne(Address.class.getName()+".load_", id);
		} finally {
			MyBatisUtil.closeSession(session);
		}
		return addr;
	}

	@Override
	public List<Address> list_(int userId) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		return this.list(Address.class.getName()+".list_", params);
	}

}
