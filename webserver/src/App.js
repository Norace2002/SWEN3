import {BrowserRouter, Routes, Route, Navigate} from 'react-router-dom';
import React from 'react';

import LandingPage from './paperless/landing';
import UploadPage from './paperless/upload';
import PreviewPage from './paperless/preview';
import ProfilePage from './paperless/profile';
import Sidebar from './paperless/sidebar';

function App() {
  return (
      <BrowserRouter>
        <div style={styles.mainDiv}>
          {/* Sidebar Component */}
          <Sidebar />

          {/* Main content area which will update based on routes */}
          <div style={{ flex: 1, padding: '20px' }}>
            <Routes>
              {/* Default route to landing page */}
              <Route path="/" element={LandingPage} />

              {/* Sidebar-routed pages */}
              <Route path="/profile" element={ProfilePage} />
              <Route path="/documents" element={LandingPage} />
              <Route path="/upload" element={UploadPage} />

              {/* Catch-all route to handle unknown paths */}
              <Route path="*" element={<Navigate to={LandingPage} />} />
            </Routes>
          </div>
        </div>
      </BrowserRouter>
  );
}

const styles={
    mainDiv:{
        display: 'flex',
        height: '100%',
        width: '100%'
    }
}

export default App;
