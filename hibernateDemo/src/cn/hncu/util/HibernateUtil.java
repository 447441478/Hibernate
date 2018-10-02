package cn.hncu.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
/**
 * &emsp;&emsp;Hibernate框架加载工具
 * <br/><br/><b>CreateTime:</b><br/>&emsp;&emsp;&emsp; 2018年10月2日 下午4:39:59	
 * @author 宋进宇&emsp;<a href='mailto:447441478@qq.com'>447441478@qq.com</a>
 */
public class HibernateUtil {
	private static SessionFactory sessionFactory;
	//为实现单个线程共享单个session
	private static ThreadLocal<Session> tlPool; 
	static {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure() // configures settings from hibernate.cfg.xml
				.build();
		try {
			sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
			tlPool = new ThreadLocal<Session>();
		}
		catch (Exception e) {
			// The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
			// so destroy it manually.
			StandardServiceRegistryBuilder.destroy( registry );
			throw new RuntimeException(e);
		}
	}
	/**
	 * 获取 SessionFactory 对象
	 * @return SessionFactory 对象
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	/**
	 * 获取一个 Session 对象
	 * @return Session 对象
	 */
	public static Session getSession() {
		Session session = tlPool.get();
		if( session == null || !session.isOpen()) {
			session = sessionFactory.openSession();
			tlPool.set(session);
		}
		return session;
	}
	
}
