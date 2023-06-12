import React from 'react';
import axios from 'axios';
import { useEffect, useState } from 'react';
import { addTokenToRequestHeader } from '../../helpers/addTokenToRequestHeader';
import { AreaChart, Area, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';
import { format, parseISO } from 'date-fns';

export const Home = () => {
  const countries = ['ARG', 'AUT', 'BEL', 'BGR', 'BRA', 'CAN', 'CHE', 'CHL', 'CHN', 'COL', 'CRI', 'CZE', 'DEU', 'DNK', 'ESP', 'EST', 'FIN', 'FRA', 'GBR', 'GRC', 'HRV', 'HUN', 'IDN', 'IND', 'IRL', 'ISL', 'ISR', 'ITA', 'JPN', 'KOR', 'LTU', 'LUX', 'LVA', 'MEX', 'NLD', 'NOR', 'NZL', 'PER', 'POL', 'PRT', 'ROU', 'RUS', 'SVK', 'SVN', 'SWE', 'TUR', 'USA', 'ZAF'];

  const handleLogout = () => {
    localStorage.removeItem('token');
    window.location.reload();
  };

  const handleCountryChange = (e) => {
    setCountry(e.target.value);
  };

  const handleFilterChange = (e) => {
    setFilter(e.target.value);
  };

  const [isLoading, setIsLoading] = useState(true);
  const [suicideData, setSuicideData] = useState();
  const [alcoholData, setAlcoholData] = useState();
  const [country, setCountry] = useState('POL');
  const [filter, setFilter] = useState('TOT');

  useEffect(() => {
    let endpoints = [`http://localhost:8080/api/suicide/country/${country}?subject=${filter}`, `http://localhost:8080/api/alcohol/country/${country}?subject=TOT`];

    const headers = addTokenToRequestHeader();

    const fetchData = async () => {
      try {
        const dataPromises = endpoints.map(async (endpoint) => {
          const response = await axios.get(endpoint, { headers });
          return response.data;
        });

        const responseData = await Promise.all(dataPromises);
        setSuicideData(responseData[0]);
        setAlcoholData(responseData[1]);
        setIsLoading(false);
      } catch (error) {
        console.log(error);
      }
    };

    fetchData();
  }, [country, filter]);

  let transformedSuicidesArray = [];
  let transformedAlcoholArray = [];

  if (!isLoading && suicideData) {
    transformedSuicidesArray = suicideData.map((obj) => {
      return {
        time: obj.time,
        value: obj.value,
      };
    });
  }

  if (!isLoading && alcoholData) {
    transformedAlcoholArray = alcoholData.map((obj) => {
      return {
        time: obj.time,
        value: obj.value,
      };
    });
  }

  const data = transformedSuicidesArray.map((suicideObj, index) => {
    const alcoholObj = transformedAlcoholArray[index];
    return {
      time: suicideObj.time,
      suicideValue: suicideObj.value,
      alcoholValue: alcoholObj ? alcoholObj.value : null,
    };
  });

  return (
    <div className="h-screen bg-gray-50 px-4 py-6">
      <div className="flex justify-between items-center">
        <div>
          <div className="text-2xl text-sky-500 font-semibold">Data Analysis</div>
          <p className="flex w-full justify-center text-xl text-gray-800">Alcohol usage and sucide rate correlation analysis</p>
        </div>
        <ul className="menu menu-horizontal px-1">
          <div className="dropdown-end dropdown">
            <label tabIndex={0} className="btn-ghost btn-circle avatar btn">
              <div className="flex w-12 items-center justify-center rounded-full border-2 border-sky-600  bg-sky-500/40"></div>
            </label>
            <ul tabIndex={0} className="menu-compact dropdown-content menu rounded-box mt-3 w-52  border-2 border-gray-200/30 bg-gray-100 p-2   shadow-2xl">
              <li>
                <a className="text-lg text-gray-600 hover:text-gray-800" onClick={handleLogout}>
                  Logout
                </a>
              </li>
            </ul>
          </div>
        </ul>
      </div>
      <select className="select mt-4  w-24 mr-4 max-w-xs border-2 border-sky-600/30 bg-gray-50 text-sky-800/50" onChange={handleCountryChange} defaultValue="POL">
        <option disabled>Choose country</option>
        {countries.map((country) => {
          return <option key={country}>{country}</option>;
        })}
      </select>
      <select className="select mt-4 w-24 max-w-xs border-2 border-sky-600/30 bg-gray-50  text-sky-800/50" onChange={handleFilterChange} defaultValue="TOT">
        <option disabled>Choose filter</option>
        <option>TOT</option>
        <option>WOMEN</option>
        <option>MEN</option>
      </select>
      <div className="flex w-full h-auto gap-5 mt-9">
        <div className="bg-gray-100 rounded-xl h-auto w-full   flex justify-center items-center py-20">
          <ResponsiveContainer width="100%" aspect={6.5 / 3.0} maxHeight={300}>
            <AreaChart data={data} margin={{ right: 20, left: 0 }}>
              <defs>
                <linearGradient id="colorSuicide" x1="0" y1="0" x2="0" y2="1">
                  <stop offset="0%" stopColor="#0ea5e9" stopOpacity={0.4} />
                  <stop offset="75%" stopColor="#0ea5e9" stopOpacity={0.1} />
                </linearGradient>
                <linearGradient id="colorAlcohol" x1="0" y1="0" x2="0" y2="1">
                  <stop offset="0%" stopColor="#3b82f6" stopOpacity={0.4} />
                  <stop offset="75%" stopColor="#3b82f6" stopOpacity={0.1} />
                </linearGradient>
              </defs>

              <Area type="monotone" dataKey="suicideValue" stroke="#0ea5e9" strokeWidth={3.5} fillOpacity={1} fill="url(#colorSuicide)" />
              <Area type="monotone" dataKey="alcoholValue" stroke="#3b82f6" strokeWidth={3.5} fillOpacity={1} fill="url(#colorAlcohol)" />

              <XAxis dy={4} dataKey="time" axisLine={false} tickLine={false} />

              <YAxis dx={2} dataKey="suicideValue" axisLine={false} tickLine={false} tickCount={5} />

              <Tooltip />

              <CartesianGrid opacity={0.2} strokeWidth={2} vertical={false} />
            </AreaChart>
          </ResponsiveContainer>
        </div>
      </div>
      <button className="btn border-sky-500 border-2 text-sky-500 bg-gray-50  mt-9 mr-4 hover:bg-sky-600 hover:text-sky-50 hover:border-sky-600 ">Export countries CSV</button>
      <button className="btn border-sky-500 border-2 text-sky-500 bg-gray-50  mt-9 mr-4 hover:bg-sky-600 hover:text-sky-50 hover:border-sky-600 ">Export countries XML</button>
      <button className="btn border-sky-500 border-2 text-sky-500 bg-gray-50  mt-9 mr-4 hover:bg-sky-600 hover:text-sky-50 hover:border-sky-600 ">Export raw data CSV</button>
      <button className="btn border-sky-500 border-2 text-sky-500 bg-gray-50  mt-9 mr-4 hover:bg-sky-600 hover:text-sky-50 hover:border-sky-600 ">Export raw data XML</button>
      <input type="file" className="file-input  file-input-ghost bg-gray-50 border-2 border-sky-500 text-sky-500 hover:bg-sky-600 hover:text-sky-50 w-full max-w-xs" />
    </div>
  );
};
