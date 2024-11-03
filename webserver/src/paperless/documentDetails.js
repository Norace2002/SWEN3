import React from 'react';

function DocumentDetailsPage() {
    return (
        <div style={styles.pageContainer}>
            <div style={styles.header}>Document XYZ</div>
            <div style={styles.contentContainer}>
                <div style={styles.details}>
                    <p style={styles.detailItem}><strong>Document Title:</strong> title</p>
                    <p style={styles.detailItem}><strong>Upload date:</strong> date </p>
                    <p style={styles.detailItem}><strong>Last edited:</strong> date </p>
                    <p style={styles.detailItem}><strong>Filetype:</strong> type </p>
                    <p style={styles.detailItem}><strong>Size:</strong> size </p>
                    <p style={styles.detailItem}><strong>Description:</strong> description </p>

                    {/* buttons */}
                    <div style={styles.buttonContainer}>
                        <button style={styles.button}>Download</button>
                        <button style={styles.button}>Edit</button>
                        <button style={styles.button}>Delete</button>
                    </div>
                </div>
                <div style={styles.preview}>
                    <p>Document Preview</p>
                </div>
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
        position: 'relative',
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
        flex: 1,
        paddingRight: '20px',
        position: 'relative',
    },
    details: {
        flex: 1,
        paddingRight: '20px',
        paddingLeft: '20px',
        lineHeight: '1.6',
        position: 'relative',
    },
    detailItem: {
        marginBottom: '10px',
        fontSize: '1.5em',
    },
    preview: {
        width: '424px',
        height: '600px',
        backgroundColor: '#000',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        color: '#fff',
        borderRadius: '8px',
        marginRight: '20px',
        marginTop: '20px',
        marginBottom: '20px',
    },
    buttonContainer: {
        position: 'absolute',
        bottom: '20px',
        left: '20px',
        display: 'flex',
        gap: '15px',
    },
    button: {
        backgroundColor: '#fff',
        color: '#000',
        border: '1px solid #ccc',
        padding: '15px 30px',
        fontSize: '1em',
        cursor: 'pointer',
    },
};

export default DocumentDetailsPage;
