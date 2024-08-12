package com.vin.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vin.entity.Subject;
import com.vin.entity.UserBooks;

@Repository
public interface SubjectRepo extends JpaRepository<Subject, Integer> {
	List<Subject> findByBookId(String bookId); 
}
