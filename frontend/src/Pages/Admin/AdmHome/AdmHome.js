import React, {useState, useEffect} from 'react'
import "./AdmHome.css"
import {Club} from "../../../Components"

const AdmHome = () => {
const [clubs, setClubs] = useState(null);
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
    }).then((r) => {
        if (r.ok) {
          
            return r;
        }
        if (r.status === 401 || r.status === 403 || r.status === 500) {
            return Promise.reject(new Error("Bir hata oluştu " + r.status));
        }
        return Promise.reject(new Error("Bilinmeyen bir hata oluştu."));
    }).then((r) => r.json()).then((r) => {
    
        setClubs(r)

    }).catch((e) => {
        console.log(e.message);
    });


}, []);


return (
    <div>
        <div>
            <div className="adm-home">
                {
                clubs && clubs.map((club) => (
                    <Club isAdmin={true}
                        name={
                            club.name
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
                        }/>
                ))
            } </div>
        </div>
    </div>
)}
export default AdmHome
