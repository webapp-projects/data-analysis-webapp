import React from 'react';
import axios from 'axios';
import { useEffect, useState } from 'react';
import { addTokenToRequestHeader } from '../../helpers/addTokenToRequestHeader';
import { AreaChart, Area, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, Legend } from 'recharts';
import { CustomTooltip } from '../organisms/CustomTooltip';

export const Home = () => {
  const [isLoading, setIsLoading] = useState(true);
  const [suicideData, setSuicideData] = useState(null);
  const [alcoholData, setAlcoholData] = useState(null);
  const [countriesData, setCountriesData] = useState();
  const [country, setCountry] = useState('POL');
  const [filter, setFilter] = useState('TOT');
  const [xmlExportData, setXmlExportData] = useState('Raw Data');
  const [csvExportData, setCsvExportData] = useState('Raw Data');
  const [jsonExportData, setJsonExportData] = useState('Raw Data');
  const [csvFile, setCsvFile] = useState(null);
  const [xmlFile, setXmlFile] = useState(null);
  const [jsonFile, setJsonFile] = useState(null);
  const [avgSuicides, setAvgSuicides] = useState(null);
  const [avgAlcoholUsage, setAvgAlcoholUsage] = useState(null);

  // for relative y axis calculations
  const [maxAlcoholValue, setMaxAlcoholValue] = useState(null);
  const [maxSuicidesValue, setMaxSuicidesValue] = useState(null);

  // remove token and reload page (results in redirect to /login)
  const handleLogout = () => {
    localStorage.removeItem('token');
    window.location.reload();
  };

  const handleCountryChange = (e) => {
    handleSoapRequest();
    setCountry(e.target.value);
  };

  const handleFilterChange = (e) => {
    setFilter(e.target.value);
  };

  // -- XML --
  // update xml download type
  const handleXmlExportDataChange = (e) => {
    setXmlExportData(e.target.value);
  };

  // download xml file
  const handleXmlDownload = () => {
    let endpoint = '';
    let name = '';
    if (xmlExportData === 'Raw Data') {
      endpoint = 'http://localhost:8080/api/raw-data/xml';
      name = 'raw_data.xml';
    } else {
      endpoint = 'http://localhost:8080/api/countries/xml';
      name = 'countries.xml';
    }

    fetch(endpoint)
      .then((response) => response.blob())
      .then((blob) => {
        const downloadLink = document.createElement('a');
        downloadLink.href = URL.createObjectURL(blob);
        downloadLink.download = name;
        downloadLink.click();
      });
  };

  // update uploaded xml file
  const handleXmlFileChange = (e) => {
    setXmlFile(e.target.files[0]);
  };

  //sent xml upload
  const handleXmlUpload = (e) => {
    if (xmlFile) {
      const formData = new FormData();
      formData.append('file', xmlFile);

      console.log(formData);

      axios
        .post('http://localhost:8080/api/countries/upload-xml', formData)
        .then((response) => {
          console.log(response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    }
  };

  // - CSV -
  // update xml download type
  const handleCsvExportDataChange = (e) => {
    setCsvExportData(e.target.value);
  };

  // csv download
  const handleCsvDownload = () => {
    let endpoint = '';
    let name = '';
    if (csvExportData === 'Raw Data') {
      endpoint = 'http://localhost:8080/api/raw-data/csv';
      name = 'raw_data.csv';
    } else {
      endpoint = 'http://localhost:8080/api/countries/csv';
      name = 'countries.csv';
    }

    fetch(endpoint)
      .then((response) => response.blob())
      .then((blob) => {
        const downloadLink = document.createElement('a');
        downloadLink.href = URL.createObjectURL(blob);
        downloadLink.download = name;
        downloadLink.click();
      });
  };

  // update uploaded xml file
  const handleCsvFileChange = (e) => {
    setCsvFile(e.target.files[0]);
  };

  //sent csv upload
  const handleCsvUpload = () => {
    if (csvFile) {
      const formData = new FormData();
      formData.append('file', csvFile);

      axios
        .post('http://localhost:8080/api/countries/upload-csv', formData)
        .then((response) => {
          console.log(response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    }
  };

  // - JSON -
  // update download json type
  const handleJsonExportDataChange = (e) => {
    setJsonExportData(e.target.value);
  };

  // json download
  const handleJsonDownload = () => {
    let endpoint = '';
    let name = '';
    if (jsonExportData === 'Raw Data') {
      endpoint = 'http://localhost:8080/api/raw-data/json';
      name = 'raw_data.json';
    } else {
      endpoint = 'http://localhost:8080/api/countries/json';
      name = 'countries.json';
    }

    fetch(endpoint)
      .then((response) => response.blob())
      .then((blob) => {
        const downloadLink = document.createElement('a');
        downloadLink.href = URL.createObjectURL(blob);
        downloadLink.download = name;
        downloadLink.click();
      });
  };

  // update uploaded json file
  const handleJsonFileChange = (e) => {
    setJsonFile(e.target.files[0]);
  };

  //sent json upload
  const handleJsonUpload = () => {
    if (jsonFile) {
      const formData = new FormData();
      formData.append('file', jsonFile);

      axios
        .post('http://localhost:8080/api/countries/upload-json', formData)
        .then((response) => {
          console.log(response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    }
  };

  // SOAP request for Avg Suicide and Alcohol Usage data
  const handleSoapRequest = () => {
    const body = `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
    xmlns:gs="http://localhost/integration">
    <soapenv:Header/>
    <soapenv:Body>
    <gs:getCountryRequest>
    <gs:name>${country}</gs:name>
    </gs:getCountryRequest>
    </soapenv:Body>
    </soapenv:Envelope>`;

    const headers = {
      'Content-Type': 'text/xml',
    };

    axios
      .post('http://localhost:8080/ws', body, { headers })
      .then((response) => {
        const parser = new DOMParser();
        const xmlDoc = parser.parseFromString(response.data, 'text/xml');

        setAvgSuicides(xmlDoc.getElementsByTagName('ns2:avgSuicideRate')[0].textContent);
        setAvgAlcoholUsage(xmlDoc.getElementsByTagName('ns2:avgAlcoholConsumption')[0].textContent);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  useEffect(() => {
    let endpoints = [`http://localhost:8080/api/suicide/country/${country}?subject=${filter}`, `http://localhost:8080/api/alcohol/country/${country}?subject=TOT`, 'http://localhost:8080/api/countries'];

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
        setCountriesData(responseData[2]);

        // find max value of Suicides (for relative Y-axis)
        if (responseData[0]) {
          const maxSuicidesValue = Math.max(...responseData[1].map((obj) => obj.value));
          setMaxSuicidesValue(maxSuicidesValue);
        }

        // find max value of Alcohol Usage (for relative Y-axis)
        if (responseData[1]) {
          const maxAlcoholValue = Math.max(...responseData[1].map((obj) => obj.value));
          setMaxAlcoholValue(maxAlcoholValue);
        }

        setIsLoading(false);
      } catch (error) {
        console.log(error);
      }
    };

    fetchData();
  }, [country, filter]);

  handleSoapRequest();

  let transformedSuicidesArray = [];
  let transformedAlcoholArray = [];

  // transform Suicides data for chart display
  if (!isLoading && suicideData) {
    transformedSuicidesArray = suicideData.map((obj) => {
      return {
        time: obj.time,
        value: obj.value,
      };
    });
  }

  // transform Alcohol Usage data for chart display
  if (!isLoading && alcoholData) {
    transformedAlcoholArray = alcoholData.map((obj) => {
      return {
        time: obj.time,
        value: obj.value,
      };
    });
  }

  // combine Alcohol Usage and Suicides data into one array
  const data = transformedSuicidesArray.map((suicideObj, index) => {
    const alcoholObj = transformedAlcoholArray[index];
    return {
      time: suicideObj.time,
      suicideValue: suicideObj.value,
      alcoholValue: alcoholObj ? alcoholObj.value : null,
    };
  });

  // display loading info (enables Recharts animation)
  if (isLoading) {
    return <div className="text-6xl font-bold text-gray-800/40">Loading data...</div>;
  }

  return (
    <div className="h-auto min-h-screen bg-gray-50 px-4 py-6">
      <div className="flex justify-between items-center">
        <div>
          <p className="flex w-full justify-center text-2xl text-sky-500 font-semibold">Alcohol usage and sucide rate</p>
          <p className=" w-full text-xl text-sky-950 font-semibold">Correlation analysis</p>
        </div>

        <ul className="menu menu-horizontal px-1">
          <div className="dropdown-end dropdown">
            <label tabIndex={0} className="btn-ghost btn-circle avatar btn">
              <div className="flex w-12 items-center justify-center rounded-full border  border-gray-500/10  bg-gray-100  "></div>
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

      <div className="flex items-center gap-5 mt-4">
        <select className="select w-24    max-w-xs border-2 border-gray-600/10 bg-gray-50 text-gray-700" onChange={handleCountryChange} defaultValue="POL">
          <option disabled>Choose country</option>
          {countriesData.map((country) => {
            return <option key={country.code}>{country.code}</option>;
          })}
        </select>

        <select className="select  w-24   max-w-xs border-2 border-gray-600/10 bg-gray-50 text-gray-700 " onChange={handleFilterChange} defaultValue="TOT">
          <option disabled>Choose filter</option>
          <option>TOT</option>
          <option>WOMEN</option>
          <option>MEN</option>
        </select>

        <div className="flex items-center h-12 gap-5 border-2 bordery-gray-600/20 rounded-lg px-5 ">
          <p className="text-gray-800">
            Avg Alcohol usage:<span className="text-sky-500 ml-2 font-bold">{Math.round(avgAlcoholUsage * 100) / 100}</span>{' '}
          </p>

          <p className="text-gray-800">
            Avg Suicides:
            <span className="text-sky-500 ml-2 font-bold">{Math.round(avgSuicides * 100) / 100}</span>{' '}
          </p>
        </div>
      </div>

      <div className="flex w-full h-full gap-5 mt-5">
        <div className="bg-gray-100/60 rounded-2xl   border-gray-500/10 border h-auto w-full   flex justify-center items-center py-12">
          <ResponsiveContainer width="100%" aspect={6.5 / 3.0} maxHeight={450}>
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

              <Area type="monotone" name="Suicides " dataKey="suicideValue" stroke="#0ea5e9" strokeWidth={3.5} fillOpacity={1} fill="url(#colorSuicide)" />
              <Area type="monotone" name="Alcohol Usage" dataKey="alcoholValue" stroke="#3b82f6" strokeWidth={3.5} fillOpacity={1} fill="url(#colorAlcohol)" />

              <XAxis dy={7} dataKey="time" axisLine={false} tickLine={false} />

              {/* for relative y axis:  */}
              {/* <YAxis dx={2} dataKey="suicideValue" axisLine={false} tickLine={false} tickCount={5} domain={[0, Math.max(maxSuicidesValue, maxAlcoholValue)]} /> */}

              {/* for nonrelative y axis: */}
              <YAxis dx={2} dataKey="suicideValue" axisLine={false} tickLine={false} tickCount={5} domain={[0, 45]} />

              <Tooltip content={<CustomTooltip />} />

              <Legend dy={20} wrapperStyle={{ position: 'relative', marginTop: '2px' }} q />

              <CartesianGrid opacity={0.4} strokeWidth={2} vertical={false} />
            </AreaChart>
          </ResponsiveContainer>
        </div>
      </div>
      <div className="flex flex-col items-center gap-5">
        <h1 className="text-xl font-semibold text-gray-600 mt-7 mb-3">Export data</h1>

        <div className="flex items-center gap-5">
          <p className="text-gray-600 text-md font-semibold">XML:</p>
          <select className="select  border-2 border-sky-600/30 bg-gray-50 text-gray-700 " defaultValue="Raw Data" onChange={handleXmlExportDataChange}>
            <option disabled>Choose filter</option>
            <option>Raw Data</option>
            <option>Countries</option>
          </select>
          <button className="btn  bg-sky-500 text-sky-50 hover:bg-sky-600 border-0 normal-case" onClick={handleXmlDownload}>
            Export
          </button>
        </div>

        <div className="flex items-center gap-5">
          <p className="text-gray-600 text-md font-semibold">CSV:</p>
          <select className="select  border-2 border-sky-600/30 bg-gray-50 text-gray-700 " defaultValue="Raw Data" onChange={handleCsvExportDataChange}>
            <option disabled>Choose filter</option>
            <option>Raw Data</option>
            <option>Countries</option>
          </select>
          <button className="btn bg-sky-500 text-sky-50 hover:bg-sky-600 border-0 normal-case" onClick={handleCsvDownload}>
            Export
          </button>
        </div>

        <div className="flex items-center gap-5">
          <p className="text-gray-600 text-md font-semibold">JSON:</p>
          <select className="select  border-2 border-sky-600/30 bg-gray-50 text-gray-700 " defaultValue="Raw Data" onChange={handleJsonExportDataChange}>
            <option disabled>Choose filter</option>
            <option>Raw Data</option>
            <option>Countries</option>
          </select>
          <button className="btn bg-sky-500 text-sky-50 hover:bg-sky-600 border-0 normal-case" onClick={handleJsonDownload}>
            Export
          </button>
        </div>

        <h1 className="text-xl font-semibold text-gray-600 mt-3">Import data</h1>
        <div className="flex items-center gap-5">
          <p className="text-gray-600 text-md font-semibold">XML:</p>
          <input type="file" onChange={handleXmlFileChange} className="file-input  file-input-ghost border-2 border-sky-600/30 bg-gray-50 text-gray-700 cursor-pointer  w-full max-w-xs" />
          <button className="btn bg-sky-500 text-sky-50 hover:bg-sky-600 border-0 disabled:bg-gray-100 disabled:text-gray-300 normal-case" disabled={!xmlFile ? 'disabled' : ''} onClick={handleXmlUpload}>
            Upload
          </button>
        </div>
        <div className="flex items-center gap-5">
          <p className="text-gray-600 text-md font-semibold">CSV:</p>
          <input type="file" onChange={handleCsvFileChange} className="file-input  file-input-ghost border-2 border-sky-600/30 bg-gray-50 text-gray-700 cursor-pointer  w-full max-w-xs" />
          <button className="btn bg-sky-500 text-sky-50 hover:bg-sky-600 border-0 disabled:bg-gray-100 disabled:text-gray-300 normal-case" disabled={!csvFile ? 'disabled' : ''} onClick={handleCsvUpload}>
            Upload
          </button>
        </div>
        <div className="flex items-center gap-5">
          <p className="text-gray-600 text-md font-semibold">JSON:</p>
          <input type="file" onChange={handleJsonFileChange} className="file-input  file-input-ghost border-2 border-sky-600/30 bg-gray-50 text-gray-700 cursor-pointer  w-full max-w-xs" />
          <button className="btn bg-sky-500 text-sky-50 hover:bg-sky-600 border-0 disabled:bg-gray-100 disabled:text-gray-300 normal-case" disabled={!jsonFile ? 'disabled' : ''} onClick={handleJsonUpload}>
            Upload
          </button>
        </div>
      </div>
    </div>
  );
};
