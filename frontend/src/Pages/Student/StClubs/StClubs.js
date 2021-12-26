import React, {useState, useEffect} from 'react'
import "./StClubs.css"
import {Club} from "../../../Components"
import {useHistory} from 'react-router-dom';


const StClubs = (props) => {
    const [allClubs, setAllClubs] = useState([]);
    const [myClubs, setMyClubs] = useState([]);
    const stId = localStorage.getItem("id");
    const [viewMyClubs, setViewMyClubs] = useState(true);
    const [onPage, setOnPage] = useState("My Clubs");
    let history = useHistory();
    var mapped = viewMyClubs ? myClubs : allClubs;

    useEffect(() => {

        fetch("http://localhost:8080/club/allClubs", {
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
         
            setAllClubs(r)

        }).catch((e) => {
            console.log(e.message);
        });


        fetch("http://localhost:8080/club/getStudentClub?id=" + stId, {
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
            }
            if (r.status === 401 || r.status === 403 || r.status === 500) {
                return Promise.reject(new Error("Bir hata oluştu " + r.status));
            } else 
                return Promise.reject(new Error("Bilinmeyen bir hata oluştu."));

        }).then((r) => r.json()).then((r) => {
            setMyClubs(r)
        }).catch((e) => {
            console.log(e.message);
        });

    }, []);

    return (
        <div>
            <div>
                <h4 className="text-center m-2">
                    {onPage} </h4>
                <div className="dflex m-3 d-flex justify-content-center">
                    <button className="btn btn-primary btn-block mx-3"
                        onClick={
                            () => {
                                setOnPage("My Clubs")
                                setViewMyClubs(true)
                            }
                    }>My Clubs</button>

                    <button className="btn btn-primary btn-block"
                        onClick={
                            () => {
                                setOnPage("All Clubs")
                                setViewMyClubs(false)
                            }
                    }>All Clubs</button>
                </div>

                <div className="st-clubs">
                    {
                    allClubs && mapped.map((club) => (
                        <Club setNav2={
                                props.setNav2
                            }
                            name={
                                club.name
                            }
                            roles={
                                JSON.stringify(club.roles)
                            }
                            total_events={
                                club.numberOfEvents
                            }
                            description={
                                club.description
                            }
                            pp={
                                club.pp
                            }
                            id={
                                club.id
                            }
                            isMember={
                                myClubs.filter(a => a.id == club.id)
                            }/>
                    ))
                } </div>

            </div>
        </div>
    )
}

export default StClubs
