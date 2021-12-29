import React, {useState} from 'react'


import "./StudentPreview.css"

const StudentPreview = (props) => {
    var studentPos;

    const [studentId, setStudentId] = useState(null)
    const editRole = () => {
        setStudentId(props.id)
    }
    const cancel = () => {
        setStudentId(null)
    }
    const remove = () => {
        if (studentPos == "The President") {
            window.alert("You cannot remove club president directly")
            return;
        }
        fetch("http://localhost:8080/club/leaveClub", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${
                    localStorage.token
                }`
            },
            body: JSON.stringify(
                {studentId, clubId: localStorage.clubId}
            ),
            credentials: "include"
        },).then((r) => {
            if (r.ok) {
                window.location.reload()
                return r;
            }
            if (r.status === 401 || r.status === 403 || r.status === 500) {
                return Promise.reject(new Error("Bir hata oluştu " + r.status));
            }
            return Promise.reject(new Error("Bilinmeyen bir hata oluştu."));
        }).catch((e) => {
            console.log(e.message);
        });


    }
    const promote = () => {
    
        fetch("http://localhost:8080/clubRole/promote", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${
                    localStorage.token
                }`
            },
            body: JSON.stringify(
                {studentId, clubId: localStorage.clubId}
            ),
            credentials: "include"
        },).then((r) => {
            if (r.ok) {
                window.location.reload()
                return r;
            }

            if (r.status === 401 || r.status === 403 || r.status === 500) {
                return Promise.reject(new Error("Bir hata oluştu " + r.status));
            }
            return Promise.reject(new Error("Bilinmeyen bir hata oluştu."));
        }).catch((e) => {
            console.log(e.message);
        });
    }
    const demote = () => {
       
        fetch("http://localhost:8080/clubRole/demote", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${
                    localStorage.token
                }`
            },
            body: JSON.stringify(
                {studentId, clubId: localStorage.clubId}
            ),
            credentials: "include"
        },).then((r) => {
            if (r.ok) {
                window.location.reload()
                return r;
            }
            if (r.status === 401 || r.status === 403 || r.status === 500) {
                return Promise.reject(new Error("Bir hata oluştu " + r.status));
            }
            return Promise.reject(new Error("Bilinmeyen bir hata oluştu."));
        }).catch((e) => {
            console.log(e.message);
        });

    }

    if (props.position == "MEMBER") 
        studentPos = "Regular Member"
     else if (props.position == "ACTIVE_MEMBER") 
        studentPos = "Active Member"
     else if (props.position == "BOARD_MEMBER") 
        studentPos = "Director Board Member"
     else if (props.position == "PRESIDENT") 
        studentPos = "The President"
     else 
        studentPos = "Something went wrong!"

    return (
        <div> {
            studentId == null ? <div className="st-prev-container">
                <p> {
                    props.id
                } </p>
                <p>{
                    props.name
                }
                    - GE 250/251: {
                    props.department
                }
                    - {studentPos}</p>
                <button onClick={editRole}
                    className="btn btn-primary btn-block">Edit Role</button>
            </div> : <div className="d-flex flex-column justify-content-center align-items-center st-edit-role">
                <p>Current Role</p>
                <div className="d-flex flex-row justify-content-around align-items-around">
                    <button onClick={remove}
                        className="mx-1 btn btn-primary btn-block">Remove Froum Club</button>
                    <button onClick={demote}
                        className="mx-1 btn btn-primary btn-block">Demote</button>
                    <button onClick={promote}
                        className="mx-1 btn btn-primary btn-block">Promote</button>
                    <button onClick={cancel}
                        className="mx-3 btn btn-primary btn-block cancel">Cancel</button>
                </div>
            </div>
        } </div>


    )
}

export default StudentPreview
