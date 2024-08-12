// src/axiosConfig.js

import axios from 'axios';

// Create an Axios instance with default configuration
const axiosInstance = axios.create({
  baseURL: 'http://localhost:8080/api', // Your base URL here
  withCredentials: true, // Ensure cookies are sent with requests
});

export default axiosInstance;
