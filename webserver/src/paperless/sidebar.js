import React from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import '../sidebar.css';

function Button({ onClick, title, isActive }) {
    return (
        <button
            onClick={onClick}
            style={{
                ...styles.button,
                ...(isActive ? styles.activeButton : {}),
            }}
        >
            {title}
        </button>
    );
}

function Sidebar() {
    const navigate = useNavigate();
    const location = useLocation();

    const handleNavigation = (page) => {
        navigate(page);
    };

    return (
        <div style={styles.sidebar}>
            <Button
                onClick={() => handleNavigation('/profile')}
                title="Profile"
                isActive={location.pathname === '/profile'}
            />
            <Button
                onClick={() => handleNavigation('/documents')}
                title="Documents"
                isActive={location.pathname === '/documents' || location.pathname.includes('/documents/')}
            />
            <Button
                onClick={() => handleNavigation('/upload')}
                title="Upload Document"
                isActive={location.pathname === '/upload'}
            />
        </div>
    );
}

const styles = {
    sidebar: {
        width: '10%',
        height: '100vh',
        padding: '20px',
        backgroundColor: '#333',
        display: 'flex',
        flexDirection: 'column',
    },
    button: {
        padding: '10px 20px',
        margin: '10px 0',
        backgroundColor: '#444',
        color: 'white',
        border: '1px solid #ccc',
        textAlign: 'left',
        cursor: 'pointer',
    },
    activeButton: {
        backgroundColor: '#4CAF50',
        color: 'white',
        border: '1px solid #4CAF50',
    },
};

export default Sidebar;
