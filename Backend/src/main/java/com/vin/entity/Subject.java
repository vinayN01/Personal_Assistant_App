package com.vin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String subject;
	
//	@Column(nullable = false)
//    private String book_id;
	
	  @Column(name = "book_id", nullable = false)
	    private String bookId; 

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

//	public String getBook_id() {
//		return book_id;
//	}
//
//	public void setBook_id(String book_id) {
//		this.book_id = book_id;
//	}
	
	public String getBookId() {
		return bookId;
	}

	public void setBookId(String book_id) {
		this.bookId = book_id;
	}

	public int getId() {
		return id;
	}

	public Subject( int id, String book_id,String subject) {
		super();
		this.subject = subject;
		this.bookId = book_id;
	}

	public Subject() {
		super();
	}
    

}
