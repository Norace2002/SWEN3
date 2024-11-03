import React from "react";

function DocumentsPage() {
    return (
        <div style={styles.pageContainer}>
            {/* header-area */}
            <div style={styles.header}>DOCUMENTS</div>

            {/* search */}
            <div style={styles.searchContainer}>
                <input type="text" placeholder="Search" style={styles.searchInput} />
            </div>

            {/* document placeholder */}
            <div style={styles.gridWrapper}>
                <div style={styles.gridContainer}>
                    {[...Array(8)].map((_, index) => (
                        <div key={index} style={styles.documentTile}>
                            <div style={styles.documentImage}></div>
                            <div style={styles.documentText}>Document XYZ</div>
                            <div style={styles.documentDate}>dd.mm.yyy</div>
                        </div>
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
    searchContainer: {
        display: 'flex',
        justifyContent: 'center',
        padding: '20px',
        backgroundColor: '#333',
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
