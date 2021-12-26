import React, {useEffect, useState} from 'react'
import {StudentPreview} from "../../../Components"
import "./AdvClubMembers.css"

const AdvClubMembers = () => {

    const [members, setMembers] = useState([]);

    useEffect(() => {

        fetch("http://localhost:8080/club/getMembersOfClub?id=" + localStorage.getItem("clubId"), {
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
            setMembers(r)
        }).catch((e) => {
            console.log(e.message);
        });

    }, []);

    const [check1, setCheck1] = useState(true);
    const [check2, setCheck2] = useState(true);
    const [check3, setCheck3] = useState(true);
    const [check4, setCheck4] = useState(true);
    return (
        <div className='adv-club-members'>
            <div className="container w-100">
                <div className="row w-100">
                    <div className="column col-sm-12 col-lg d-flex flex-column  align-items-center">
                        <div className="adv-st-filter">
                            <div className="adv-st-filter-head">
                                <h4>Filter Students</h4>
                            </div>
                            <div className="adv-st-filter-body">
                                <form>
                                    <input onChange={
                                            () => setCheck1(!check1)
                                        }
                                        type="checkbox"
                                        checked={check1}/>
                                    <label>Regular Members</label><br/><hr/>
                                    <input onChange={
                                            () => setCheck2(!check2)
                                        }
                                        type="checkbox"
                                        checked={check2}/>
                                    <label>Active Members</label><br/><hr/>
                                    <input onChange={
                                            () => setCheck3(!check3)
                                        }
                                        type="checkbox"
                                        checked={check3}/>
                                    <label>Director Board Members</label><br/><hr/>
                                    <input onChange={
                                            () => setCheck4(!check4)
                                        }
                                        type="checkbox"
                                        checked={check4}/>
                                    <label>The President</label><br/>

                                </form>
                            </div>
                        </div>
                    </div>

                    <div className="column col-sm col-lg d-flex flex-column justify-content-center align-items-center">
                        {
                        members.filter(student => (student.role == "MEMBER" && check1) || (student.role == "ACTIVE_MEMBER" && check2) || (student.role == "BOARD_MEMBER" && check3) || (student.role == "PRESIDENT" && check4)).map((student) => (
                            <StudentPreview name={
                                    student.name
                                }
                                id={
                                    student.id
                                }
                                department={
                                    student.ge250
                                }
                                position={
                                    student.role
                                }/>
                        ))
                    } </div>
                </div>
            </div>

        </div>
    )
}

export default AdvClubMembers// d-flex flex-column justify-content-center align-items-center
