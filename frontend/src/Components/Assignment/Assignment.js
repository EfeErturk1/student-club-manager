import React, {useEffect, useState} from 'react'
import { useHistory } from 'react-router-dom';
import { Link } from 'react-router-dom';
import "./Assignment.css"


const Assignment = (props) => {
    const eventDate = props.date
    const year = eventDate.substring(0, eventDate.indexOf("-"))
    const month = eventDate.substring(eventDate.indexOf("-")+1, eventDate.length-3)
    const day = eventDate.substring(eventDate.length-2)
    const eventDateFormatted = day + "/" + month + "/" + year
    const [pdfUrl, setPdfUrl] = useState(null);
    const [view, setView] = useState(false);
    const fileName = props.file;
    useEffect(() => {
        console.log(fileName);

        fetch("http://localhost:8080/files/" + fileName, {

            method: "get",
            headers: {
                "Authorization": `Bearer ${
                    localStorage.token
                }`,
            },
            credentials: "include"
        }, ).then((r) => {
            if (r.ok) {
                return r;
            }
            if (r.status === 401 || r.status === 403 || r.status === 500) {
                return Promise.reject(new Error("Bir hata oluştu " + r.status));
            }
            return Promise.reject(new Error("Bilinmeyen bir hata oluştu."));
        }).then((r) => r.blob()).then((r) => {
            console.log('hereeeee');
            var binaryData = [];
            binaryData.push(r);
            setPdfUrl(URL.createObjectURL(new Blob(binaryData, {
                type: "application/pdf"
            })));
            console.log(pdfUrl);

        }).catch((e) => {
            console.log(e.message);
        });
    }, []);
    return (
        <div>
            <div>
                <div className="assignment">
                    <div className="assignment-header mb-3">
                        <div className='d-flex flex-row'>
                            <div className="d-flex flex-column assignment_club_info">
                                <img className=" assignment_pp"
                                     src={
                                         props.pp
                                     }/>
                                <p>{
                                    props.club
                                }</p>
                            </div>
                            <p>{
                                props.name
                            }</p>
                        </div>
                        <p>due to {
                            eventDateFormatted
                        }</p>

                        <a href={pdfUrl} download>Click to download</a>

                    </div>
                    <div className="assignment-body">
                        <div className="d-flex flex-column"><p>{
                            props.description
                        }</p>
                            {
                                view?<p>{
                                    "Submission Comment: " + props.submissionDes
                                }</p>:<></>
                            }
                        </div>
                        {localStorage.getItem("clubId") != null && localStorage.getItem("clubId") != 0 ? pdfUrl == null ?
                                <button className="btn btn-primary btn-block" disabled>Not Submitted Yet</button>:
                                <><button className="btn btn-primary btn-block" disabled>Submitted</button><button onClick={() => setView(!view)} className="btn btn-primary btn-block" >View Comment</button></>

                            :
                            pdfUrl != null ? <button className="btn btn-primary btn-block" disabled>Done</button> :

                                <Link to={{
                                    pathname: "/uploadAssignment", state: {
                                        assignmentId: props.id,
                                    }
                                }}>
                                    <button className="btn btn-primary btn-block">Do It!</button>
                                </Link>

                        }
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Assignment
