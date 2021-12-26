import React from "react";
import {useState} from "react";
import {useHistory} from "react-router-dom";
import {useLocation} from "react-router";
import {useEffect} from "react";


const EditProfile = (props) => {
    let data = useLocation();
    const [newName, setNewName] = useState(data.state.name);
    const [newGe250, setGe250] = useState(data.state.ge250);
    const [picture, setPicture] = useState(null);
    const studentId = localStorage.getItem('id');
    const history = useHistory();

    const handleSubmit = (event) => {
        event.preventDefault();

        let formData = new FormData();

        fetch("http://localhost:8080/auth/updateStudentProfile", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${
                    localStorage.token
                }`
            },
            body: JSON.stringify(
                {id: localStorage.id, name: newName, ge250: newGe250}
            ),

            credentials: "include"
        },).then((r) => {
            if (r.ok) {
                localStorage.clear()
                history.push("/")
                window.alert("Changes are saved! Please login again...")
                window.location.reload()
                return r;
            }
            if (r.status === 401 || r.status === 403 || r.status === 500) {
                return Promise.reject(new Error("Bir hata oluştu " + r.status));
            }
            return Promise.reject(new Error("Bilinmeyen bir hata oluştu."));
        }).then((r) => r.json()).then((r) => {
            history.push("/myProfile");
        }).catch((e) => {
            console.log(e.message);
        });

        formData.append('file', picture);

        fetch("http://localhost:8080/uploadProfilePic?id=" + studentId, {
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


    const handlePicture = (event) => {
        setPicture(event.target.files[0]);
    };

    useEffect(() => {
    }, [picture]);

    return <div className='create-club-container'>
        <div className="crate-event">
            <div className="create-event-header">
                <h3>Profile Page Edit</h3>
            </div>
            <div className="create-event-body ">
                <form className="d-flex flex-column"
                    onSubmit={handleSubmit}
                    enctype="multipart/form-data">
                    <label forName='studentName'>Your Name</label>
                    <input type="mt-3 text" name='studentName'
                        placeholder={
                            data.state.name
                        }
                        className="form-control"
                        onChange={
                            e => setNewName(e.target.value)
                        }/>

                    <label forName='file_area'>Submit A profile pic</label>
                    <input type='file' name='file_area'
                        onChange={handlePicture}
                        multiple/> {/* <UploadFiles></UploadFiles> */}
                    <button className="mt-3 btn btn-primary btn-block" type='submit'>Update Profile</button>

                </form>
            </div>
        </div>
    </div>

}

export default EditProfile;
