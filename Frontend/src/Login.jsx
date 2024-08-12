
import React, { useState } from 'react';
import axiosInstance from './axiosConfig'; // Updated import
import { Link, useNavigate } from 'react-router-dom';
import './styles/Login.css'; // Import the CSS file for styling

const Login = () => {
  const [name, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      await axiosInstance.post('/login', {
        name,
        password,
      });
      navigate('/home');
    } catch (error) {
      console.error('Login failed:', error);
      setError('Login failed. Please check your credentials and try again.');
    }
  };

  return (
    <div className="login-container">
      <h2 className="login-title">Login</h2>
      <div className="login-form">
        <input
          type="text"
          placeholder="Username"
          value={name}
          onChange={(e) => setUsername(e.target.value)}
          className="login-input"
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          className="login-input"
        />
        <button onClick={handleLogin} className="login-button">Login</button>
        {error && <p className="login-error">{error}</p>}
        <Link to="/signup" className="login-link">Create an account</Link>
      </div>
    </div>
  );
};

export default Login;
