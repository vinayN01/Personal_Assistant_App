package com.vin.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class UserBooks {


	@Id
	@Column(nullable = false)
	    private String book_id;

	@Column(nullable = false)
	    private String title;

	    private String authorName;

	    private String coverUrl;
	    
	    private String publishedYear;

	   
	    @Column(name = "user_id", nullable = false)
	    private int userId;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getAuthorName() {
			return authorName;
		}

		public void setAuthorName(String authorName) {
			this.authorName = authorName;
		}

		public String getCoverUrl() {
			return coverUrl;
		}

		public void setCoverUrl(String coverUrl) {
			this.coverUrl = coverUrl;
		}


		public String getPublishedYear() {
			return publishedYear;
		}

		public void setPublishedYear(String publishedDate) {
			this.publishedYear = publishedDate;
		}

		public String getBook_id() {
			return book_id;
		}
		public void setBook_id(String id) {
			this.book_id = id;
		}

		public int getUserId() {
			return userId;
		}
		 public void setUserId(int user) {
				this.userId = user;
			}

	

		public UserBooks(String book_id, String title, String authorName, String coverUrl,
				String publishedYear, int userId) {
			super();
			this.book_id = book_id;
			this.title = title;
			this.authorName = authorName;
			this.coverUrl = coverUrl;
			this.publishedYear = publishedYear;
			this.userId = userId;
		}

		public UserBooks() {
			super();
		}

		}

		
	    
	   


