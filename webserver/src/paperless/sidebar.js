import React from 'react';
import { useNavigate } from 'react-router-dom';

function Button({ onClick, title, style }) {
    return (
        <button onClick={onClick} style={style}>
            {title}
        </button>
    );
}

function Sidebar() {
    const navigate = useNavigate(); // Hook for navigation

    let currentPage = '/documents';

    // Helper function to handle navigation
    const handleNavigation = (page) => {
        navigate(page);
        currentPage = page;
    };

    return (
        <div style={styles.sidebar}>
            <Button
                onClick={() => handleNavigation('/profile')}
                style={currentPage === 'profile' ? styles.activeButton : styles.button}
                title="Profile"
            />
            <Button
                onClick={() => handleNavigation('/documents')}
                style={currentPage === 'documents' ? styles.activeButton : styles.button}
                title="Documents"
            />
            <Button
                onClick={() => handleNavigation('/upload')}
                style={currentPage === 'upload' ? styles.activeButton : styles.button}
                title="Upload"
            />
        </div>
    );
}

// Styles for the sidebar and buttons
const styles = {
    sidebar: {
        width: '10%',
        height: '100%',
        padding: '20px',
        backgroundColor: '#f0f0f0',
        display: 'flex',
        flexDirection: 'column',
    },
    button: {
        padding: '10px 20px',
        margin: '10px 0',
        backgroundColor: '#fff',
        border: '1px solid #ccc',
        cursor: 'pointer',
        textAlign: 'left',
    },
    activeButton: {
        padding: '10px 20px',
        margin: '10px 0',
        backgroundColor: '#007bff',
        color: '#fff',
        border: 'none',
        cursor: 'pointer',
        textAlign: 'left',
    },
};

export default Sidebar;
