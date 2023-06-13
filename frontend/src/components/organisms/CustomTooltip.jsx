import React from 'react';

export const CustomTooltip = ({ active, payload, label }) => {
  if (active) {
    return (
      <div className="flex flex-col justify-center rounded-xl bg-gray-50  p-5  shadow-sm  border-2 border-gray-100">
        <h1 className="mb-2 text-xl font-bold  text-center text-gray-500">{label}</h1>
        <p className="text-center text-lg font-semibold text-sky-600/70 ">Suicides: {payload[0].value}</p>
        <p className="text-center text-lg font-semibold text-blue-600/70">Alcohol Usage: {payload[1].value}</p>
      </div>
    );
  }

  return null;
};
