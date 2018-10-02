package cn.hncu.domain;

/*
create table student(
	id varchar(32) primary key,
	name varchar(20),
	age int,
	deptId varchar(32)
);
 */
public class Student {
	private String studId;
	private String studName;
	private Integer age;
	private String deptId;
	
	public Student() {
	}
	
	
	@Override
	public String toString() {
		return "Student [studId=" + studId + ", studName=" + studName + ", age=" + age + ", deptId=" + deptId + "]";
	}

	public String getStudId() {
		return studId;
	}
	public void setStudId(String studId) {
		this.studId = studId;
	}
	public String getStudName() {
		return studName;
	}
	public void setStudName(String studName) {
		this.studName = studName;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
}
