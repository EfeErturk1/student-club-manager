import React, {useState, useEffect} from 'react'
import {ClubProfile, Event} from "../../../Components"
const AdvClubProfile = () => {
    const [description, setDescription] = useState("")
    const [name, setName] = useState("")
    const [clubId, setClubId] = useState(-1)
    const [myEvents, setMyEvents] = useState([]);
    const [numberOfEvents, setNumberOfEvents] = useState(0);
    const [numberOfMember, setNumberOfMembers] = useState(0);
    const [photoLink, setPhotoLink] = useState(null);

    useEffect(() => {
        
        fetch("http://localhost:8080/club/clubView?id=" + localStorage.clubId, {
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
            setDescription(r[0].description)
            setName(r[0].name)
            setClubId(r[0].id)
            setNumberOfEvents(r[0].numberOfEvents)
            setNumberOfMembers(r[0].roles.length)
            setPhotoLink(r[0].photos);

            return(r[0].id)
        }).then((id) => {

            fetch("http://localhost:8080/event/clubEvents?id=" + id, {
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
                    obj["club"] = id;

                }
                setMyEvents(r)

            }).catch((e) => {
                console.log(e.message);
            });
        }).catch((e) => {
            console.log(e.message);
        });


    }, []);

    return (
        <div>

            <div>
                <ClubProfile description={description}
                    name={name}
                    photo={photoLink}
                    numberOfEvents={numberOfEvents}
                    numberOfMember={numberOfMember}
                    clubId={clubId}/>

            </div>
            <div>


                <div className='d-flex flex-column align-items-center justify-content-center p-5'>
                    <h3>Club's Events</h3>
                    <div className="d-flex flex-column-reverse">
                        {
                        myEvents != [] && myEvents.map((event) => (
                            <Event isStudent={false}
                                endDate={
                                    event.eventFinish
                                }
                                isClub={false}
                                isAdvisor={true}
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
                                photos={
                                    event.photos
                                }
                                startClock={
                                    event.startClock
                                }
                                endClock={
                                    event.endClock
                                }
                                isInEvent={
                                    !(myEvents.filter(a => a.eventId == event.eventId).length == 0)
                                }/>
                        ))
                    } </div>
                </div>
            </div>
        </div>
    )
}

export default AdvClubProfile
