import { useState } from 'react';
import reactLogo from './assets/react.svg';
import './App.css';
import { useEffect } from 'react';
import { Route, Routes, Navigate, useNavigate } from 'react-router-dom';
import { Home } from './components/pages/Home';
import { Register } from './components/pages/Register';
import { Login } from './components/pages/Login';

function App() {
  const user = localStorage.getItem('token');
  const navigate = useNavigate();

  useEffect(() => {
    if (!user && window.location.pathname !== '/register') {
      navigate('/login');
    }
  }, [user, navigate]);

  return (
    <Routes>
      {user && <Route path="/" exact element={<Home />} />}
      <Route path="/register" exact element={<Register />} />
      <Route path="/login" exact element={<Login />} />
    </Routes>
  );
}

export default App;
