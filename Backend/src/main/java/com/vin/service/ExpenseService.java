package com.vin.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vin.entity.Expense;
import com.vin.repo.ExpenseRepo;

@Service
public class ExpenseService {
	
   @Autowired
   ExpenseRepo expR;
   
	public void addExpense(Expense expense) {
		expR.save(expense);
	}

	public List<Expense> getExpenseByUser(int userId) {
       List<Expense> expenses = expR.findByUserId(userId);
       return expenses;
       
	}

	  public Double getMonthlyExpenses(int userId, int month, int year) {
	        YearMonth yearMonth = YearMonth.of(year, month);
	        LocalDate startDate = yearMonth.atDay(1);
	        LocalDate endDate = yearMonth.atEndOfMonth();
	        List<Expense> expenses = expR.findByUserIdAndDateCreatedBetween(userId, startDate, endDate);
	        return expenses.stream().mapToDouble(Expense::getAmount).sum();
	    }

	public void update(Expense expense) {
		expR.save(expense);
	}

	public void delete(Long id) {
		expR.deleteById(id);
	}
}
