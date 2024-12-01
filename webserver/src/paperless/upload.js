import React, { useRef, useState } from "react";
import "../Upload.css";

function UploadPage() {

    const [selectedFile, setSelectedFile] = useState(null);

    //gets called after uploading file
    const handleFileChange = (event) => {
        const file = event.target.files[0];
        if (file) {
            setSelectedFile(file);
        }
    };

    const uploadFile = async () => {
        const file = selectedFile;
        const author = document.getElementById("input_author").value;
        const description = document.getElementById("input_description").value;
        const fileUrl = document.getElementById("input_path").value;

        //----------------------------------

        const currentDate = new Date().toISOString().split('T')[0];
        const fileType = file.type.split('/')[1];
        //const fileSizeMB = (file.size / (1024 * 1024)).toFixed(2);
        const fileSize = file.size;
        const title = file.name.split('.')[0];
        const uid = crypto.randomUUID();

        const uploadDocument = '{"id": "' + uid + '", "title": "' + title + '", "author": "' +
                                author + '", "description": "' + description + '", "uploadDate": "' +
                                currentDate + '", "fileType": "' + fileType + '", "fileSize": ' +
                                fileSize + ', "fileUrl": "' + fileUrl + '"}'

        console.log(uploadDocument);

        const formData = new FormData();
        formData.append('document', uploadDocument);
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
            <div className="form-container">
                <div className="form-row">
                    <label className="label">File:</label>
                    <input id="input_file" className="input-field" type="file" accept=".pdf" onChange={handleFileChange}/>
                </div>
                <div className="form-row">
                    <label className="label">Author:</label>
                    <input id="input_author" className="input-field" type="text" />
                </div>
                <div className="form-row">
                    <label className="label">Description:</label>
                    <textarea id="input_description"></textarea>
                </div>
                <div className="form-row">
                    <label className="label">Path:</label>
                    <input id="input_path" className="input-field" type="text" />
                </div>
                <button className="upload-button" onClick={uploadFile}>Upload</button>
            </div>
        </div>
    );
}

export default UploadPage;

