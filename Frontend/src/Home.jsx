// import React from 'react';
// import { Link } from 'react-router-dom';

// const Home = () => (
//   <div>
//     <h2>Home</h2>
//     <Link to="/notes">Notes</Link>
//     <Link to="/expense-tracker">Expense Tracker</Link>
//     <Link to="/book-manager">Book Manager</Link>
//   </div>
// );

// export default Home;

import React from 'react';
import { Link } from 'react-router-dom';
import './styles/Home.css'; // Import the CSS file for styling

const Home = () => (
  <div className="home-container">
    <h1 className="home-title">Welcome to Your Personal Assistant</h1>
    <div className="home-links">
      <Link to="/notes" className="home-link">Notes</Link>
      <Link to="/expense-tracker" className="home-link">Expense Tracker</Link>
      <Link to="/book-manager" className="home-link">Book Manager</Link>
    </div>
  </div>
);

export default Home;
