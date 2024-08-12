package com.vin.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vin.entity.Expense;

@Repository
public interface ExpenseRepo extends JpaRepository<Expense,Long> {

    List<Expense> findByUserId(int userId);
    List<Expense> findByUserIdAndDateCreatedBetween(int userId, LocalDate startDate, LocalDate endDate);
}
