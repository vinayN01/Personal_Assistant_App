package com.vin.repo;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vin.entity.UserBooks;

@Repository
public interface BookRepo extends JpaRepository<UserBooks,String>{
	List<UserBooks> findByUserId(int userId);

}
