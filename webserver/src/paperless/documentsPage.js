import React, {useEffect, useState} from "react";
import { Link } from "react-router-dom";
import '../DocumentsPage.css';
import axios from 'axios';


function DocumentsPage() {

    // Save documents in documents array including setter
    const [documents, setDocuments] = useState([]);

    function handleSearch(e) {
        if (e.key === 'Enter') {
            search();
        }
    }

    async function search(){
        const data = await loadResults();

        if(data.length === 0){
            document.getElementById( 'emptySearchInfoBox').style.display = 'block';
        }

        setDocuments(data);
    }

    async function fetchDocuments() {
        const data = await loadDocuments();
        setDocuments(data);
    }

    async function cancelSearch(){
        document.getElementById( 'emptySearchInfoBox').style.display = 'none';
        document.getElementById('search').value = "";
        fetchDocuments();
    }

    useEffect(() => {
        fetchDocuments();
    }, []);

    return (
        <div className="page-container">
            <div className="header">DOCUMENTS</div>
            <div className="search-container">
                <input type="text" placeholder="Search" id="search" className="search-input" onKeyUp={handleSearch} />
                <button onClick={cancelSearch}>Cancel</button>
            </div>
            <div className="content-container" id="emptySearchInfoBox" style={{ display: 'none' }}>
                <p> No document matches your search! </p>
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



async function loadResults(){
    // Loading Documents
    try {
        const response = await axios.get('http://127.0.0.1:8081/documents/search/' + document.getElementById("search").value);
        console.log("SearchbarValue: " + document.getElementById("search").value)
        console.log("Elastic Search id List:", response.data);
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
