import React from 'react'
import "./Club.css"
import {confirm} from "react-confirm-box";
import {useHistory} from 'react-router-dom'
const Club = (props) => {


    const clubId = props.id;
    let history = useHistory();
    const joinClub = () => {

        fetch("http://localhost:8080/club/joinClub", {
            method: "POST",
            headers: {
                "Content-type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify(
                {studentId: localStorage.id, clubId: clubId}
            )
        }).then((r) => {
            if (r.ok) {
                window.location.reload();
                return r;
            }
            if (r.status === 401 || r.status === 403 || r.status === 500) {
                return Promise.reject(new Error("hata oluştu"));
            }
            return Promise.reject(new Error("bilinmeyen hata"));
        }).
        then((r) => r.json())
        .catch((e) => {console.log(e)  });
    };

    const deleteClub = async () => {

        const result = await confirm("Are you sure you want to delete your account?");
        if (result) {
            fetch("http://localhost:8080/club/deleteClub", {
                method: "POST",
                headers: {
                    "Content-type": "application/json",
                    "Accept": "application/json"
                },
                body: JSON.stringify(
                    {id: clubId}
                )
            }).then((r) => {
                
                if (r.ok) {
                    
                    window.location.reload();
                    return r;
                }
                if (r.status === 401 || r.status === 403 || r.status === 500) {
                    return Promise.reject(new Error("hata oluştu"));
                }
                return Promise.reject(new Error("bilinmeyen hata"));
            }).then((r) => r.json()).then((response) => {
            }).catch((e) => {
                console.log( e);
            });
            return;
        }
        console.log("You click No!");
    };

    const leaveClub = () => {
        fetch("http://localhost:8080/club/leaveClub", {
            method: "POST",
            headers: {
                "Content-type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify(
                {studentId: localStorage.id, clubId: clubId}
            )
        }).then((r) => {
            if (r.ok) {
                window.location.reload();
                return r;
            }
            if (r.status === 401 || r.status === 403 || r.status === 500) {
                return Promise.reject(new Error("hata oluştu"));
            }
            return Promise.reject(new Error("bilinmeyen hata"));
        }).then((r) => r.json()).then((response) => {
           
        }).catch((e) => {
            console.log( e);
        });
    }

    const visitClub = () => {

        fetch("http://localhost:8080/club/clubView?id=" + clubId, {
            method: "GET",
            headers: {
                "Content-type": "application/json",
                "Accept": "application/json",
                "Authorization": `Bearer ${
                    localStorage.token
                }`
            }

        }).then((r) => {
            if (r.ok) {
                return r;
            }
            if (r.status === 401 || r.status === 403 || r.status === 500) {
                return Promise.reject(new Error("hata oluştu"));
            }
            return Promise.reject(new Error("bilinmeyen hata"));
        }).then((r) => r.json()).then((r) => {

            const clubRoles = r[0].roles;
            const role = clubRoles.filter(x => x.studentId == localStorage.id)
            const roleSt = (role[0]["name"])
            return roleSt
        }).catch((e) => {
            console.log( e);
        }).then((role) => {
            // is not a member or regular member
            if (JSON.stringify(props.isMember) == "[]" || role.toString() == "MEMBER") { 
                localStorage.setItem("clubId", clubId)
                props.setNav2(2)
                history.push("/view-club");
                // active member or higher member
            } else { 
                localStorage.setItem("onclub", "true");
                localStorage.setItem("clubId", clubId);
                localStorage.setItem("roleOfStudent", role);
                history.push("/club/home");
                window.location.reload();

            }
        })


    };
    return (
        <div>
            <div className="club-container">

                <div className="club-header">
                    <img className="pp"
                        src={
                            props.pp
                        }/>
                    <p>{
                        props.name
                    }</p>
                </div>
                <div className="club-body">
                    <p>{
                        props.total_events
                    }
                        {'\u00A0'}
                        Total Events</p>
                    <div className="container">
                        <div className="club-body-bottom row">
                            <div className="col-md-8 club-body-left">
                                <p> {
                                    props.description
                                } </p>
                            </div>
                            <div className="col-md-2 club-body-right">
                                {
                                props.isAdmin ? <div>
                                    <button className="btn btn-primary btn-block">Change Advisor</button>
                                    <button onClick={deleteClub}
                                        className="mt-2 btn btn-primary btn-block">Delete Club</button>
                                </div> : <div> {
                                    JSON.stringify(props.isMember) == "[]" ? <button onClick={joinClub}
                                        className="btn btn-primary btn-block">Join</button> : <button onClick={leaveClub}
                                        className="mt-2 btn btn-primary btn-block">Leave</button>
                                }
                                    <button onClick={visitClub}
                                        className="mt-2 btn btn-primary btn-block">Visit Club</button>

                                </div>
                            } </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}
export default Club
