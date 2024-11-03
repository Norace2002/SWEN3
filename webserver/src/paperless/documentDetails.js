import React from 'react';

function DocumentDetailsPage() {
    return (
        <div style={styles.pageContainer}>
            <div style={styles.header}>Document XYZ</div>
            <div style={styles.contentContainer}>
                <div style={styles.details}>
                    <p><strong>Document Title:</strong> title</p>
                    <p><strong>Upload date:</strong> date</p>
                    <p><strong>Last edited:</strong> date</p>
                    <p><strong>Filetype:</strong> type</p>
                    <p><strong>Size:</strong> size</p>
                    <p><strong>Description:</strong> description</p>
                </div>
                <div style={styles.preview}>
                    <p>Document Preview</p>
                </div>
            </div>
            <div style={styles.buttonContainer}>
                <button style={styles.button}>Download</button>
                <button style={styles.button}>Edit</button>
                <button style={styles.button}>Delete</button>
            </div>
        </div>
    );
}

const styles = {
    pageContainer: {
        display: 'flex',
        flexDirection: 'column',
        paddingLeft: '10%',
        backgroundColor: '#333',
        padding: 0,
        height: '100vh',
        color: '#fff',
    },
    header: {
        width: '100%',
        backgroundColor: '#4CAF50',
        padding: '15px 0',
        fontSize: '1.5em',
        color: '#fff',
        textAlign: 'center',
    },
    contentContainer: {
        display: 'flex',
        marginTop: '20px',
    },
    details: {
        flex: 1,
        paddingRight: '20px',
    },
    preview: {
        flex: 1,
        backgroundColor: '#000',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        color: '#fff',
    },
    buttonContainer: {
        display: 'flex',
        justifyContent: 'center',
        marginTop: '20px',
    },
    button: {
        backgroundColor: '#fff',
        color: '#000',
        border: '1px solid #ccc',
        padding: '10px 20px',
        margin: '0 10px',
        cursor: 'pointer',
    },
};

export default DocumentDetailsPage;
