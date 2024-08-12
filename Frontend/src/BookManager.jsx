
import React, { useState } from 'react';
import axiosInstance from './axiosConfig';
import './styles/BookManager.css'; // Import the CSS file for styling

const BookManager = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [books, setBooks] = useState([]);
  const [selectedBook, setSelectedBook] = useState(null);

  // Search for books based on the search term
  const searchBooks = async () => {
    try {
      const response = await axiosInstance.get('/searchBooks', {
        params: { query: searchTerm },
      });
      setBooks(response.data);
      setSelectedBook(null); // Clear selected book when searching
    } catch (error) {
      console.error('Failed to search books:', error);
      alert('Failed to search books. Please try again later.');
    }
  };

  // Fetch details of a selected book
  const viewBookDetails = async (bookId) => {
    try {
      const response = await axiosInstance.post('/getBookById', null, {
        params: { book_id: bookId },
      });
      setSelectedBook(response.data);
    } catch (error) {
      console.error('Failed to fetch book details:', error);
      alert('Failed to fetch book details. Please try again later.');
    }
  };

  // Add a book to the readlist
  const addBookToReadList = async () => {
    if (selectedBook) {
      try {
        await axiosInstance.post('/addBookToReadList', null, {
          params: { book_id: selectedBook.book_id },
        });
        alert('Book added to readlist');
        setSelectedBook(null); // Clear selected book after adding
      } catch (error) {
        console.error('Failed to add book to readlist:', error);
        alert('Failed to add book to readlist. Please try again later.');
      }
    }
  };

  // Navigate to the read list
  const goToReadList = () => {
    window.location.href = '/readlist'; // Assuming you have a route for the read list
  };

  return (
    <div className="book-manager-container">
      {!selectedBook ? (
        <div>
          <h2>Book Manager</h2>
          <div className="search-container">
            <input
              type="text"
              placeholder="Search for books"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
            <button onClick={searchBooks}>Search</button>
            <button className="readlist-button" onClick={goToReadList}>View Read List</button>
          </div>
          <div className="book-cards-container">
            {books.map((book) => (
              <div key={book.book_id} className="book-card">
                <img src={book.coverUrl} alt={book.title} className="book-cover" />
                <div className="book-info">
                  <h3>{book.title}</h3>
                  <p>{book.authorName}</p>
                  <button onClick={() => viewBookDetails(book.book_id)}>View Details</button>
                </div>
              </div>
            ))}
          </div>
        </div>
      ) : (
        <div className="book-details">
          <h3>{selectedBook.title}</h3>
          <p>{selectedBook.authorName}</p>
          <p><strong>Published Date:</strong> {new Date(selectedBook.publishedYear).toLocaleDateString()}</p>
          <img src={selectedBook.coverUrl} alt={selectedBook.title} className="book-cover-large" />
          <p><strong>Subjects:</strong> {selectedBook.subject.slice(0, 5).join(', ')}</p>
          <button onClick={addBookToReadList}>Add to Readlist</button>
          <button onClick={() => setSelectedBook(null)}>Go Back</button>
        </div>
      )}
    </div>
  );
};

export default BookManager;
