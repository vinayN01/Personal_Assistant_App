
import React, { useState, useEffect } from 'react';
import axiosInstance from './axiosConfig';
import './styles/ReadList.css'; // Import the CSS file for styling

const ReadList = () => {
  const [readList, setReadList] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchReadList = async () => {
      try {
        const response = await axiosInstance.get('/getReadListBooks');
        setReadList(response.data);
      } catch (error) {
        console.error('Failed to fetch read list:', error);
      } finally {
        setLoading(false);
      }
    };
    fetchReadList();
  }, []);

  return (
    <div className="readlist-container">
      <h2>Read List</h2>
      {loading ? (
        <p>Loading...</p>
      ) : readList.length > 0 ? (
        <div className="readlist-cards-container">
          {readList.map((book) => (
            <div key={book.book_id} className="readlist-card">
              <img src={book.coverUrl} alt={book.title} className="readlist-cover" />
              <div className="readlist-info">
                <h3>{book.title}</h3>
                <p>Author: {book.authorName}</p>
                <p>Published Year: {book.publishedYear}</p>
                <p>Subjects: {book.subjects.join(', ')}</p>
              </div>
            </div>
          ))}
        </div>
      ) : (
        <p>No books in your read list.</p>
      )}
    </div>
  );
};

export default ReadList;





