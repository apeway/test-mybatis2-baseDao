package org.liws.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.liws.jdbc.util.DBUtil;
import org.liws.mybatis.model.Address;
import org.liws.mybatis.model.User;

/**
 * 对于jdbc方式的一对一、一对多处理，都可以一条sql处理或者分开处理。
 * 
 * 这里演示一对多，一对一也一样。
 */
public class TestJDBCRelation {

	/**
	 *  一对多,  user带有多个addr。
	 */
	@Test
	public void testLoadUserWithAddrs() {
		int userId = 1;
		
		System.out.println(" loadUserInOneTime(int userId) invoked !");
		System.out.println(loadUserInOneTime(userId));
		
		System.out.println("\n loadUserInMultiTimes(int userId) invoked !");
		System.out.println(loadUserInMultiTimes(userId));
	}
	
	/**
	 * 1、一条sql查出来user和addrs
	 */
	private User loadUserInOneTime(int userId) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		List<Address> addrs = new ArrayList<Address>();
		try {
			con = DBUtil.getConnection();
			String sql = "select *, t2.id as 'a_id' " 
					+ "from t_user t1 "
					+ "left join t_address t2 on(t1.id=t2.user_id) "
					+ "where t1.id = ?";
			ps = con.prepareStatement(sql);
			System.out.println("sql==>" + sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				if (user == null) { // !!!!
					user = new User();
					user.setId(rs.getInt("user_id"));
					user.setNickname(rs.getString("nickname"));
					user.setPassword(rs.getString("password"));
					user.setType(rs.getInt("type"));
					user.setUsername(rs.getString("user_name"));
				}

				Address addr = new Address();
				addr.setId(rs.getInt("a_id"));
				addr.setName(rs.getString("name"));
				addr.setPhone(rs.getString("phone"));
				addr.setPostcode(rs.getString("post_code"));
				addrs.add(addr);
			}
			user.setAddresses(addrs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(con);
		}
		return user;
	}
	
	/**
	 * 2、一条sql查user,额外加一条sql查addrs
	 * 当然这种影响效率！
	 */
	@SuppressWarnings("resource")
	private User loadUserInMultiTimes(int userId) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		List<Address> addrs = new ArrayList<Address>();
		try {
			con = DBUtil.getConnection();
			String sql = "select * from t_user where id = ?";
			ps = con.prepareStatement(sql);
			System.out.println("sql==>" + sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while(rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setNickname(rs.getString("nickname"));
				user.setPassword(rs.getString("password"));
				user.setType(rs.getInt("type"));
				user.setUsername(rs.getString("user_name"));
			}
			
			sql = "select * from t_address where user_id = ?";
			System.out.println("sql==>" + sql);
			ps = con.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while(rs.next()) {
				Address a = new Address();
				a.setId(rs.getInt("id"));
				a.setName(rs.getString("name"));
				a.setPhone(rs.getString("phone"));
				a.setPostcode(rs.getString("post_code"));
				addrs.add(a);
			}
			user.setAddresses(addrs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(con);
		}
		return user;
	}
	
	
	
	
	
	
	@Test
	public void testListAddressesWithUser() {
		System.out.println(list(1));
	}
	
	/**
	 * 得到地址时连带取出相应用户 ， 节约查询次数
	 */
	private List<Address> list(int userId) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Address> as = new ArrayList<Address>();
		Address a = null;
		User u = null;
		try {
			String sql = "select t1.id as 'a_id',t1.name as 'a_name',t1.phone as 'phone',t1.post_code," +
					" t2.id as 'user_id', t2.nickname, t2.password, t2.user_name, t2.type as 'user_type'" +
					" from t_address t1 left join t_user t2 on(t1.user_id=t2.id) where user_id=?";
			//sql = "select t1.*, t2.*, t1.id as 'a_id' from ...";
			//sql = "select *, t1.id as 'a_id' from ...";
			
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while(rs.next()) {
				a = new Address();
				a.setId(rs.getInt("a_id"));
				a.setName(rs.getString("a_name"));
				a.setPhone(rs.getString("phone"));
				a.setPostcode(rs.getString("post_code"));
				
				u = new User();
				u.setId(rs.getInt("user_id"));
				u.setNickname(rs.getString("nickname"));
				u.setPassword(rs.getString("password"));
				u.setType(rs.getInt("user_type"));
				u.setUsername(rs.getString("user_name"));
				a.setUser(u);
				
				as.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps);
			DBUtil.close(con);
		}
		return as;
	}
	
}
