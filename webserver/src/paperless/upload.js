import Sidebar from "./sidebar";
import React, { useRef, useState } from "react";
import '../Upload.css';

let uidCounter = 3;

function UploadPage() {
    const fileInputRef = useRef(null);
    const [selectedFile, setSelectedFile] = useState(null);


    //-------------------- Input Events -------------------
    const handleUploadClick = () => {
        fileInputRef.current.click();
    };

    //gets called after uploading file
    const handleFileChange = (event) => {
        const file = event.target.files[0];
        if (file) {
            setSelectedFile(file);
            console.log("Selected file:", file);

            uploadFile(file);
        }
    };

    //Same but for drag and drop - enables Drag and Drag and Drop functionality
    const handleDrop = (event) => {
        event.preventDefault();
        const file = event.dataTransfer.files[0];
        if (file) {
            setSelectedFile(file);
            console.log("Dropped file:", file);

            uploadFile(file);
        }
    };

    //disables the browser's default behaviour, which leads to the opening of the file - which we don't want
    const handleDragOver = (event) => {
        event.preventDefault();
    };

    //-----------------------------Upload -----------------------------

    const uploadFile = async (file) => {
        //placeholder values
        const author = "Jerome";
        const description = "some description";
        const fileUrl = "http://test";
        const tags = '["tag1", "tag2"]';
        const version = 3;
        //----------------------------------
        const currentDate = new Date().toISOString().split('T')[0];
        const fileType = file.type.split('/')[1];
        //const fileSizeMB = (file.size / (1024 * 1024)).toFixed(2);
        const fileSize = file.size;
        const title = file.name.split('.')[0];

        const document = '{"id": "uid-' + uidCounter + '", "title": "' + title + '", "author": "' +
                                author + '", "description": "' + description + '", "uploadDate": "' +
                                currentDate + '", "fileType": "' + fileType + '", "fileSize": ' +
                                fileSize + ', "fileUrl": "' + fileUrl + '", "tags": ' +
                                tags + ', "version": "' + version + '"}'

        //increment uidCounter by 1
        uidCounter++;
        console.log(document);

        const formData = new FormData();
        formData.append('document', document);
        formData.append('file', file);

        try {
            const response = await fetch('http://127.0.0.1:8081/documents', {
                method: 'POST',
                body: formData,
            });

            if (response.ok) {
                console.log('Upload successfully uploaded');
            } else {
                console.error('Error during upload: ', response.status, response.statusText);
            }
        } catch (error) {
            console.error('Error during upload:', error);
        }
    };

    //------------------------- React --------------------------------

    return (
        <div className="page-container">
            {/* header-area */}
            <div className="header">Upload Document</div>

            {/* content-area */}
            <div
                className="upload-box"
                onClick={handleUploadClick}
                onDrop={handleDrop}
                onDragOver={handleDragOver}
            >
                {selectedFile
                    ? `Selected: ${selectedFile.name}`
                    : "Drag your Document here or click to upload"}
            </div>

            {/* input */}
            <input
                type="file"
                ref={fileInputRef}
                style={{ display: 'none' }}
                onChange={handleFileChange}
                //accept="application/pdf" //accept only pdf ? or not
            />
        </div>
    );
}

export default UploadPage;
