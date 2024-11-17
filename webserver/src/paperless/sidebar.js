import React from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import '../sidebar.css';

function Button({ onClick, title, className }) {
    return (
        <button onClick={onClick} className={className}>
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
    
    const isActive = (path) => {
        if (path === '/documents') {
            return location.pathname.startsWith('/documents');
        }
        return location.pathname === path;
    };

    return (
        <div className="sidebar">
            <Button
                onClick={() => handleNavigation('/profile')}
                className={`sidebar-button ${isActive('/profile') ? 'sidebar-button-active' : ''}`}
                title="Profile"
            />
            <Button
                onClick={() => handleNavigation('/documents')}
                className={`sidebar-button ${isActive('/documents') ? 'sidebar-button-active' : ''}`}
                title="Documents"
            />
            <Button
                onClick={() => handleNavigation('/upload')}
                className={`sidebar-button ${isActive('/upload') ? 'sidebar-button-active' : ''}`}
                title="Upload"
            />
        </div>
    );
}

export default Sidebar;
