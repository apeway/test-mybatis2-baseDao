package org.liws.jdbc.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
	private static Properties jdbcProp;
	
	public static Properties getJdbcProp() {
		try {
			if(jdbcProp==null) {
				jdbcProp = new Properties();
				jdbcProp.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("config/jdbc.properties"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jdbcProp;
	}
	
}
