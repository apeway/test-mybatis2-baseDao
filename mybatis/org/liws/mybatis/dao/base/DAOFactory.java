package org.liws.mybatis.dao.base;

import org.liws.mybatis.dao.AddressDao;
import org.liws.mybatis.dao.IAddressDao;
import org.liws.mybatis.dao.IUserDao;
import org.liws.mybatis.dao.UserDao;

public class DAOFactory {

	public static IAddressDao getAddressDao() {
		return new AddressDao();
	}

	public static IUserDao getUserDao() {
		return new UserDao();
	}
}
