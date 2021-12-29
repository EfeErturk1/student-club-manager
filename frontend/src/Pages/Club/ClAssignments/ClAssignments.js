import React, {useEffect, useState} from 'react'
import "./ClAssignments.css"
import { Assignment} from "../../../Components"
import { useHistory } from 'react-router-dom';



const ClAssignments = (props) => {
    let history = useHistory()
    const [assignments, setAssignments] = useState([]);

    useEffect(() => {


        fetch("http://localhost:8080/assignment/getClubAssignment?id=" + localStorage.getItem("clubId"), {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${
                    localStorage.token
                }`
            },
            credentials: "include"
        },).then((r) => {
            if (r.ok) {
                return r;
            }
            if (r.status === 401 || r.status === 403 || r.status === 500) {
                return Promise.reject(new Error("Bir hata oluştu " + r.status));
            }
            return Promise.reject(new Error("Bilinmeyen bir hata oluştu."));
        }).then((r) => r.json()).then((r) => {
            setAssignments(r)
        }).catch((e) => {
            console.log(e.message);
        });



    }, []);
    return (
        <div className='d-flex flex-column justify-content align-items-center'>
            <button
                onClick={()=>{props.setNav(2); history.push("create-assignment")}}

                className="m-4 btn btn-primary btn-block center">Create New Assignment</button>

            <div className="cl-assignment">

                <div className="d-flex flex-column-reverse">
                    {
                        assignments.map((assignment) => (
                            <Assignment club={
                                assignment.clubName
                            }
                                        submissionDes = {
                                            assignment.submissionDes
                                        }
                                        name ={assignment.name}
                                        date={
                                            assignment.due_date.substring(0, assignment.due_date.indexOf("T"))
                                        }

                                        description={
                                            assignment.description
                                        }
                                        id = {
                                            assignment.assignmentId
                                        }
                                        pp={
                                            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQxciyGtNprXskRvTxL6sLgglWj5MXb5YQGcw&usqp=CAU"
                                        }
                                        file={
                                            assignment.file
                                        }/>
                        ))
                    }
                </div>
            </div>


        </div>
    )
}

export default ClAssignments

