import React, {useState} from "react";
import {useHistory, useLocation} from "react-router-dom";

const EditClubProfile = (props) => {
    const location = useLocation();
    const [description, setDescription] = useState(location.state.description);
    const [name, setName] = useState(location.state.name);
    const [picture, setPicture] = useState(null);
    const history = useHistory();
    const clubId = location.state.id;

    const handleSubmit = (event) => {
        event.preventDefault();
        let formData = new FormData();

        fetch("http://localhost:8080/club/updateClub", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${
                    localStorage.token
                }`
            },
            body: JSON.stringify(
                {id: clubId, description}
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
            history.push("/club/home");
        }).catch((e) => {
            console.log(e.message);
        });


        formData.append('file', picture);

        fetch("http://localhost:8080/uploadClubPic?id=" + clubId, {
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
            console.log('uploaded');
        }).catch((e) => {
            console.log(e.message);
        });
    };

    const handlePicture = (event) => {
        setPicture(event.target.files[0]);
    }
    return <div className='create-club-container'>
        <div className="crate-event">
            <div className="create-event-header">
                <h3>Edit Club Profile</h3>
            </div>
            <div className="create-event-body ">
                <h3>{name}</h3>
                <form className="d-flex flex-column"
                    onSubmit={handleSubmit}
                    enctype="multipart/form-data">
                    <label forName='description'>Event Description</label>
                    <input type="mt-3 text" name='description' className="form-control"
                        placeholder={description}
                        onChange={
                            e => setDescription(e.target.value)
                        }/>
                    <label forName='photo'></label>
                    <input type='file' name='photo'
                        onChange={handlePicture}
                        multiple/>
                    <button className="mt-3 btn btn-primary btn-block" type='submit'>Update Club Profile</button>
                </form>
            </div>
        </div>
    </div>

};
export default EditClubProfile;
