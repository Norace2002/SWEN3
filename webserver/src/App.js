import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import React from 'react';

import LandingPage from './paperless/landing';
import UploadPage from './paperless/upload';
import PreviewPage from './paperless/preview';
import ProfilePage from './paperless/profile';
import Sidebar from './paperless/sidebar';
import DocumentsPage from './paperless/documentsPage';
import DocumentDetailsPage from './paperless/documentDetails';

function App() {
    return (
        <BrowserRouter>
            <div style={styles.mainDiv}>
                {/* Sidebar Component */}
                <Sidebar />

                {/* Main content area which will update based on routes */}
                <div style={{ flex: 1 }}>
                    <Routes>
                        {/* Default route to landing page */}
                        <Route path="/" element={<LandingPage />} />

                        {/* Sidebar-routed pages */}
                        <Route path="/profile" element={<ProfilePage />} />
                        <Route path="/documents" element={<DocumentsPage />} />
                        <Route path="/upload" element={<UploadPage />} />
                        <Route path="/documents/:id" element={<DocumentDetailsPage />} />
                        {/* Catch-all route to handle unknown paths */}
                        <Route path="*" element={<Navigate to={LandingPage} />} />
                    </Routes>
                </div>
            </div>
        </BrowserRouter>
    );
}

const styles = {
    mainDiv: {
        display: 'flex',
        height: '100vh',
        width: '100vw',
    },
    content: {
        flex: 1,
        overflow: 'hidden',
    }
}

export default App;
