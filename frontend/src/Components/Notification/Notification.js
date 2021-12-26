import React from 'react'
import "./Notification.css"

const Notification = (props) => {
    return (
        <div>
            <div className="notification">
                <div className="notification-header">
                    <p>{
                        props.club
                    }</p>
                    <p>{
                        props.date
                    }</p>
                </div>
                <div className="notification-body">
                    <p>{
                        props.description
                    }</p>
                    {
                    props.isReq ? <p>{
                        props.pending
                    }</p> : <p></p>
                } </div>
            </div>
        </div>
    )
}

export default Notification
