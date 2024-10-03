import logo from './logo.svg';
import './App.css';
import {BrowserRouter, Routes, Route} from "react-router-dom";

/*
import './paperless/landing.js';
import './paperless/upload.js';
import './paperless/preview.js';
*/

function App() {
  return (
      <BrowserRouter>
        <Routes>
          <!-- default path leads (for now) to standard react page -->
          <Route exact path="/">
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
          </Route>

          <!-- documents landing page -->
          <Route exact path="/documents">
            /documents reached
          </Route>

          <!-- documents upload page -->
          <Route exact path="/documents/upload">
            /documents/upload reached
          </Route>

          <!-- document preview/info page -->
          <Route exact path="/documents/{id}">
            /documents/id reached
          </Route>
        </Routes>
      </BrowserRouter>
  );
}

export default App;
