import React, {useEffect, useState} from 'react'
import "./Profile.css"
import {StNav, Event, Club} from "../../../Components"
import {confirm} from "react-confirm-box";
import {useHistory} from 'react-router-dom';
import {Link} from 'react-router-dom';
import defaultPP from "../../../Assets/ppDefault.jpg"

const Profile = (props) => {

    let history = useHistory();
    const [clubs, setmyClubs] = useState([]);
    const studentId = localStorage.getItem("id");
    const [events, setmyEvents] = useState([]);
    const [studentName, setStudentName] = useState('');
    const [studentGe250, setStudentGe250] = useState(0);

    const [myClubs, setMyClubs] = useState([]);

    const [picLink, setPicLink] = useState(null);
    const [picture, setPicture] = useState(null);
    const [imageData, setImageData] = React.useState({});
    const [imageUrl, setImageUrl] = useState(null);

    useEffect(() => {
        fetch("http://localhost:8080/club/getStudentClub?id=" + studentId, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${
                    localStorage.token
                }`
            },
            credentials: "include"
        }).then((r) => {
            if (r.ok) {
               
                return r;
            }
            if (r.status === 401 || r.status === 403 || r.status === 500) {
                return Promise.reject(new Error("Bir hata oluştu " + r.status));
            } else 
                return Promise.reject(new Error("Bilinmeyen bir hata oluştu."));
            

        }).then((r) => r.json()).then((r) => {
          
            setMyClubs(r);
            setPicLink(r.photoName);
            setPicture(`http://localhost:8080/files/${
                r.photoName
            }`);
     

        }).catch((e) => {
            console.log(e.message);
        });
    }, [])

    useEffect(() => {
     
        fetch("http://localhost:8080/files/" + picLink, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${
                    localStorage.token
                }`
            },
            credentials: "include"
        }).then((r) => {
            if (r.ok) {
                
                return r;
            }
            if (r.status === 401 || r.status === 403 || r.status === 500) {
                return Promise.reject(new Error("Bir hata oluştu " + r.status));
            } else {
                return Promise.reject(new Error("Bilinmeyen bir hata oluştu."));
            }
        }).then(r => r.blob()).then((r) => {

           
            var binaryData = [];
            binaryData.push(r);
            setImageUrl(URL.createObjectURL(new Blob(binaryData, {type: "application/json"})))
            setImageData(r);
           
        }).catch((e) => {
            console.log(e.message);
        });
    }, [picLink]);


    const deleteProfile = async () => {
        const result = await confirm("Are you sure you want to delete your account?");
        if (result) {
            console.log("You click yes!");
           
            fetch("http://localhost:8080/auth/deleteStudent", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${
                        localStorage.token
                    }`
                },
                body: JSON.stringify(
                    {id: localStorage.id}
                ),

                credentials: "include"
            },).then((r) => {
                if (r.ok) {
                    history.push("/")
                    localStorage.clear()
                    console.log(r);
                    window.location.reload()
                    return r;
                }
                if (r.status === 401 || r.status === 403 || r.status === 500) {
                    return Promise.reject(new Error("Bir hata oluştu " + r.status));
                }
                return Promise.reject(new Error("Bilinmeyen bir hata oluştu."));
            }).then((r) => r.json()).then((r) => {
             
                setmyEvents(r)
            }).catch((e) => {
                console.log(e.message);
            });
            return;
        }
        console.log("You click No!");
    };

    useEffect(() => {

        fetch("http://localhost:8080/auth/getStudentInfo?id=" + studentId, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${
                    localStorage.token
                }`
            },
            credentials: "include"
        }).then((r) => {
            if (r.ok) {
                
                return r;
            }
            if (r.status === 401 || r.status === 403 || r.status === 500) {
                return Promise.reject(new Error("Bir hata oluştu " + r.status));
            } else {
                return Promise.reject(new Error("Bilinmeyen bir hata oluştu."));
            }
        }).then((r) => r.json()).then((r) => {
            
            setStudentName(r.name);
            setStudentGe250(r.ge250);
            setPicLink(r.photoName);

        }).catch((e) => {
            console.log(e.message);
        });
    }, []);

    useEffect(() => {

        fetch("http://localhost:8080/club/getStudentClub?id=" + studentId, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${
                    localStorage.token
                }`
            },
            credentials: "include"
        }).then((r) => {
            if (r.ok) {
                
                return r;
            }
            if (r.status === 401 || r.status === 403 || r.status === 500) {
                return Promise.reject(new Error("Bir hata oluştu " + r.status));
            } else {
                return Promise.reject(new Error("Bilinmeyen bir hata oluştu."));
            }
        }).then((r) => r.json()).then((r) => {
            
            setmyClubs(r)

        }).catch((e) => {
            console.log(e.message);
        });
    }, []);

    useEffect(() => {

        fetch("http://localhost:8080/event/myEvents?id=" + studentId, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${
                    localStorage.token
                }`
            },
            credentials: "include"
        }, console.log("olmayan")).then((r) => {
            if (r.ok) {
               
                return r;
            }
            if (r.status === 401 || r.status === 403 || r.status === 500) {
                return Promise.reject(new Error("Bir hata oluştu " + r.status));
            }
            return Promise.reject(new Error("Bilinmeyen bir hata oluştu."));
        }).then((r) => r.json()).then((r) => {
            
            setmyEvents(r)
        }).catch((e) => {
            console.log(e.message);
        });
    }, []);


    return (
        <div className="st-body-grid">
            <div className="flex_cont">
                <button onClick={deleteProfile}
                    className="btn btn-primary btn-block del_my_acc">
                    Delete My Account
                </button>
                <div className="container">
                    <div className="row header_bio">
                        <div className="col-md-4 header_left">
                            <img className="pp_class"
                                src={
                                    imageUrl ? imageUrl : defaultPP
                                }/>
                        </div>
                        <div className="col-md-8 ">
                            <div className="header_right">
                                <h3>{studentName}</h3>
                                <p>GE 250: {studentGe250}</p>
                                <Link to={
                                    {
                                        pathname: '/editStudentProfile',
                                        state: {
                                            name: studentName,
                                            ge250: studentGe250
                                        }
                                    }
                                }>
                                    <button className="btn btn-primary btn-block">Edit Profile</button>
                                </Link>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div className="container">
                <div className="row">
                    <div className="profile_clubs col-lg-6">
                        {
                        clubs.map((club) => (
                            
                            <Club setNav2={
                                    props.setNav2
                                }
                                name={
                                    club.name
                                }
                                roles={
                                    JSON.stringify(club.roles)
                                }
                                total_events={
                                    club.numberOfEvents
                                }
                                description={
                                    club.description.substring(0, 30)
                                }
                                pp={
                                    club.pp
                                }
                                id={
                                    club.id
                                }
                                isMember={
                                    myClubs.filter(a => a.id == club.id)
                                }/>
                        ))
                    } </div>
                    <div className="profile_events col-lg-6">
                        {
                        events.map((event) => (
                            <Event endDate={
                                    event.eventFinish
                                }
                                status={
                                    event.status
                                }
                                eventId={
                                    event.eventId
                                }
                                club={
                                    event.club
                                }
                                ge250={
                                    event.ge250
                                }
                                name={
                                    event.name
                                }
                                clubName={
                                    event.clubName
                                }
                                quota={
                                    event.quota
                                }
                                remainingQuota={
                                    event.remainingQuota
                                }
                                date={
                                    event.eventDate
                                }
                                description={
                                    event.description
                                }
                                img={"https://i.pinimg.com/736x/b2/8a/ee/b28aee3a7e645b68bcebc83f780af2a5.jpg"}
                                startClock={
                                    event.startClock
                                }
                                endClock={
                                    event.endClock
                                }
                                isInEvent={
                                    !(events.filter(a => a.eventId == event.eventId).length == 0)
                                }/>
                        ))
                    } </div>
                </div>
            </div>
        </div>
    )
}

export default Profile
