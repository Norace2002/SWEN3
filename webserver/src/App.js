import { BrowserRouter, Routes, Route } from 'react-router-dom';
import React from 'react';
import './App.css';
import logo from './logo.svg';

function AppRouter() {
  return (
      <BrowserRouter>
        <Routes>
          {/* Home route */}
          <Route path="/" element={
            <div className="App">
              <header className="App-header">
                <img src={logo} className="App-logo" alt="logo" />
                <p>
                  Edit <code>src/App.js</code> and save to reload.
                </p>
                <a
                    className="App-link"
                    href="https://reactjs.org"
                    target="_blank"
                    rel="noopener noreferrer"
                >
                  Learn React
                </a>
              </header>
            </div>
          } />

          {/* Documents route */}
          <Route path="/documents" element={<div>/documents reached</div>} />

          {/* Documents upload route */}
          <Route path="/documents/upload" element={<div>/documents/upload reached</div>} />

          {/* Dynamic document route (use `:id` for dynamic segments in URL) */}
          <Route path="/documents/:id" element={<div>/documents/id reached</div>} />
        </Routes>
      </BrowserRouter>
  );
}

export default AppRouter;
