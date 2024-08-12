
import React, { useState, useEffect } from 'react';
import axiosInstance from './axiosConfig';
import './styles/Expenses.css'; // Import the CSS file for styling

const Expenses = () => {
  const [expenses, setExpenses] = useState([]);
  const [amount, setAmount] = useState('');
  const [description, setDescription] = useState('');
  const [month, setMonth] = useState('');
  const [year, setYear] = useState('');
  const [totalMonthlyExpenses, setTotalMonthlyExpenses] = useState(null);
  const [editingExpense, setEditingExpense] = useState(null);

  // Fetch expenses from the backend
  const fetchExpenses = async () => {
    try {
      const response = await axiosInstance.get('/getExpenseByUser');
      setExpenses(response.data);
    } catch (error) {
      console.error('Failed to fetch expenses:', error);
      alert('Failed to fetch expenses. Please try again later.');
    }
  };

  // Fetch expenses when the component mounts
  useEffect(() => {
    fetchExpenses();
  }, []);

  // Add or update an expense
  const saveExpense = async () => {
    try {
      if (editingExpense) {
        await axiosInstance.put('/update', {
          ...editingExpense,
          amount,
          description,
        });
      } else {
        await axiosInstance.post('/addExpense', {
          amount,
          description,
        });
      }
      setAmount('');
      setDescription('');
      setEditingExpense(null);
      fetchExpenses(); // Refresh the list of expenses
    } catch (error) {
      console.error('Failed to save expense:', error);
      alert('Failed to save expense. Please try again later.');
    }
  };

  // Delete an expense
  const deleteExpense = async (id) => {
    try {
      await axiosInstance.delete(`/delete/${id}`);
      fetchExpenses(); // Refresh the list of expenses
    } catch (error) {
      console.error('Failed to delete expense:', error);
      alert('Failed to delete expense. Please try again later.');
    }
  };

  // Start editing an expense
  const startEditing = (expense) => {
    setEditingExpense(expense);
    setAmount(expense.amount);
    setDescription(expense.description);
  };

  // Calculate monthly expenses
  const calculateMonthlyExpenses = async () => {
    if (!month || !year) {
      alert('Please enter both month and year.');
      return;
    }

    try {
      const response = await axiosInstance.get('/monthly', {
        params: { month, year }
      });
      setTotalMonthlyExpenses(response.data);
    } catch (error) {
      console.error('Failed to calculate monthly expenses:', error);
      alert('Failed to calculate monthly expenses. Please try again later.');
    }
  };

  return (
    <div className="expense-tracker-container">
      <h2>Expense Tracker</h2>
      <div className="expenses-list">
        {expenses.length > 0 ? (
          expenses.map((expense) => (
            <div key={expense.id} className="expense-item">
              <p>{expense.description}: <span>{expense.amount} Rs</span></p>
              <div className="expense-actions">
                <button onClick={() => startEditing(expense)} className="edit-button">Edit</button>
                <button onClick={() => deleteExpense(expense.id)} className="delete-button">Delete</button>
              </div>
            </div>
          ))
        ) : (
          <p>No expenses found.</p>
        )}
      </div>
      <div className="expense-form">
        <input
          type="number"
          placeholder="Amount"
          value={amount}
          onChange={(e) => setAmount(e.target.value)}
          className="input-field"
        />
        <input
          type="text"
          placeholder="Description"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          className="input-field"
        />
        <button onClick={saveExpense} className="action-button">
          {editingExpense ? 'Update Expense' : 'Add Expense'}
        </button>
      </div>
      <div className="monthly-calculation">
        <input
          type="number"
          placeholder="Month (1-12)"
          value={month}
          onChange={(e) => setMonth(e.target.value)}
          className="input-field"
        />
        <input
          type="number"
          placeholder="Year"
          value={year}
          onChange={(e) => setYear(e.target.value)}
          className="input-field"
        />
        <button onClick={calculateMonthlyExpenses} className="action-button">Calculate Monthly Expenses</button>
      </div>
      {totalMonthlyExpenses !== null && (
        <div className="total-expenses">
          <h3>Total Monthly Expenses</h3>
          <p>Rs.{totalMonthlyExpenses}</p>
        </div>
      )}
    </div>
  );
};

export default Expenses;

