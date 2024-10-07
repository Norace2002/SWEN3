import React, {useEffect} from "react";
import axios from 'axios';

function LandingPage(){
    // Loading Documents
    const updateDocuments = () =>{
        axios.get('http://127.0.0.1:8081/documents')
            .then((response) => {
                console.log("API Response:", response.data);
            })
            .catch((err) => {
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
            })
    }

    // Problem with useEffect
    // Uncaught TypeError: Cannot read properties of null (reading 'useEffect')
    /*
    useEffect(() => {
        updateDocuments();
    }, []);
    */

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Searchbar shenanigans

    let searchQuery;

    const updateSearchbar = (event) => {
        searchQuery = event.target.value; // Update the search query as the user types
    };

    const searchSubmit = () => {
        console.log("Search query:", searchQuery);
    };

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Actual site content

    return(
        <div>
            <h1> Paperless Landing Page </h1>
            <div id={"searchbar"} style={styles.searchbar}>
                <input
                    type = "text"
                    placeholder = "Search documents..."
                    onChange= {updateSearchbar}
                    value = {searchQuery}
                    style={{ padding: '10px', width: '300px', marginRight: '10px' }}
                />
                <button onClick={searchSubmit} style={{ padding: '10px 20px' }}>
                    Search
                </button>
            </div>
            <div id={"documents"}>
                {/* will present all documents once component is mounted */}
                <button onClick={updateDocuments} style={{ padding: '10px 20px' }}>
                    Test to make API call
                </button>
            </div>
        </div>
    );
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// in-file styles for now

const styles = {
    searchbar:{
        padding: '10px',
        width: '100%'
    }
}

export default LandingPage();