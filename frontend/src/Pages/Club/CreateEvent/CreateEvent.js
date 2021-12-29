import React, {useState, useEffect} from 'react'
import DatePicker from "react-datepicker"
import TimePicker from 'react-time-picker';
import InputSpinner from 'react-bootstrap-input-spinner'
import "react-datepicker/dist/react-datepicker.css"
import "./CreateEvent.css"
import {useHistory} from "react-router-dom";


const CreateEvent = () => {
    const [startDate, setStartDate] = useState(new Date())
    const [startClock, setStartClock] = useState('10:30');
    const [endClock, setEndClock] = useState('11:30');
    const [name, setName] = useState("")
    const [description, setDescription] = useState("")
    const [quota, setQuota] = useState(0)
    const [ge250, setGE250] = useState(0)

    const [picLink, setPicLink] = useState(null);
    const [picture, setPicture] = useState(null);

    let history = useHistory();

    const handlePicture = (event) => {
        setPicture(event.target.files[0]);
    };


    const handleSubmit = (event) => {
        event.preventDefault()

        let formData = new FormData();

        const clubId = localStorage.clubId;
        var m1 = (startDate.getMonth() + 1)
        if (startDate.getMonth() < 9) 
            m1 = "0" + (
                startDate.getMonth() + 1
            )
        
        var d1 = startDate.getDate()
        if (startDate.getDate() < 10) 
            d1 = "0" + startDate.getDate()
        
        const startDateFormatted = startDate.getFullYear() + "-" + m1 + "-" + d1 + "T" + startClock + ":00"

        var m2 = (startDate.getMonth() + 1)
        if (startDate.getMonth() < 9) 
            m2 = "0" + (
                startDate.getMonth() + 1
            )
        
        var d2 = startDate.getDate()
        if (startDate.getDate() < 10) 
            d2 = "0" + startDate.getDate()
        
        const endDateFormatted = startDate.getFullYear() + "-" + m2 + "-" + d2 + "T" + endClock + ":00"

        if (name == "" || description == "") {
            window.alert("None of the fields can be left empty!")

        }

        if (startClock > endClock) {
            window.alert("End clock cannot be before start clock ")
        }

        else {
            console.log("No bad credentials");
            formData.append('file', picture);

            fetch("http://localhost:8080/event/addEvent", {
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
                        name,
                        description,
                        clubId,
                        quota,
                        eventDate: startDateFormatted,
                        finishDate: endDateFormatted,
                        photos: "",
                        ge250
                    }
                )
            }).then((r) => {
                console.log(r);
                if (r.ok) {
                    return r
                } else if (r.status === 401 || r.status === 403 || r.status === 500) {
                    return Promise.reject(new Error("hata oluştu"));
                } else {
                    return Promise.reject(new Error("bilinmeyen hata"));
                }
            }).then((r) => r.json()).then((r) => {
                const eventId = r.eventId;
      
                fetch("http://localhost:8080/uploadEventPic?id=" + eventId, {
                    method: "POST",
                    headers: {
                        "Authorization": `Bearer ${
                            localStorage.token
                        }`
                    },
                    body: formData,
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

                    history.push("/club/home")


                }).catch((e) => {
                    console.log(e.message);
                });
            })
        }
    }
    return (
        <div className='create-club-container'>
            <div className="crate-event">
                <div className="create-event-header">
                    <h3>Create Event</h3>
                </div>
                <div className="create-event-body ">
                    <form className="d-flex flex-column"
                        onSubmit={handleSubmit}
                        enctype="multipart/form-data">
                        <label>
                            <input type="mt-3 text" className="form-control" placeholder="Event Name"
                                onChange={
                                    e => setName(e.target.value)
                                }/>
                        </label>
                        <label>
                            <textarea rows="5" cols="60" type="text" className="mt-2 form-control" placeholder="Event Description"
                                onChange={
                                    e => setDescription(e.target.value)
                            }></textarea>
                        </label>
                        <div className="d-flex flex-row mt-3">
                            <p className='mr-5'>Select the quota:
                            </p>
                            <InputSpinner type={'int'}
                                min={1}
                                step={1}
                                value={quota}
                                onChange={
                                    num => setQuota(num)
                                }
                                variant={'dark'}
                                size="sm"/>
                        </div>
                        <div className="row mt-3">
                            <div className="column col-md d-flex flex-column justify-content-center align-items-center ">
                                <h6>
                                    Select Date
                                </h6>
                                <DatePicker dateFormat="MM/dd/yyyy" selected ={startDate} minDate={new Date()}

                                    onChange={
                                        (date) => {
                                            setStartDate(date);
                                        }
                                    }/>
                                <div className='d-flex flex-row justify-content-center align-items-center'>
                                    <p className="mt-2 mx-2">Starts at:
                                    </p>
                                    <TimePicker className="mt-3"
                                        onChange={setStartClock}
                                        value={startClock}/>
                                </div>
                                <div className="d-flex flex-row">
                                    <p className="mt-2 mx-2">End at:
                                    </p>
                                    <TimePicker className="mt-3"
                                        onChange={setEndClock}
                                        value={endClock}/>
                                </div>

                            </div>
                            <div className="column col-md d-flex flex-column justify-content-center align-items-center">
                                <h6>
                                    Set GE 250/251 Points
                                </h6>

                                <InputSpinner type={'int'}
                                    min={0}
                                    step={5}
                                    value={ge250}
                                    onChange={
                                        num => setGE250(num)
                                    }
                                    variant={'dark'}
                                    size="sm"/>


                                <div className='my-3 text-center'>
                                    <h4>Upload Event Photo</h4>
                                    <input type='file' name='file_area'
                                        onChange={handlePicture}
                                        multiple/>
                                </div>
                            </div>
                        </div>
                        <button className="mt-3 btn btn-primary btn-block" type='submit'>Create Event</button>
                    </form>
                </div>
            </div>
        </div>

    )
}

export default CreateEvent
