package cn.hncu.hib;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import cn.hncu.domain.Student;
import cn.hncu.util.HibernateUtil;

public class HibCRUD {
	/*
	 * 经测试发现，openSession获得到的 session在查询时可以不用开启事务，
	 * 而 currentSession在查询时必须要开启事务，并且在事务提交后会自动关闭session。
	 * 
	 * openSession：每次获取到的都是新新的，所以为实现同currentSession一样单线程共享，
	 * 				所以，我在HibernateUtil工具类中使用ThreadLocal线程局部变量容器。
	 * 
	 * currentSession：只要当前线程中的session没有提交事务，后面获得到的都是同一个。
	 * 
	 */

	// 使瞬时态的对象 转化成 持久态 
	@Test
	public void save() {
		//Student student = new Student("S010","张三",23,"D004");
		//Student student = new Student("S011","张飞",33,"D005");
		Student student = new Student("S077","张飞",33,"D005");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		System.out.println("11111111");
		//session.save(student); //插入数据库 --- 非延迟加载主键，但是延迟查询
		session.persist(student); //插入数据库---延迟加载主键
		
		/* 由于前面存储了，所以 student对象处于persist状态，受session管理，
		 * 所以当student对象调用 setXXX方法时会发起修改的数据库操作。
		 */
		student.setAge(66); 
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		tx.commit();
	}
	@Test
	public void update() {
		Student student = new Student("S010","张-三",12,"D006");
		//Student student = new Student("S011","李三",16,"D006");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		
		session.saveOrUpdate(student); //添加或者修改
		//session.merge(student); //修改
		
		tx.commit();
	}

	/* transient：没有 id值的对象
	 * persistent： 有id值的对象，且受session管理
	 * detached：有id值的对象，但是不受session管理
	 */
	
	//删除 就是把 持久态-->托管态
	@Test
	public void delet() {
		//Student student = new Student("S010","sssad-三",0,"0");
		Student student = new Student(null,"sssad-三",0,"0"); //主键没有值是，认为该实体是空
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		
		session.delete(student); //删除，根据对象的主键去删除
		
		tx.commit();
	}
	
	@Test
	public void querySingle() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		
		Student student = session.get(Student.class, "s002");
		System.out.println(student);
		
		tx.commit();
		
	}
	@Test
	public void queryAll() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		
		String hql = "from Student";
		Query query = session.createQuery(hql);
		List<?> list = query.list();
		System.out.println( list );
		
		tx.commit();
		
	}
	
	@Test 
	public void qeuryByCondition() {
		Student student = new Student();
//		student.setStudId("s002");
//		student.setAge(20);
		student.setStudName("张");
//		student.setDeptId("D002");
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		//用flag的各位二进制数 判断该字段是否有条件
		int flag=0;
		String hql = "from Student s where 1=1 ";
		//进行按位或运算
		if( student.getStudId() != null ) {
			hql += "and s.studId=:studId ";
			flag |= 1; //2^0
		}
 		if( student.getStudName() != null ) {
 			hql += "and s.studName like :studName ";
			flag |= 2; //2^1
 		}
 		if( student.getAge() != null ) {
 			hql += "and s.age>:age ";
			flag |= 4; //2^2
 		}
 		if( student.getDeptId() != null ) {
 			hql += "and s.deptId=:deptId";
 			flag |= 8; //2^3
 		}
 		System.out.println(hql);
 		Query query = session.createQuery(hql);
 		
 		//进行按位与运算
 		if( (flag&1) == 1 ) { //2^0
 			query.setParameter("studId", student.getStudId() );
 		}
 		if( (flag&2) == 2 ) { //2^1
 			query.setParameter("studName", "%"+student.getStudName()+"%" );
 		}
 		if( (flag&4) == 4 ) { //2^2
 			query.setParameter("age", student.getAge() );
 		}
 		if( (flag&8) == 8 ) { //2^3
 			query.setParameter("deptId", student.getDeptId() );
 		}
 		List<?> list = query.list();
 		System.out.println( list );
		
		tx.commit();
	}
	
}
