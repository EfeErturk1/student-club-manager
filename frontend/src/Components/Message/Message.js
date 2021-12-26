import React from 'react'
import "./Message.css"
const Message = (props) => {
    return (
        <div className='message'>
            <div className="d-flex">
                <h6>Ege</h6>
                <p className='mx-2'>tarih</p>
            </div>
            {/*replying message here*/}
            <p className="mx-3">Anim adipisicing magna deserunt adsicing magna deserunt ad. sicing magna deserunt ad. sicing magna deserunt ad..</p>
        </div>
    )
}

export default Message
