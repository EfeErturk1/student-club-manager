import React, {useEffect, useState} from 'react'
import {Event} from "../../../Components"
import "./AdvHome.css"

const AdvHome = () => {

    const [feed, setFeed] = useState([]);
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
            var myClubsEvents = r.filter(event => event.clubId == localStorage.clubId)
            setFeed(myClubsEvents)

        }).catch((e) => {
            console.log(e.message);
        });
    }, []);

    return (
        <div>
            <div className="adv-home">
                <div className="d-flex flex-column-reverse">
                    {
                    feed.map((event) => (
                        <Event isStudent={false}
                            isClub={false}
                            isAdvisor={true}
                            status={
                                event.status
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
                            endDate={
                                event.eventFinish
                            }
                            ge250={
                                event.ge250
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
                            photos={
                                event.photos
                            }
                            startClock={
                                event.startClock
                            }
                            endClock={
                                event.endClock
                            }/>
                    ))
                } </div>
            </div>
        </div>
    )
}

export default AdvHome
