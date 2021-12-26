import React, {useState, useEffect} from 'react'
import DatePicker from "react-datepicker"
import TimePicker from 'react-time-picker';
import {useHistory} from "react-router-dom";
import "./ClCreateAssignment.css"


const ClCreateAssignment = () => {
    const [description, setDescription] = useState("")
    const [dueDate, setDueDate] = useState(new Date())
    const [ids, setIds] = useState([]);
    const [startClock, setStartClock] = useState('10:30');
    const [clubName, setClubName] = useState([]);
    const [name, setName] = useState([]);
    let history = useHistory()

    useEffect(() => {
        fetch("http://localhost:8080/assignment/getNameOfClub?id=" + localStorage.getItem("id"), {
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
            setClubName(r)
        }).catch((e) => {
            console.log(e.message);
        });

        fetch("http://localhost:8080/club/getMembersIdsOfClub?id=" + localStorage.getItem("clubId"), {
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
            setIds(r)
        }).catch((e) => {
            console.log(e.message);
        });


    }, []);

    const handleSubmit = (event) => {
        event.preventDefault()
        var m1 = (dueDate.getMonth() + 1)
        if (dueDate.getMonth() < 9) 
            m1 = "0" + (
                dueDate.getMonth() + 1
            )
        
        var d1 = dueDate.getDate()
        if (dueDate.getDate() < 10) 
            d1 = "0" + dueDate.getDate()
        
        const startDateFormatted = dueDate.getFullYear() + "-" + m1 + "-" + d1 + "T" + startClock + ":00"
        if (description == "") {
            window.alert("None of the fields can be left empty!")
        } else {
            console.log("No bad credentials");
        }
        fetch("http://localhost:8080/assignment/addAssignment", {

            method: "POST",
            headers: {
                "Content-type": "application/json",
                "Accept": "application/json",
                "Authorization": `Bearer ${
                    localStorage.token
                }`
            },

            body: JSON.stringify(
                {
                    due_date: startDateFormatted,
                    name: name,
                    description: description,
                    clubId: localStorage.getItem("clubId"),
                    assignees: ids


                }
            )
        }).then((r) => {
            if (r.ok) {
                history.push("/club/assignments")
                window.location.reload()
            } else if (r.status === 401 || r.status === 403 || r.status === 500) {
                return Promise.reject(new Error("hata oluştu"));
            } else {
                return Promise.reject(new Error("bilinmeyen hata"));
            }
        })

    }


    return (
        <div className='create-assigment-container'>
            <div className="create-assignment">
                <div className="create-assignment-header">
                    <h3>Create New Assignment</h3>
                </div>
                <div className="create-assignment-body">
                    <form onSubmit={handleSubmit}
                        action="">
                        <div className="column col-md d-flex flex-column justify-content-center align-items-center ">
                            <h6>
                                Select Due Date
                            </h6>
                            <DatePicker dateFormat="dd/MM/yyyy" selected ={dueDate}
                                onChange={
                                    (date) => {
                                        setDueDate(date);
                                    }
                                }/>
                            <TimePicker className="mt-3"
                                onChange={setStartClock}
                                value={startClock}/>
                        </div>

                        <label>
                            <input type="mt-3 text" className="form-control" placeholder="Assignment Name"
                                onChange={
                                    e => setName(e.target.value)
                                }/>
                        </label>

                        <textarea rows="5" cols="60" type="text" className="mt-2 form-control" placeholder="Assignment Description"
                            onChange={
                                e => setDescription(e.target.value)
                        }></textarea>
                        <button className="create-button mt-3 btn btn-primary btn-block" type="submit">Create Assignment</button>
                    </form>
                </div>
            </div>

        </div>
    )
}

export default ClCreateAssignment
