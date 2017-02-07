package com.sea.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import com.sea.dao.BaseDao;

@SuppressWarnings("unchecked")
@Component
public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T>{
	private Class<T> cls;
	@Resource(name="sessionFactory")
	public void superSessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public void save(T t) {
		this.getHibernateTemplate().save(t);
	}

	@Override
	public void update(T t) {
		this.getHibernateTemplate().saveOrUpdate(t);
	}

	@Override
	public void delete(T t) {
		this.getHibernateTemplate().delete(t);
	}
	
	@Override
	public T load(int id) {
		return this.getHibernateTemplate().get(getCls(), id);
	}

	@Override
	public List<T> list(String hql) {
		return this.getHibernateTemplate().find(hql);
	}

	@Override
	public List<T> list(String hql, Object arg) {
		return this.getHibernateTemplate().find(hql, arg);
	}

	@Override
	public List<T> list(String hql, Object[] args) {
		Query q = this.getSessionFactory().getCurrentSession().createQuery(hql); //this.getSessionFactory().openSession().createQuery(hql);
		for(int i=0;i<=args.length;i++){
			q.setParameter(i, args[i]);
		}
		return q.list();
	}
	
	public Class<T> getCls() {
		if(cls==null){
			cls= (Class<T>) (((ParameterizedType)(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]);
		}
		return cls;
	}
	
	@Override
	public List<T> queryForPage(final String hql,int startRows,int pageSize) {
		final int fstartRows=startRows;
		final int fpageSize=pageSize;
		List<T> list = this.getHibernateTemplate().execute(new HibernateCallback<List<T>>(){
			 public List<T> doInHibernate(Session session) throws HibernateException,SQLException{
                Query query = session.createQuery(hql);
                query.setFirstResult(fstartRows);
                query.setMaxResults(fpageSize);
                List<T> list = query.list();
                return list;
	            }
	        });
        return list;
	}

	@Override
	public int getAllRowCount(String hql) {
		return this.getHibernateTemplate().find(hql).size();
	}
	
	
}
