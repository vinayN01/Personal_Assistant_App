package com.vin.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vin.entity.Note;

public interface NoteRepo extends JpaRepository<Note, Integer> {
    List<Note> findByUserId(int userId);

}
