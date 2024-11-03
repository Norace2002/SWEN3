import React from 'react';
import '../DocumentDetailsPage.css';

function DocumentDetailsPage() {
    return (
        <div className="page-container">
            <div className="header">Document XYZ</div>
            <div className="content-container">
                <div className="details">
                    <p className="detail-item"><strong>Document Title:</strong> title</p>
                    <p className="detail-item"><strong>Upload date:</strong> date </p>
                    <p className="detail-item"><strong>Last edited:</strong> date </p>
                    <p className="detail-item"><strong>Filetype:</strong> type </p>
                    <p className="detail-item"><strong>Size:</strong> size </p>
                    <p className="detail-item"><strong>Description:</strong> description </p>

                    {/* buttons */}
                    <div className="button-container">
                        <button className="button">Download</button>
                        <button className="button">Edit</button>
                        <button className="button">Delete</button>
                    </div>
                </div>
                <div className="preview">
                    <p>Document Preview</p>
                </div>
            </div>
        </div>
    );
}

export default DocumentDetailsPage;
