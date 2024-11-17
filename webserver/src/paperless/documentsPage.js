import React, {useEffect, useState} from "react";
import { Link } from "react-router-dom";
import '../DocumentsPage.css';
import axios from 'axios';


function DocumentsPage() {

    // Save documents in documents array including setter
    const [documents, setDocuments] = useState([]);

    useEffect(() => {
        async function fetchDocuments() {
            const data = await loadDocuments();
            setDocuments(data);
        }
        fetchDocuments();
    }, []);
    return (
        <div className="page-container">
            <div className="header">DOCUMENTS</div>
            <div className="search-container">
                <input type="text" placeholder="Search" className="search-input" />
            </div>
            <div className="grid-wrapper">
                <div className="grid-container">
                    {documents.map((document) => (
                        <Link
                            to={`/documents/${document.id}`}
                            key={document.id}
                            className="document-link"
                        >
                            <div className="document-tile">
                                <div className="document-image"></div>
                                <div className="document-text">{document.title}</div>
                                <div className="document-date">placeholder</div>
                            </div>
                        </Link>
                    ))}
                </div>
            </div>
        </div>
    );
}

async function loadDocuments() {
    // Loading Documents
    try {
        const response = await axios.get('http://127.0.0.1:8081/documents');
        //console.log("API Response:", response.data);
        return response.data;

    } catch(err) {
        if (err.response) {
            // The request was made and the server responded with a status code
            console.error("Error status:", err.response.status);
            console.error("Error data:", err.response.data);
        } else if (err.request) {
            // The request was made but no response was received
            console.error("No response received:", err.request);
        } else {
            // Something happened in setting up the request that triggered an error
            console.error("Error:", err.message);
        }
    }

    return [];
}

export default DocumentsPage;
