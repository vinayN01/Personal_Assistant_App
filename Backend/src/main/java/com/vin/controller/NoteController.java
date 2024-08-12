package com.vin.controller;

import com.vin.entity.Note;
import com.vin.service.NoteService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("/addNote")
    public String createNote(@RequestBody Note note,HttpSession session) {
    	int userId = (int) session.getAttribute("userId");
    	note.setUserId(userId);
        noteService.createNote(note);
        return "Note created successfully";
    }

    @GetMapping
    public List<Note> getNotes(HttpSession session) {
    	int userId = (int) session.getAttribute("userId");
        return noteService.getNotesByUserId(userId);
    }

    @PutMapping("/update")
    public String updateNote(@RequestBody Note note) {
        noteService.updateNote(note);
        return "Note updated successfully";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteNote(@PathVariable int id) {
        noteService.deleteNoteById(id);
        return "Note deleted successfully";
    }
}
