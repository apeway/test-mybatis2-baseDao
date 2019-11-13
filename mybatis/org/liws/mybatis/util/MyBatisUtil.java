package org.liws.mybatis.util;

import java.io.IOException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtil {
	
	// SqlSessionFactory单例就行
	private static SqlSessionFactory factory;
	static {
		try {
			final String MYBATIS_CONFIG_PATH = "config/mybatis-config.xml";
			factory = new SqlSessionFactoryBuilder().build(
					Resources.getResourceAsStream(MYBATIS_CONFIG_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static SqlSession openSession() {
		return factory.openSession();
	}
	
	public static void closeSession(SqlSession session) {
		if (session != null) session.close();
	}
}
