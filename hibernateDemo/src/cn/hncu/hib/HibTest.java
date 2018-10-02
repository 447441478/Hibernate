package cn.hncu.hib;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import cn.hncu.domain.Person;
import cn.hncu.domain.Student;
import cn.hncu.util.HibernateUtil;

public class HibTest {
	public static void main(String[] args) {
		//save();
		query();
//		Session session1 = HibernateUtil.getSession();
//		System.out.println(session1.hashCode());
//		session1.close(); //当 session关闭后，同一个线程获取到的session应该不同。 
//		
//		Session session2 = HibernateUtil.getSession();
//		System.out.println(session2.hashCode());
		
	}
	
	@Test //测试注解
	public void t1() {
		Person person = new Person();
		person.setId("p001");
		person.setName("rose");
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		session.save(person);
		
		tx.commit();
		session.close();
	}
	//查询所有student记录
	public static void query() {
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery("from Student");
		List list = query.list();
		System.out.println(list);
	}
	//插入一条记录
	public static void save() {
		Student student = new Student();
		student.setStudId("s003");
		student.setStudName("马克");
		student.setAge(22);
		student.setDeptId("d002");
		
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		session.save(student);
		
		tx.commit();
		session.close();
	}
}	
