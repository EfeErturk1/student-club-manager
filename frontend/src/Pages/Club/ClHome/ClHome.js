import React, {useState, useEffect} from 'react';
import {Event} from "../../../Components"

const ClHome = () => {

    const [viewAllEvents, setViewAllEvents] = useState(true);
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
            setEvents(r);

        }).catch((e) => {
            console.log(e.message);
        });

        fetch("http://localhost:8080/event/clubEvents?id=" + localStorage.getItem('clubId'), {
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
            for (var i = 0; i < r.length; i++) {
                var obj = r[i];
                obj["club"] = localStorage.clubId;

            }
            setMyEvents(r)

        }).catch((e) => {
            console.log(e.message);
        });

    }, []);

    return (

        <div>
            <div className="dflex m-3 d-flex justify-content-center">
                <button className="btn btn-primary btn-block mx-3"
                    onClick={
                        () => {
                            setViewAllEvents(true)
                        }
                }>All Events</button>
                <button className="btn btn-primary btn-block"
                    onClick={
                        () => {
                            setViewAllEvents(false)
                        }
                }>My Events</button>
            </div>
            <div className="st-notifications">
                <div className="d-flex flex-column-reverse">
                    {
                    viewAllEvents ? events.map((event) => (

                        <div>
                            <Event isStudent={false}
                                endDate={
                                    event.eventFinish
                                }
                                isClub={true}
                                isAdvisor={false}
                                status={
                                    event.status
                                }
                                eventId={
                                    event.eventId
                                }
                                club={
                                    event.clubId
                                }
                                ge250={
                                    event.ge250
                                }
                                eventId={
                                    event.eventId
                                }
                                club={
                                    event.club
                                }
                                name={
                                    event.name
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
                                clubName={
                                    event.clubName
                                }

                             
                                isInEvent={
                                    !(myEvents.filter(a => a.eventId == event.eventId).length == 0)
                                }

                                img={
                                    event.img
                                }
                                date={
                                    event.eventDate
                                }
                                endClock={
                                    event.eventFinish
                                }/>
                        </div>

                    )) : myEvents.map((event) => (
                        <div>
                            <Event isStudent={false}
                                clubName={
                                    event.clubName
                                }
                                endDate={
                                    event.eventFinish
                                }
                                isClub={true}
                                isAdvisor={false}
                                eventId={
                                    event.eventId
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

                                startClock={
                                    event.startClock
                                }
                                endClock={
                                    event.endClock
                                }
                                isInEvent={
                                    !(myEvents.filter(a => a.eventId == event.eventId).length == 0)
                                }

                                img={
                                    event.img
                                }
                                date={
                                    event.eventDate
                                }
                                endClock={
                                    event.eventFinish
                                }/>
                        </div>

                    ))
                } </div>
            </div>
        </div>
    )
}

export default ClHome
