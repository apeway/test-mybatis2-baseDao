package org.liws.mybatis.dao;

import java.util.HashMap;
import java.util.Map;

import org.liws.mybatis.dao.base.BaseDao;
import org.liws.mybatis.model.User;
import org.liws.mybatis.util.Pager;
import org.liws.mybatis.util.ShopException;

/**
 * 由于没有写Service层，这里把业务相关的代码也写到Dao层了
 */
public class UserDao extends BaseDao<User> implements IUserDao {

	@Override
	public User login(String username, String password) {
		User u = this.loadByUsername(username);
		if (u == null)
			throw new ShopException("用户名不存在!");
		if (!password.equals(u.getPassword()))
			throw new ShopException("用户名密码不正确");
		return u;
	}

	@Override
	public void add(User user) {
		User tu = this.loadByUsername(user.getUsername());
		if (tu != null)
			throw new ShopException("要添加的用户已经存在");
		super.add(user);
	}
	
	@Override
	public void delete(int id) {
		// TODO 需要先删除关联对象，省略...
		super.delete(User.class, id);
	}

	@Override
	public void update(User user) {
		super.update(user);
	}

	@Override
	public User load(int id) {
		return super.load(User.class, id);
	}
	
	@Override
	public User loadByUsername(String username) {
		return super.loadBySqlId(User.class.getName()+".load_by_username", username);
	}

	@Override
	public Pager<User> find(String name) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (name != null && !name.equals(""))
			params.put("name", "%" + name + "%");
		return super.find(User.class, params);
	}

	@Override
	public Pager<User> find(String name, int type) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (name != null && !name.equals(""))
			params.put("name", "%" + name + "%");
		params.put("type", type);
		return super.find(User.class, params);
	}
	
	
	
	
	
	
	
	
	
	
	
	
// =================================================================================================================================================	
	
//	@Override
//	public void add(User user) {
//		/**
//		 * <insert id="add" parameterType="User">
//		 *		insert into t_user (username,password,nickname,type) value 
//		 *		(#{username},#{password},#{nickname},#{type})
//		 * </insert>
//		 */
//		
//		User tu = this.loadByUsername(user.getUsername());
//		if(tu != null) throw new ShopException("要添加的用户已经存在");
//		
//		SqlSession session = null;
//		try {
//			session = MyBatisUtil.createSession();
//			session.insert(User.class.getName()+".add", user);  
//			session.commit();
//		} catch (Exception e){
//			e.printStackTrace();
//			session.rollback();
//		} finally {
//			MyBatisUtil.closeSession(session);
//		}
//	}
//
//	@Override
//	public void delete(int id) {
//		/**
//		 * <delete id="delete" parameterType="int">
//		 *		delete from t_user where id = #{id}
//		 * </delete>
//		 */
//		
//		SqlSession session = null;
//		try {
//			session = MyBatisUtil.createSession();
//			session.delete(User.class.getName()+".delete", id);
//			session.commit();
//		} catch (Exception e){
//			e.printStackTrace();
//			session.rollback();
//		} finally {
//			MyBatisUtil.closeSession(session);
//		}
//	}
//
//	@Override
//	public void update(User user) {
//		/**
//		 * <update id="update" parameterType="User">
//		 *		update t_user set nickname=#{nickname},password=#{password},type=#{type} where id=#{id}
//		 * </update>
//		 */
//		
//		SqlSession session = null;
//		try {
//			session = MyBatisUtil.createSession();
//			session.update(User.class.getName()+".update", user);
//			session.commit();
//		} catch (Exception e){
//			e.printStackTrace();
//			session.rollback();
//		} finally {
//			MyBatisUtil.closeSession(session);
//		}
//	}
//
//	@Override
//	public User load(int id) {
//		/**
//		 * <select id="load" parameterType="string" resultType="User">
//		 *		select * from t_user where id = #{id}
//		 * </select>
//		 */
//	
//		SqlSession session = null;
//		User u = null;
//		try {
//			session = MyBatisUtil.createSession();
//			u = session.selectOne(User.class.getName()+".load", id);
//		} finally {
//			MyBatisUtil.closeSession(session);
//		}
//		return u;
//	}
//	
//	@Override
//	public User loadByUsername(String username) {
//		/**
//		 * <select id="load_by_username" parameterType="int" resultType="User">
//		 *		select * from t_user where username = #{username}
//		 * </select>
//		 */
//	
//		SqlSession session = null;
//		User u = null;
//		try {
//			session = MyBatisUtil.createSession();
//			u = session.selectOne(User.class.getName()+".load_by_username", username);
//		} finally {
//			MyBatisUtil.closeSession(session);
//		}
//		return u;
//	}
//	
//	@Override
//	public User login(String username, String password) {
//		User u = this.loadByUsername(username);
//		if(u==null) throw new ShopException("用户名不存在!");
//		if(!password.equals(u.getPassword())) throw new ShopException("用户名密码不正确");
//		return u;
//	}
//
//	@Override
//	public Pager<User> find(String name) {
//		/**
//		 * 由于用到的参数（name, pageOffset, pageSize ）比较多，用map传
//		 * 
//		 * <select id="find" resultType="User" parameterType="map">
//		 * 		select * from t_user 
//		 * 			where username like #{name} or nickname like #{name}
//		 * 			order by ${sort} ${order}
//		 * 			limit #{pageOffset}, #{pageSize}
//		 * </select>
//		 * 
//		 * <select id="find_count" resultType="int" parameterType="map">
//		 * 		select count(*) from t_user 
//		 * 			where username like #{name} or nickname like #{name}
//		 * </select>
//		 */
//		
//		int pageSize = SystemContext.getPageSize();
//		int pageOffset = SystemContext.getPageOffset();
//		String order = SystemContext.getOrder();
//		String sort = SystemContext.getSort();
//		int totalRecord = 0;
//		
//		Pager<User> pages = new Pager<User>();
//		List<User> datas = null;
//		
//		SqlSession session = null;
//		try{
//			session = MyBatisUtil.createSession();
//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("pageSize", pageSize);
//			params.put("pageOffset", pageOffset);
//			params.put("sort", sort);
//			params.put("order", order);
//			if(name != null && !"".equals(name)){
//				params.put("name", "%"+name+"%");  		 //"%"+name+"%"传递之前加%
//			}
//			datas = session.selectList(User.class.getName()+".find", params);
//			
//			totalRecord = session.selectOne(User.class.getName()+".find_count",params);
//		} finally {
//			MyBatisUtil.closeSession(session);
//		}
//		
//		pages.setDatas(datas);
//		pages.setPageOffset(pageOffset);
//		pages.setPageSize(pageSize);
//		pages.setTotalRecord(totalRecord);
//		
//		return pages;
//	}

}
