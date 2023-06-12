import React from 'react';
import { Link } from 'react-router-dom';
import { useState } from 'react';
import axios from 'axios';

export const Login = () => {
  const [invalidCreditentials, setInvalidCreditentials] = useState();
  const [data, setData] = useState({ login: '', password: '' });
  const [error, setError] = useState('');

  const handleChange = ({ currentTarget: input }) => {
    setData({ ...data, [input.name]: input.value });
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const url = 'http://localhost:8080/api/auth/login';
      const { data: res } = await axios.post(url, data);
      localStorage.setItem('token', res.token);
      console.log(res.token);
      window.location = '/';
    } catch (error) {
      if (error.status && error.status >= 400 && reponse <= 500) {
        setError(error.response.data.message);
      }
    }
  };

  return (
    <div className=" h-screen items-center flex flex-col justify-center gap-9 bg-gray-50 px-5 py-6">
      <div className="w-1/2">
        <h1 className="flex w-full justify-center text-5xl font-semibold text-sky-500">Data Analysis Webapp</h1>
        <p className="mt-2 flex w-full justify-center text-2xl text-gray-500">Alcohol usage and sucide rate correlation analysis</p>
      </div>

      <div className=" w-2/5 max-w-sm  rounded-lg bg-gray-100 p-5  border-gray-200/30 border">
        <form className="flex w-full flex-col items-center" onSubmit={handleSubmit}>
          <h1 className=" w-full text-2xl font-semibold text-gray-700">Log in</h1>
          <div className="mt-3 w-full">
            <label className="label ">
              <span className="label-text text-gray-400">Login</span>
            </label>
            <input type="text" placeholder="Type here" className="input  w-full max-w-sm bg-gray-200  placeholder:text-gray-600 text-gray-800" name="login" onChange={handleChange} required />
          </div>
          <div className="mt-3 w-full">
            <label className="label">
              <span className="label-text text-gray-400">Password</span>
            </label>
            <input type="password" placeholder="********" className="input  w-full max-w-sm bg-gray-200 placeholder:text-gray-600 text-gray-800" name="password" onChange={handleChange} required />
          </div>
          <button type="submit" className="btn-primary btn mt-9 w-full border-0 bg-sky-600 hover:bg-sky-700">
            Log in
          </button>
        </form>
        <Link to={'/register'}>
          <h1 className="mt-7 text-center text-sm font-light text-gray-600">
            Don't have account yet? <span className="font-semibold">Register</span>
          </h1>
        </Link>
      </div>
    </div>
  );
};
