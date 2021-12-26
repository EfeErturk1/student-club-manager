import React from "react";
import {useState} from "react";
import {useHistory} from "react-router-dom";
import {useLocation} from "react-router";
import {useEffect} from "react";


const UploadAssignment = (props) => {
    let data = useLocation();
    const [file, setFile] = useState(null);
    const [pdfUrl, setPdfUrl] = useState(null);
    const [submission, setSubmission] = useState(null);
    const history = useHistory();
    const assignmentId = data.state.assignmentId;

    const handleSubmit = (event) => {
        event.preventDefault();

        let formData = new FormData();

        fetch("http://localhost:8080/assignment/updateSubmission", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${
                    localStorage.token
                }`
            },
            body: JSON.stringify(
                {description: submission, id: assignmentId}
            ),

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
            history.push("/assignments");
        }).catch((e) => {
            console.log(e.message);
        });

        formData.append('file', file);

        fetch("http://localhost:8080/uploadAssignmentDocument?id=" + assignmentId, {
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

    const handleFile = (event) => {
        setFile(event.target.files[0]);
    };

    return <div className='create-club-container'>
        <div className="crate-event">
            <div className="create-event-header">
                <h3>Upload Assignment</h3>
            </div>
            <div className="create-event-body ">
                <form className="d-flex flex-column"
                    onSubmit={handleSubmit}
                    enctype="multipart/form-data">
                    <label forDescription='description'>Description</label>
                    <input type="mt-3 text" name='studentName'
                        placeholder={"Describe what you uploaded"}
                        className="form-control"
                        onChange={
                            e => setSubmission(e.target.value)
                        }/>

                    <label forName='file_area'>Upload your file below</label>
                    <input type='file' name='file_area'
                        onChange={handleFile}
                        multiple/> {/* <UploadFiles></UploadFiles> */}
                    <button className="mt-3 btn btn-primary btn-block" type='submit'>Update Assignment</button>

                </form>
            </div>
        </div>
    </div>

}

export default UploadAssignment;
