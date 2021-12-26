import React, {useState} from 'react'
import "./Forum.css"
import {Message} from "../"

const Forum = () => {
    const [message, setMessage] = useState("")
    return (
        <div className="forum ">
            <div className="forum-header d-flex flex-row justif-content-between align-items-between">
                <h4 className='m-3'>ACM</h4>
                <p className="m-3">Ut ut eu voluptate est ullamco.</p>
            </div>
            <div className="forum-body d-flex flex-column align-items-center justify-content-center ">
                <div className="messages">
                    <Message/>
                    <Message/>
                    <Message/>
                    <Message/>
                    <Message/>
                </div>
                <button className="mx-3 btn btn-primary btn-block">View Full Forum</button>
            </div>

            <form className="send">
                <textarea rows="3" cols="60" type="text" className="mt-2 form-control" placeholder="Your Message"
                    onChange={
                        e => setMessage(e.target.value)
                }></textarea>
                <button className="mx-3 btn btn-primary btn-block">Send</button>
            </form>
        </div>
    )
}

export default Forum
