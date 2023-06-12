import React from 'react';

export const Home = () => {
  const handleLogout = () => {
    localStorage.removeItem('token');
    window.location.reload();
  };
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
              <div className="flex w-12 items-center justify-center rounded-full border-2 border-sky-600  bg-sky-500"></div>
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
      <div className="flex w-full h-2/3 gap-5 mt-9">
        <div className="bg-gray-100 h-full w-full   flex justify-center items-center">wykres</div>
      </div>
      <button className="btn border-sky-500 border-2 text-sky-500 bg-gray-50  mt-9 mr-4 hover:bg-sky-600 hover:text-sky-50 hover:border-sky-600 ">Export countries CSV</button>
      <button className="btn border-sky-500 border-2 text-sky-500 bg-gray-50  mt-9 mr-4 hover:bg-sky-600 hover:text-sky-50 hover:border-sky-600 ">Export countries XML</button>
      <button className="btn border-sky-500 border-2 text-sky-500 bg-gray-50  mt-9 mr-4 hover:bg-sky-600 hover:text-sky-50 hover:border-sky-600 ">Export raw data CSV</button>
      <button className="btn border-sky-500 border-2 text-sky-500 bg-gray-50  mt-9 mr-4 hover:bg-sky-600 hover:text-sky-50 hover:border-sky-600 ">Export raw data XML</button>
      <input type="file" className="file-input  file-input-ghost bg-gray-50 border-2 border-sky-500 text-sky-500 hover:bg-sky-600 hover:text-sky-50 w-full max-w-xs" />
    </div>
  );
};
