import React, {useState, useEffect} from 'react';
import {Notification} from "../../../Components"
import "./StNotifications.css"

const StNotifications = () => {
    const [nots, setNots] = useState([]);
    useEffect(() => {
        fetch("http://localhost:8080/notification/getStudentNotification?id=" + localStorage.id, {
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
            setNots(r);
        }).catch((e) => {
            console.log(e.message);
        });
    }, [])

    return (
        <div>
            <h3 className="text-center m-3">Notifications</h3>
            <div className="st-notifications">
                <div className="d-flex flex-column-reverse">
                    {
                    nots.map((notification) => <Notification club={
                            notification.club
                        }
                        date={
                            notification.date
                        }
                        isReq={
                            notification.request
                        }
                        pending={
                            notification.pending
                        }
                        description={
                            notification.description
                        }
                        notification={
                            notification.notification
                        }/>)
                } </div>
            </div>
        </div>
    )
}

export default StNotifications
