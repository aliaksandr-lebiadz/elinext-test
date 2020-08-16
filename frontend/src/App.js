import React, { useEffect, useState } from 'react';
import { LoginPage, ReservationPage } from './components';
import './App.scss';

const App = () => {
  const initialTokenValue = localStorage.getItem('token');
  const [token, setToken] = useState(initialTokenValue);

  useEffect(() => {
    localStorage.setItem('token', token);
  }, [token]);

  return token === null ? <LoginPage setToken={setToken} /> : <ReservationPage token={token} />;
};

export default App;
