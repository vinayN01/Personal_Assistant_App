import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LandingPage from './LandingPage';
import Login from './Login';
import Signup from './Signup';
import Home from './Home';
import Notes from './Notes';
import Expenses from './Expenses';
import BookManager from './BookManager';
import ReadList from './ReadList';


const App = () => (
  <Router>
    <Routes>
      <Route path="/" element={<LandingPage />} />
      <Route path="/login" element={<Login />} />
      <Route path="/signup" element={<Signup />} />
      <Route path="/home" element={<Home />} />
      <Route path="/notes" element={<Notes />} />
      <Route path="/expense-tracker" element={<Expenses />} />
      <Route path="/book-manager" element={<BookManager />} />
      <Route path="/readlist" element={<ReadList />} />
    </Routes>
  </Router>
);

export default App;
