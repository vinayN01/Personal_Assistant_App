

import React, { useState, useEffect } from 'react';
import axiosInstance from './axiosConfig';
import './styles/Notes.css'; // Import the CSS file for styling

const Notes = () => {
  const [notes, setNotes] = useState([]);
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [editingNote, setEditingNote] = useState(null); // Track the note being edited

  useEffect(() => {
    const fetchNotes = async () => {
      try {
        const response = await axiosInstance.get('/notes');
        setNotes(response.data);
      } catch (error) {
        console.error('Failed to fetch notes:', error);
      }
    };
    fetchNotes();
  }, []);

  const addNote = async () => {
    try {
      await axiosInstance.post('/notes/addNote', {
        title,
        content,
      });
      setTitle('');
      setContent('');
      const response = await axiosInstance.get('/notes');
      setNotes(response.data);
    } catch (error) {
      console.error('Failed to add note:', error);
    }
  };

  const updateNote = async () => {
    if (editingNote) {
      try {
        await axiosInstance.put('/notes/update', {
          id: editingNote.id,
          title: editingNote.title, // Maintain existing title
          content, // Updated content
          createdDate: editingNote.createdDate, // Preserve createdDate
          userId: editingNote.userId // Preserve userId
        });
        setEditingNote(null);
        setTitle('');
        setContent('');
        const response = await axiosInstance.get('/notes');
        setNotes(response.data);
      } catch (error) {
        console.error('Failed to update note:', error);
      }
    }
  };

  const deleteNote = async (id) => {
    try {
      await axiosInstance.delete(`/notes/delete/${id}`);
      const response = await axiosInstance.get('/notes');
      setNotes(response.data);
    } catch (error) {
      console.error('Failed to delete note:', error);
    }
  };

  const handleEdit = (note) => {
    setEditingNote(note);
    setTitle(note.title);
    setContent(note.content);
  };

  return (
    <div className="notes-container">
      <h2>Notes</h2>
      <div className="notes-list">
        {notes.map((note) => (
          <div key={note.id} className="note-item">
            <div className="note-details">
              <h3>{note.title}</h3>
              <p>{note.content}</p>
            </div>
            <div className="note-actions">
              <button onClick={() => handleEdit(note)}>Edit</button>
              <button onClick={() => deleteNote(note.id)}>Delete</button>
            </div>
          </div>
        ))}
      </div>
      <div className="note-form">
        <input
          type="text"
          placeholder="Title"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
        />
        <textarea
          placeholder="Content"
          value={content}
          onChange={(e) => setContent(e.target.value)}
        />
        <button onClick={editingNote ? updateNote : addNote}>
          {editingNote ? 'Update Note' : 'Add Note'}
        </button>
      </div>
    </div>
  );
};

export default Notes;



