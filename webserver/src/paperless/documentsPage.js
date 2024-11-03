import React from "react";
import { Link } from "react-router-dom";

function DocumentsPage() {
    return (
        <div style={styles.pageContainer}>
            <div style={styles.header}>DOCUMENTS</div>
            <div style={styles.searchContainer}>
                <input type="text" placeholder="Search" style={styles.searchInput} />
            </div>
            <div style={styles.gridWrapper}>
                <div style={styles.gridContainer}>
                    {[...Array(8)].map((_, index) => (
                        <Link to={`/documents/${index}`} key={index} style={styles.documentLink}>
                            <div style={styles.documentTile}>
                                <div style={styles.documentImage}></div>
                                <div style={styles.documentText}>Document XYZ</div>
                                <div style={styles.documentDate}>dd.mm.yyy</div>
                            </div>
                        </Link>
                    ))}
                </div>
            </div>
        </div>
    );
}

const styles = {
    pageContainer: {
        display: 'flex',
        flexDirection: 'column',
        height: '100vh',
        backgroundColor: '#333',
        boxSizing: 'border-box',
        paddingLeft: '10%',
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
    searchContainer: {
        display: 'flex',
        justifyContent: 'center',
        padding: '20px',
    },
    searchInput: {
        width: '600px',
        padding: '12px',
        fontSize: '1.2em',
        borderRadius: '5px',
        border: '1px solid #ccc',
    },
    gridWrapper: {
        display: 'flex',
        justifyContent: 'center',
        width: '100%',
    },
    gridContainer: {
        display: 'grid',
        gridTemplateColumns: 'repeat(4, 1fr)',
        gridTemplateRows: 'repeat(2, auto)',
        gap: '20px',
        padding: '20px',
        justifyItems: 'center',
    },
    documentLink: {
        textDecoration: 'none',
    },
    documentTile: {
        backgroundColor: '#4CAF50',
        width: '150px',
        height: '200px',
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        padding: '10px',
        borderRadius: '10px',
        boxShadow: '0px 4px 8px rgba(0, 0, 0, 0.2)',
        color: '#fff',
        textAlign: 'center',
    },
    documentImage: {
        backgroundColor: '#000',
        width: '100%',
        height: '100px',
        marginBottom: '10px',
    },
    documentText: {
        fontSize: '1em',
        fontWeight: 'bold',
    },
    documentDate: {
        fontSize: '0.9em',
        color: '#ddd',
    },
};

export default DocumentsPage;
