import React, {useEffect, useState} from 'react'
import {StNav, Event} from "../../../Components"
import "./StHome.css"

const StHome = () => {
    const [events, setEvents] = useState([]);
    const [myEvents, setMyEvents] = useState([]);
    useEffect(() => {
        fetch("http://localhost:8080/event/allEvents", {
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
            } else if (r.status === 401 || r.status === 403 || r.status === 500) {
                return Promise.reject(new Error("Bir hata oluştu " + r.status));
            } else {
                return Promise.reject(new Error("Bilinmeyen bir hata oluştu."));
            }
        }).then((r) => r.json()).then((r) => {
            setEvents(r)
        }).catch((e) => {
            console.log(e.message);
        });


        fetch("http://localhost:8080/event/myEvents?id=" + localStorage.id, {
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
            } else if (r.status === 401 || r.status === 403 || r.status === 500) {
                return Promise.reject(new Error("Bir hata oluştu " + r.status));
            } else {
                return Promise.reject(new Error("Bilinmeyen bir hata oluştu."));
            }
        }).then((r) => r.json()).then((r) => {
            setMyEvents(r)
        }).catch((e) => {
            console.log(e.message);
        });
    }, []);
console.log(events);
    return (
        <div>
            <h3 className='m-3 text-center'>Events</h3>
            <div className="st-home ">
                <div className="d-flex flex-column-reverse">
                    {
                    events.map((event) => (

                        <Event isStudent={true}
                            isClub={false}
                            isAdvisor={false}
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
                            photos={
                                event.photos
                            }
                            endDate={
                                event.eventFinish
                            }
                            description={
                                event.description
                            }
                           
                           
                            isInEvent={
                                !(myEvents.filter(a => a.eventId == event.eventId).length == 0)
                            }/>

                    ))
                } </div>

            </div>
        </div>

    )
}


export default StHome
