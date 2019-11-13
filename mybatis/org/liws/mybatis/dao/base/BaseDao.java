package org.liws.mybatis.dao.base;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.liws.mybatis.util.MyBatisUtil;
import org.liws.mybatis.util.Pager;
import org.liws.mybatis.util.SystemContext;

/**
 * 约定优于配置
 */
public class BaseDao<T> {
	
	private String getNamespace(T obj) {
		return obj.getClass().getName();
	}
	private String getNamespace(Class<T> clz) {
		return clz.getName();
	}
	
	public void add(T obj) {
		SqlSession session = null;
		try {
			session = MyBatisUtil.openSession();
			session.insert(getNamespace(obj) + ".add", obj);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		} finally {
			MyBatisUtil.closeSession(session);
		}
	}

	public void update(T obj) {
		SqlSession session = null;
		try {
			session = MyBatisUtil.openSession();
			session.update(getNamespace(obj) + ".update", obj);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		} finally {
			MyBatisUtil.closeSession(session);
		}
	}

	public void delete(Class<T> clz, int id) {
		SqlSession session = null;
		try {
			session = MyBatisUtil.openSession();
			session.delete(getNamespace(clz) + ".delete", id);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		} finally {
			MyBatisUtil.closeSession(session);
		}
	}

	public T load(Class<T> clz, int id) {
		String sqlId = getNamespace(clz) + ".load";
		return loadBySqlId(sqlId, id);
	}

	@SuppressWarnings("unchecked")
	public T loadBySqlId(String sqlId, Object obj) {
		T t = null;
		SqlSession session = null;
		try {
			session = MyBatisUtil.openSession();
			t = (T) session.selectOne(sqlId, obj);
		} finally {
			MyBatisUtil.closeSession(session);
		}
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public T loadBySqlId(String sqlId, Map<String, Object> params) {
		T t = null;
		SqlSession session = null;
		try {
			session = MyBatisUtil.openSession();
			t = (T) session.selectOne(sqlId, params);
		} finally {
			MyBatisUtil.closeSession(session);
		}
		return t;
	}

	public List<T> list(Class<T> clz, Map<String, Object> params) {
		String sqlId = getNamespace(clz) + ".list";
		return this.list(sqlId, params);
	}

	public List<T> list(String sqlId, Map<String, Object> params) {
		List<T> list = null;
		SqlSession session = null;
		try {
			session = MyBatisUtil.openSession();
			list = session.selectList(sqlId, params);
		} finally {
			MyBatisUtil.closeSession(session);
		}
		return list;
	}
	
	public Pager<T> find(Class<T> clz, Map<String, Object> params) {
		String sqlId = getNamespace(clz) + ".find";
		return this.find(sqlId, params);
	}
	
	public Pager<T> find(String sqlId,Map<String,Object> params) {
		
		int pageSize = SystemContext.getPageSize();
		int pageOffset = SystemContext.getPageOffset();
		String order = SystemContext.getOrder();
		String sort = SystemContext.getSort();
		int totalRecord = 0;
		int totalPage = 0;
		
		Pager<T> pages = new Pager<T>();
		List<T> datas = null;
		
		SqlSession session = null;
		try{
			session = MyBatisUtil.openSession();
			if(params==null) params = new HashMap<String, Object>();
			params.put("pageSize", pageSize);
			params.put("pageOffset", pageOffset);
			params.put("sort", sort);
			params.put("order", order);
			datas = session.selectList(sqlId, params);
			
			totalRecord = session.selectOne(sqlId+"_count",params); // 约定优于配置
			totalPage = (totalRecord-1)/pageSize+1;

		} finally {
			MyBatisUtil.closeSession(session);
		}
		
		pages.setDatas(datas);
		pages.setPageOffset(pageOffset);
		pages.setPageSize(pageSize);
		pages.setTotalRecord(totalRecord);
		pages.setTotalPage(totalPage);
		
		return pages;
	}
	
}


