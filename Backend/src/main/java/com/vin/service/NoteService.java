package com.vin.service;

import com.vin.entity.Note;
import com.vin.repo.NoteRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepo noteRepository;

    public void createNote(Note note) {
        note.setCreatedDate(LocalDateTime.now());
        note.setUpdatedDate(LocalDateTime.now());
        noteRepository.save(note);
    }

    public List<Note> getNotesByUserId(int userId) {
        return noteRepository.findByUserId(userId);
    }

    public void updateNote(Note note) {
        note.setUpdatedDate(LocalDateTime.now());
        noteRepository.save(note);
    }

    public void deleteNoteById(int id) {
        noteRepository.deleteById(id);
    }
}

