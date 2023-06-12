import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import axios from 'axios';

export const Register = () => {
  const [data, setData] = useState({
    login: '',
    email: '',
    password: '',
  });

  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleChange = ({ currentTarget: input }) => {
    setData({ ...data, [input.name]: input.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const url = 'http://localhost:8080/api/auth/register';
      const { data: res } = await axios.post(url, data);
      navigate('/login');
      console.log(res.token);
    } catch (error) {
      if (error.status && error.status >= 400 && error.status <= 500) {
        setError(error.response.data.message);
      }
    }
  };
  return (
    <div className="flex h-screen flex-col items-center justify-center gap-9 bg-gray-50 px-5 py-6">
      <div className="w-1/2">
        <h1 className="flex w-full justify-center text-5xl font-semibold text-sky-500">Data Analysis Webapp</h1>
        <p className="mt-2 flex w-full justify-center text-2xl text-gray-500">Alcohol usage and sucide rate correlation analysis</p>
      </div>

      <div className=" w-2/5 max-w-sm  rounded-lg bg-gray-100 p-5 border-gray-200/30 border">
        <h1 className=" w-full text-2xl font-semibold text-gray-700">Sign in</h1>
        <form className="flex w-full flex-col items-center" onSubmit={handleSubmit}>
          <div className="mt-3 w-full">
            <label className="label ">
              <span className="label-text text-gray-400" onChange={handleChange}>
                Login
              </span>
            </label>
            <input type="text" placeholder="Type here" className=" input w-full max-w-sm text-gray-800 bg-gray-200 placeholder:text-gray-600" name="login" onChange={handleChange} required />
          </div>

          <div className="mt-3 w-full">
            <label className="label ">
              <span className="label-text text-gray-400" onChange={handleChange}>
                Email
              </span>
            </label>
            <input type="email" placeholder="Type here" className=" input w-full max-w-sm bg-gray-200 placeholder:text-gray-600 text-gray-800" name="email" onChange={handleChange} required />
          </div>

          <div className="mt-3 w-full">
            <label className="label">
              <span className="label-text text-gray-400" onChange={handleChange}>
                Password
              </span>
            </label>
            <input type="password" placeholder="********" className=" input w-full max-w-sm bg-gray-200 placeholder:text-gray-600 text-gray-800" name="password" onChange={handleChange} required />
          </div>

          <button type="submit" className="btn-primary btn mt-9 w-full border-0 bg-sky-600 hover:bg-sky-700">
            Sign in
          </button>
        </form>

        <Link to={'/login'}>
          <h1 className="font-base mt-7 text-center text-sm font-light text-gray-600">
            Already have an accout?<span className="font-semibold"> Log in</span>
          </h1>
        </Link>
      </div>
    </div>
  );
};
