
import React from 'react';
import { Link } from 'react-router-dom';
import './styles/LandingPage.css'; // Import the CSS file for styling

const LandingPage = () => (
  <div className="landing-container">
    <h1 className="landing-title">Welcome to the Personal Assistant Application</h1>
    <p className="landing-description">Manage your notes, expenses, and books with ease.</p>
    <div className="landing-links">
      <Link to="/login" className="landing-link">Login</Link>
      <span className="landing-divider">|</span>
      <Link to="/signup" className="landing-link">Sign Up</Link>
    </div>
  </div>
);

export default LandingPage;
