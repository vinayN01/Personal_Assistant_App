package com.vin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vin.entity.Subject;
import com.vin.entity.UserBooks;
import com.vin.repo.BookRepo;
import com.vin.repo.SubjectRepo;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookService {

    private static final String OPEN_LIBRARY_API_URL = "https://openlibrary.org/search.json?title=";
    private static final String OPEN_LIBRARY_API_URL_ID = "https://openlibrary.org/";
    
    @Autowired
    private BookRepo bookr;
    @Autowired
    private SubjectRepo subR;
    private final RestTemplate rest = new RestTemplate();

    
    public List<Map<String,Object>> searchBooks(String query) {
    	String formattedQuery = query.replace(" ","+");
    	Map<String,Object> response = rest.getForObject(OPEN_LIBRARY_API_URL+ formattedQuery, Map.class);
    	if(response == null || !response.containsKey("docs")) {
    		return new ArrayList<>();
    	}
    	List<Map<String,Object>> docs = (List<Map<String, Object>>) response.get("docs");
    	List<Map<String,Object>> limitedDocs = new ArrayList<>();
    	
    	for(int i=0;i< Math.min(docs.size(), 20); i++) {
    	     Map<String, Object> book = docs.get(i);

    	        String bookId = book.get("key") != null ? book.get("key").toString() : "Unknown";
    	        String title = book.get("title") != null ? book.get("title").toString() : "Unknown";
    	        String authorName = (book.get("author_name") != null && ((List<String>) book.get("author_name")).size() > 0) 
    	                            ? ((List<String>) book.get("author_name")).get(0) 
    	                            : "Unknown";
    	        String coverUrl = book.get("cover_i") != null 
    	                            ? "https://covers.openlibrary.org/b/id/" + book.get("cover_i") + "-L.jpg" 
    	                            : "No Image";
//    	        String publishedYear = book.get("first_publish_year") != null ? book.get("first_publish_year").toString() : "Unknown";
//    	        List<String> subjects = getSubjectForBooks(book);

    	        Map<String, Object> bookDetails = new HashMap<>();
    	        bookDetails.put("book_id", bookId);
    	        bookDetails.put("title", title);
    	        bookDetails.put("authorName", authorName);
    	        bookDetails.put("coverUrl", coverUrl);
//    	        bookDetails.put("publishedYear", publishedYear);
//    	        bookDetails.put("subject", subjects);

    	        limitedDocs.add(bookDetails);
    	    }
    	return limitedDocs;
    }
    


    public void addBook(String id, int userId) {
        String url = OPEN_LIBRARY_API_URL_ID + id + ".json";
        Map<String, Object> response = rest.getForObject(url, Map.class);

        String title = response.get("title") != null ? response.get("title").toString() : "Unknown";

        String authorName = "Unknown";
        if (response.containsKey("authors")) {
            List<Map<String, Object>> authorsList = (List<Map<String, Object>>) response.get("authors");
            if (!authorsList.isEmpty()) {
                Map<String, Object> author = authorsList.get(0);
                if (author.containsKey("author")) {
                    Map<String, Object> authorDetails = (Map<String, Object>) author.get("author");
                    String authorKey = authorDetails.get("key") != null ? authorDetails.get("key").toString() : "";
                    
                    // Fetch author details to get the name
                    if (!authorKey.isEmpty()) {
                        String authorUrl = "https://openlibrary.org" + authorKey + ".json";
                        Map<String, Object> authorResponse = rest.getForObject(authorUrl, Map.class);
                        authorName = authorResponse != null && authorResponse.get("name") != null 
                                        ? authorResponse.get("name").toString() 
                                        : "Unknown";
                    }
                }
            }
        }
        
        // Covers (handling array of cover IDs)
        List<Integer> covers = (List<Integer>) response.get("covers");
        String coverUrl = "No Image";
        if (covers != null && !covers.isEmpty()) {
            coverUrl = "https://covers.openlibrary.org/b/id/" + covers.get(0) + "-L.jpg";
        }
        

        // Published Year
        String publishedYear = response.get("created") != null 
                ? ((Map<String, Object>) response.get("created")).get("value").toString() 
                : "Unknown";

        // Subjects
        List<String> subjects = getSubjectForBook(response);
        subjects.forEach(subject -> {
            Subject sub = new Subject();
            sub.setBookId(id);
            sub.setSubject(subject);
            subR.save(sub);
        });


        

        // Create UserBooks object and save it
        UserBooks userBook = new UserBooks();
        userBook.setBook_id(id);
        userBook.setTitle(title);
        userBook.setAuthorName(authorName);
        userBook.setCoverUrl(coverUrl);
        userBook.setPublishedYear(publishedYear);
        userBook.setUserId(userId);

        bookr.save(userBook);
    }
    
    
    public Map<String, Object> getBookById(String id,int userId){
    	   String url = OPEN_LIBRARY_API_URL_ID + id + ".json";
           Map<String, Object> response = rest.getForObject(url, Map.class);

           String title = response.get("title") != null ? response.get("title").toString() : "Unknown";

           String authorName = "Unknown";
           if (response.containsKey("authors")) {
               List<Map<String, Object>> authorsList = (List<Map<String, Object>>) response.get("authors");
               if (!authorsList.isEmpty()) {
                   Map<String, Object> author = authorsList.get(0);
                   if (author.containsKey("author")) {
                       Map<String, Object> authorDetails = (Map<String, Object>) author.get("author");
                       String authorKey = authorDetails.get("key") != null ? authorDetails.get("key").toString() : "";
                       
                       // Fetch author details to get the name
                       if (!authorKey.isEmpty()) {
                           String authorUrl = "https://openlibrary.org" + authorKey + ".json";
                           Map<String, Object> authorResponse = rest.getForObject(authorUrl, Map.class);
                           authorName = authorResponse != null && authorResponse.get("name") != null 
                                           ? authorResponse.get("name").toString() 
                                           : "Unknown";
                       }
                   }
               }
           }
           
           // Covers (handling array of cover IDs)
           List<Integer> covers = (List<Integer>) response.get("covers");
           String coverUrl = "No Image";
           if (covers != null && !covers.isEmpty()) {
               coverUrl = "https://covers.openlibrary.org/b/id/" + covers.get(0) + "-L.jpg";
           }
           

           // Published Year
           String publishedYear = response.get("created") != null 
                   ? ((Map<String, Object>) response.get("created")).get("value").toString() 
                   : "Unknown";

           // Subjects
           List<String> subjects = getSubjectForBook(response);
           Map<String, Object> bookDetails = new HashMap<>();
	        bookDetails.put("book_id", id);
	        bookDetails.put("title", title);
	        bookDetails.put("authorName", authorName);
	        bookDetails.put("coverUrl", coverUrl);
	        bookDetails.put("publishedYear", publishedYear);
	        bookDetails.put("subject", subjects);

               return bookDetails;           
    }

    
    public List<Map<String,Object>> getReadListBooks(int userId){
    	
    	List<UserBooks> data = bookr.findByUserId(userId);
        List<Map<String, Object>> result = new ArrayList<>();
        
        for (UserBooks book : data) {
            if (book != null) {
                String book_id = book.getBook_id();
                List<Subject> subjects = subR.findByBookId(book_id);
                
                // Creating a map to store the book details along with subjects
                Map<String, Object> bookDetails = new HashMap<>();
                bookDetails.put("book_id", book.getBook_id());
                bookDetails.put("title", book.getTitle());
                bookDetails.put("authorName", book.getAuthorName());
                bookDetails.put("coverUrl", book.getCoverUrl());
                bookDetails.put("publishedYear", book.getPublishedYear());
                
                // Adding the subjects to the map
                List<String> subjectNames = subjects.stream()
                                                    .map(Subject::getSubject)
                                                    .collect(Collectors.toList());
                bookDetails.put("subjects", subjectNames);
                
                // Adding the map to the result list
                result.add(bookDetails);
            }
        }
        
        return result;
    }

public List<String>  getSubjectForBooks(Map<String,Object> book) {
	List<String> subjects = (List<String>) book.get("subject");
	if(subjects!=null && !subjects.isEmpty()) {
		int maxSubjects = Math.min(subjects.size(), 5);
		List<String> limitedStrings = subjects.subList(0, maxSubjects);
		return limitedStrings;
	}
	return new ArrayList<>();
	
}
public List<String>  getSubjectForBook(Map<String,Object> book) {
	List<String> subjects = (List<String>) book.get("subjects");
	if(subjects!=null && !subjects.isEmpty()) {
		int maxSubjects = Math.min(subjects.size(), 5);
		List<String> limitedStrings = subjects.subList(0, maxSubjects);
		return limitedStrings;
	}
	return new ArrayList<>();
	
}
}


