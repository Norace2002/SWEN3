import React from "react";
import { Link } from "react-router-dom";
import '../DocumentsPage.css';

function DocumentsPage() {
    return (
        <div className="page-container">
            <div className="header">DOCUMENTS</div>
            <div className="search-container">
                <input type="text" placeholder="Search" className="search-input" />
            </div>
            <div className="grid-wrapper">
                <div className="grid-container">
                    {[...Array(8)].map((_, index) => (
                        <Link to={`/documents/${index}`} key={index} className="document-link">
                            <div className="document-tile">
                                <div className="document-image"></div>
                                <div className="document-text">Document XYZ</div>
                                <div className="document-date">dd.mm.yyy</div>
                            </div>
                        </Link>
                    ))}
                </div>
            </div>
        </div>
    );
}

export default DocumentsPage;
