import React from 'react';
import { useNavigate, useLocation } from 'react-router-dom';

function Button({ onClick, title, style }) {
    return (
        <button onClick={onClick} style={style}>
            {title}
        </button>
    );
}

function Sidebar() {
    const navigate = useNavigate(); // Hook for navigation
    const location = useLocation(); // Hook to get current location

    const handleNavigation = (page) => {
        navigate(page);
    };

    const isActive = (path) => {
        if (path === '/documents') {
            return location.pathname.startsWith('/documents');
        }
        return location.pathname === path;
    };

    return (
        <div style={styles.sidebar}>
            <Button
                onClick={() => handleNavigation('/profile')}
                style={{
                    ...styles.button,
                    backgroundColor: isActive('/profile') ? '#4CAF50' : '#fff',
                    color: isActive('/profile') ? '#fff' : '#000'
                }}
                title="Profile"
            />
            <Button
                onClick={() => handleNavigation('/documents')}
                style={{
                    ...styles.button,
                    backgroundColor: isActive('/documents') ? '#4CAF50' : '#fff',
                    color: isActive('/documents') ? '#fff' : '#000'
                }}
                title="Documents"
            />
            <Button
                onClick={() => handleNavigation('/upload')}
                style={{
                    ...styles.button,
                    backgroundColor: isActive('/upload') ? '#4CAF50' : '#fff',
                    color: isActive('/upload') ? '#fff' : '#000'
                }}
                title="Upload"
            />
        </div>
    );
}

// Styles for the sidebar and buttons
const styles = {
    sidebar: {
        width: '10%',
        height: '100vh',
        padding: '20px',
        backgroundColor: '#f0f0f0',
        display: 'flex',
        flexDirection: 'column',
    },
    button: {
        padding: '10px 20px',
        margin: '10px 0',
        border: '1px solid #ccc',
        cursor: 'pointer',
        textAlign: 'left',
        transition: 'background-color 0.3s ease',
    },
};

export default Sidebar;
