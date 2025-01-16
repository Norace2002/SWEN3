import React, {useEffect, useState} from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';

function DocumentDetailsPage() {
    const {id} = useParams();
    const navigate = useNavigate();
    const [documentData, setDocumentData] = useState([]);


    useEffect(() => {
        if (id) {
            // REST call
            axios.get(`http://127.0.0.1:8081/documents/${id}/metadata`)
                .then((response) => {
                    setDocumentData(response.data); // Save Document
                })
                .catch((error) => {
                    console.error("Fehler beim Laden des Dokuments:", error);
                });
        }
    }, [id]); // //effect trigger every time id changes

    const deleteDocument = async () => {
        if (!id) return;

        const confirmDelete = window.confirm("Are you sure you want to delete this document?");
        if (!confirmDelete) return;

        try {
            await axios.delete(`http://127.0.0.1:8081/documents/${id}`);
            alert("Document deleted successfully.");
            navigate('/documents');
        } catch (error) {
            if (error.response) {
                console.error("Error status:", error.response.status);
                console.error("Error data:", error.response.data);
                alert("Error deleting the document. Please try again.");
            } else if (error.request) {
                console.error("No response received:", error.request);
                alert("Server not responding. Please check your connection.");
            } else {
                console.error("Error:", error.message);
                alert("An error occurred. Please try again.");
            }
        }
    };

    const downloadDocument = async () => {
        if (!id) return;

        try {
            const response = await axios.get(`http://127.0.0.1:8081/documents/${id}/download`, {
                // specify datatype to be byte array
                responseType: 'arraybuffer',
            });

            // convert data to Blob (wtf is blob???, does it work???)
            const file = new Blob([response.data], {
                type: 'application/pdf',
            });

            // create url and open in browser
            const fileURL = URL.createObjectURL(file);
            window.open(fileURL);

        } catch (error) {
            if (error.response) {
                console.error("Error status:", error.response.status);
                console.error("Error data:", error.response.data);
                alert("Error downloading the document. Please try again.");
            } else if (error.request) {
                console.error("No response received:", error.request);
                alert("Server not responding. Please check your connection.");
            } else {
                console.error("Error:", error.message);
                alert("An error occurred. Please try again.");
            }
        }
    };

    return (
        <div style={styles.pageContainer}>
            <div style={styles.header}>Document {documentData.id}</div>
            <div style={styles.contentContainer}>
                <div style={styles.details}>
                    <p style={styles.detailItem}><strong>Title:</strong> {documentData.title}</p>
                    <p style={styles.detailItem}><strong>Uploaded:</strong> {documentData.uploadDate} </p>
                    <p style={styles.detailItem}><strong>Type:</strong> {documentData.fileType} </p>
                    <p style={styles.detailItem}><strong>Size:</strong> {documentData.fileSize} </p>
                    <p style={styles.detailItem}><strong>Description:</strong> {documentData.description }</p>

                    {/* buttons */}
                    <div style={styles.buttonContainer}>
                        <button style={styles.button} onClick={downloadDocument}>Download</button>
                        <button style={styles.button} onClick={deleteDocument}>Delete</button>
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
