import React, {useState} from "react";
import {useEffect} from "react";

const DocumentUpload = (props) => {
    const [file, setFile] = useState(null);
    const [pdfUrl, setPdfUrl] = useState(null);

    const handleFile = (event) => {
        setFile(event.target.files[0]);
    };

    const handleSubmit = (event) => {
        event.preventDefault();

        let formData = new FormData();
        formData.append('file', file);

        fetch("http://localhost:8080/upload", {
            method: "POST",
            headers: {
                "Authorization": `Bearer ${
                    localStorage.token
                }`
            },
            body: formData,
            credentials: "include"
        },).then((r) => {
            if (r.ok) {
                return r;
            }
            if (r.status === 401 || r.status === 403 || r.status === 500) {
                return Promise.reject(new Error("Bir hata oluştu " + r.status));
            }
            return Promise.reject(new Error("Bilinmeyen bir hata oluştu."));
        }).then((r) => r.json()).then((r) => {  

        }).catch((e) => {
            console.log(e.message);
        });

    };

    useEffect(() => {
        fetch("http://localhost:8080/files/230mt2 Fall 2015.pdf", {
            method: "get",
            headers: {
                "Authorization": `Bearer ${
                    localStorage.token
                }`
            },
            credentials: "include"
        },).then((r) => {
            if (r.ok) {
                return r;
            }
            if (r.status === 401 || r.status === 403 || r.status === 500) {
                return Promise.reject(new Error("Bir hata oluştu " + r.status));
            }
            return Promise.reject(new Error("Bilinmeyen bir hata oluştu."));
        }).then((r) => r.blob()).then((r) => {
            var binaryData = [];
            binaryData.push(r);
            setPdfUrl(URL.createObjectURL(new Blob(binaryData, {type: "application/pdf"})));
        }).catch((e) => {
            console.log(e.message);
        });
    }, []);

    return <div>
        <form className="d-flex flex-column"
            onSubmit={handleSubmit}
            enctype="multipart/form-data">
            <input type='file' name='file_area'
                onChange={handleFile}
                multiple/>
            <button className="mt-3 btn btn-primary btn-block" type='submit'>upload
            </button>
        </form>
        <a href={pdfUrl}
            download>Click to download</a>

    </div>
};
export default DocumentUpload;
