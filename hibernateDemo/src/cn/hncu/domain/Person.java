package cn.hncu.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/*
CREATE TABLE person(
	id VARCHAR(32) PRIMARY KEY ,
	NAME VARCHAR(32)
);
 */
@Entity
@Table(name="person")
public class Person {
	private String id;
	private String name;
	
	public Person() {
		super();
	}
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
