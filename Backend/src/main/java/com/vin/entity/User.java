package com.vin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
@Id	
@GeneratedValue(strategy = GenerationType.IDENTITY)
 private int id;

@Column(nullable = false)
 private String name;

@Column(nullable = false)
 private String password;

public int getId() {
	return id;
}

public User() {
	super();
}

public User(int id, String name, String password) {
	super();
	this.id = id;
	this.name = name;
	this.password = password;
}

public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}


 
}
