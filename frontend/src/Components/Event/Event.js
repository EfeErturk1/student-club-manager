import React, {useEffect, useState} from 'react'
import "./Event.css"
import {Link} from 'react-router-dom';

const Event = (props) => {
    const eventId = props.eventId;
    const [picLink, setPicLink] = useState(null);
    const [picture, setPicture] = useState(null);
    const [imageData, setImageData] = React.useState({});
    const [imageUrl, setImageUrl] = useState(null);

    useEffect(() => {
        fetch("http://localhost:8080/event/eventView?id=" + eventId, {
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
            setPicLink(r.photos);
            console.log("ege")
            console.log(r.photos)
            setPicture(`http://localhost:8080/files/${
                r.photos
            }`);
        
        }).catch((e) => {
            console.log(e.message);
        });
    }, [])


    useEffect(() => {
        fetch("http://localhost:8080/files/" + props.photos, {
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

    const joinEvent = () => {
        const studentId = localStorage.getItem("id");
        fetch("http://localhost:8080/event/joinEvent", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${
                    localStorage.token
                }`
            },

            body: JSON.stringify(
                {studentId, eventId}
            )

        }).then((r) => {

            if (r.ok) {
                return r;
            } else if (r.status === 401 || r.status === 403 || r.status === 500) {

                return Promise.reject(new Error("Bir hata oluştu " + r.status));
            } else {
                return Promise.reject(new Error("Bilinmeyen bir hata oluştu."));
            }
        }).then((r) => r.json()).then((r) => {
            window.alert(r.message);
            window.location.reload()

        }).catch((e) => {
            console.log(e.message);
        });
    }

    const leaveEvent = () => {
        const studentId = localStorage.getItem("id");
        fetch("http://localhost:8080/event/leaveEvent", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${
                    localStorage.token
                }`
            },

            body: JSON.stringify(
                {studentId, eventId}
            )

        }).then((r) => {
            if (r.ok) {
                return r;
            } else if (r.status === 401 || r.status === 403 || r.status === 500) {

                return Promise.reject(new Error("Bir hata oluştu " + r.status));
            } else {
                return Promise.reject(new Error("Bilinmeyen bir hata oluştu."));
            }
        }).then((r) => r.json()).then((r) => {
            window.alert(r.message);
            window.location.reload()

        }).catch((e) => {
            console.log(e.message);
        });
    }

    const approveEvent = () => {

        fetch("http://localhost:8080/advisor/acceptEvent", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${
                    localStorage.token
                }`
            },

            body: JSON.stringify(
                {id: eventId}
            )
        }).then((r) => {
            if (r.ok) {
                return r;
            } else if (r.status === 401 || r.status === 403 || r.status === 500) {
                return Promise.reject(new Error("Bir hata oluştu " + r.status));
            } else {
                return Promise.reject(new Error("Bilinmeyen bir hata oluştu."));
            }
        }).then((r) => r.json()).then((r) => {

            window.alert(r.message);
            window.location.reload()
        }).catch((e) => {
            console.log(e.message);
        });
    }

    const declinEvent = () => {

        fetch("http://localhost:8080/advisor/rejectEvent", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${
                    localStorage.token
                }`
            },

            body: JSON.stringify(
                {id: eventId}
            )

        }).then((r) => {
            if (r.ok) {
                return r;
            } else if (r.status === 401 || r.status === 403 || r.status === 500) {

                return Promise.reject(new Error("Bir hata oluştu " + r.status));
            } else {
                return Promise.reject(new Error("Bilinmeyen bir hata oluştu."));
            }
        }).then((r) => r.json()).then((r) => {
            window.alert(r.message);
            window.location.reload()

        }).catch((e) => {
            console.log(e.message);
        });

    }

    const eventDate = props.date.substring(0, props.date.indexOf("T"))
    const year = eventDate.substring(0, eventDate.indexOf("-"))
    const month = eventDate.substring(eventDate.indexOf("-") + 1, eventDate.length - 3)
    const day = eventDate.substring(eventDate.length - 2)
    const eventDateFormatted = day + "/" + month + "/" + year
    const startClock = props.date.substring(props.date.indexOf("T") + 1, (props.date.length - 3))
    const endClock = props.endDate.substring(props.endDate.indexOf("T") + 1, (props.date.length - 3))

    if (props.isStudent && props.status != "ACCEPTED") {
        return (
            <div></div>
        )
    } else if (props.isClub && props.club != localStorage.clubId && props.status != "ACCEPTED"){
        return( <div></div>)
    }

    return (
        <div>
            <div className="event-container">
                <div className="event-header">
                    {
                    props.isClub && props.club == localStorage.clubId && props.status == "ACCEPTED" ? <h3 className='accepted'>
                        ACCEPTED
                    </h3> : props.isClub && props.club == localStorage.clubId && props.status == "REJECTED" ? <h3 className='rejected'>
                        REJECTED
                    </h3> : props.isClub && props.club == localStorage.clubId && props.status == "NOT_DECIDED" ? <h3 className='waiting'>
                        WAITING APPROVAL
                    </h3> : <></>
                }
                    <p>{
                        props.clubName
                    }</p>
                    <div className="d-flex flex-column">
                        <p>Max Quota: {
                            props.quota
                        }</p>
                        <p>Available Quota: {
                            props.remainingQuota
                        }</p>
                    </div>
                </div>
                <div>

                    <div className="event-body">
                        <div className="container">
                            <div className="row">
                                <div className="d-flex flex-row justify-content-between align-items-center">
                                    <h3>{
                                        props.name
                                    }</h3>
                                    <p>GE 250/251: {'\u00A0'}
                                        {
                                        props.ge250
                                    }</p>
                                </div>

                            </div>
                            <div className="row">
                                <div className="col-lg-4">
                                    <img className="pp_class"
                                        src={imageUrl}
                                        className="event-img"/>
                                </div>
                                <div className="col-lg-8">
                                    <div className="event-body-right">
                                        <p> {eventDateFormatted}</p>
                                        <p> {startClock}
                                            - {endClock}</p>
                                        <p>{
                                            props.description
                                        }</p>
                                        {
                                        (props.isAdvisor && !props.isStudent && !props.isClub) ?

                                        // if advsior
                                        props.status == "NOT_DECIDED" ?
                                        // if advsior and not decided event 
                                        <div className="d-flex flex-row ">
                                            <button onClick={approveEvent}
                                                className="btn btn-primary btn-block">Approve</button>
                                            <button onClick={declinEvent}
                                                className="mx-3 btn btn-primary btn-block">Decline</button>
                                        </div> :
                                        // if advsior but event is decided 
                                        <></>

                                        // end of advisor

                                        // if student 
                                        : props.isStudent ?

                                        // if student and already joined event
                                        props.isInEvent ? <button onClick={leaveEvent}
                                            className="btn btn-primary btn-block">Leave Event</button>

                                        // if student but did not joined event 
                                        : 
                                        <button onClick={joinEvent}
                                            className="btn btn-primary btn-block">Join Event</button>

                                        // end of student

                                        // if club 
                                        :
                                        // if club and that event is theirs
                                        props.isClub && props.club == localStorage.clubId ? <Link to={
                                            {
                                                pathname: '/editEvent',
                                                state: {
                                                    name: props.name,
                                                    ge250: props.ge250,
                                                    quota: props.quota,
                                                    start: props.startClock,
                                                    endClock: props.endClock,
                                                    description: props.description,
                                                    date: props.date,
                                                    id: eventId
                                                }
                                            }
                                        }>
                                            <button className="mx-3 btn btn-primary btn-block">Edit Event</button>
                                        </Link> :

                                        // if club but the event isn't theirs 
                                        <></>
                                    } </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Event
