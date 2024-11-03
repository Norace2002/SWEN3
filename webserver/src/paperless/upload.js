import Sidebar from "./sidebar";
import React, { useRef } from "react";

function UploadPage(){

    const fileInputRef = useRef(null);

    const handleUploadClick = () => {
        fileInputRef.current.click();
    };
    return(
        <div style={styles.pageContainer}>
            {/* header-area */}
            <div style={styles.header}>Upload Document</div>

            {/* content-area */}
            <div style={styles.uploadBox} onClick={handleUploadClick}>
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

const styles = {
    pageContainer: {
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'flex-start',
        height: '100vh',
        width: '100%',
        backgroundColor: '#333',
        boxSizing: 'border-box',
        margin: 0,
        padding: 0,
    },
    header: {
        width: '100%',
        textAlign: 'center',
        backgroundColor: '#4CAF50',
        padding: '15px 0',
        fontSize: '1.5em',
        color: '#fff',
    },
    uploadBox: {
        backgroundColor: '#4CAF50',
        width: '300px',
        height: '300px',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        borderRadius: '25px',
        color: '#fff',
        fontSize: '1.2em',
        textAlign: 'center',
        cursor: 'pointer',
        marginTop: '150px',
    },
};

export default UploadPage;
