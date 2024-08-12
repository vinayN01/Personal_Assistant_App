package com.vin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vin.entity.Expense;
import com.vin.service.ExpenseService;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class ExpenseController {
	
	@Autowired
	ExpenseService expS;
	
	@PostMapping("/addExpense")
	public void addExpense(@RequestBody Expense expense , HttpSession session) {
		int userId = (int) session.getAttribute("userId");
		expense.setUserId(userId);
		expS.addExpense(expense);
		
	}
	
	@GetMapping("/getExpenseByUser")
	public List<Expense> getExpenseByUser(HttpSession session) {
		int userId = (int) session.getAttribute("userId");
		return expS.getExpenseByUser(userId);
		
	}


	    @GetMapping("/monthly")
	    public Double getMonthlyExpenses(
	            @RequestParam int month,
	            @RequestParam int year, HttpSession session) {
			int userId = (int) session.getAttribute("userId");
	        return expS.getMonthlyExpenses(userId, month, year);
	    }
	    
	    @PutMapping("/update")
	    public String updateExpense(@RequestBody Expense expense) {
	         expS.update(expense);
	         return "Note updated successfully";
	    }
	    
	    @DeleteMapping("/delete/{id}")
	    public String deleteExpense(@PathVariable Long id) {
	         expS.delete(id);
	         return "Note deleted successfully";
	    }

}
