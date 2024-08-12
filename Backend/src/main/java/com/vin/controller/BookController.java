package com.vin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vin.service.BookService;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/searchBooks")
    public List<Map<String,Object>> searchBooks(@RequestParam String query) {
        return  bookService.searchBooks(query);
    }
    
    @GetMapping("/getReadListBooks")
    public List<Map<String,Object>> getReadListBooks(HttpSession session) {
    	int userId = (int) session.getAttribute("userId");
        return  bookService.getReadListBooks(userId);
    }
    
    @PostMapping("/addBookToReadList")
    public String addBook(@RequestParam String book_id, HttpSession session) {
    	int userId = (int) session.getAttribute("userId");
         bookService.addBook(book_id,userId);
         return "Success";
    }
    
    @PostMapping("/getBookById")
    public Map<String,Object> getBookById(@RequestParam String book_id, HttpSession session) {
    	int userId = (int) session.getAttribute("userId");
         return bookService.getBookById(book_id,userId);
    }
}
