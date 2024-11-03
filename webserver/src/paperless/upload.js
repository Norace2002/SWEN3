import Sidebar from "./sidebar";
import React, { useRef } from "react";
import '../Upload.css';

function UploadPage() {
    const fileInputRef = useRef(null);

    const handleUploadClick = () => {
        fileInputRef.current.click();
    };

    return (
        <div className="page-container">
            {/* header-area */}
            <div className="header">Upload Document</div>

            {/* content-area */}
            <div className="upload-box" onClick={handleUploadClick}>
                Drag your Document here ...
            </div>

            {/* input */}
            <input
                type="file"
                ref={fileInputRef}
                style={{ display: 'none' }}
            />
        </div>
    );
}

export default UploadPage;
