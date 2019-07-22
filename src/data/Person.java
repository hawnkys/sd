package data;

import java.io.Serializable;
import java.util.Date;

public class Person implements Serializable{
	private static final long serialVersionUID = 1L;
	
	int id;
	String username; 
	String pw; 
	String job;
	String address;
	String college;
	String department;
	int telephone;
	int cc_number;
	Date cc_date;
	
	public Person() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getTelephone() {
		return telephone;
	}

	public void setTelephone(int telephone) {
		this.telephone = telephone;
	}

	public int getCc_number() {
		return cc_number;
	}

	public void setCc_number(int cc_number) {
		this.cc_number = cc_number;
	}

	public int getCC_number() {
		return cc_number;
	}

	public void setCC_number(int cc_number) {
		this.cc_number = cc_number;
	}

	public Date getCC_date() {
		return cc_date;
	}

	public void setCC_date(Date cc_date) {
		this.cc_date = cc_date;
	}

	public Date getCc_date() {
		return cc_date;
	}

	public void setCc_date(Date cc_date) {
		this.cc_date = cc_date;
	}
}
