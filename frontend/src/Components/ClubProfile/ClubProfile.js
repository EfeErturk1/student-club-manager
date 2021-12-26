import React, {useEffect, useState} from 'react'
import "./ClubProfile.css"
import {Link} from 'react-router-dom';

const ClubProfile = (props) => {
    const [imageData, setImageData] = React.useState({});
    const [imageUrl, setImageUrl] = useState(null);

    const photoLink = props.photo;

    useEffect(() => {

        fetch("http://localhost:8080/files/" + photoLink, {
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
            } else {
                return Promise.reject(new Error("Bilinmeyen bir hata oluştu."));
            }
        }).then(r => r.blob()).then((r) => {
            
            var binaryData = [];
            binaryData.push(r);
            setImageUrl(URL.createObjectURL(new Blob(binaryData, {type: "application/json"})))
            setImageData(r);

        }).catch((e) => {
            console.log(e.message);
        });

    }, [photoLink]);

    return (
        <div>
            <div className="container">
                <div className="row d-flex justify-content-center align-items-center mt-5">
                    <div className="column col-md d-flex flex-row justify-content-center align-items-center ">
                        <img className="club-pp d-flex r"
                            src={imageUrl}
                            alt="club profile picture"/>

                    </div>
                    <div className="column col-md d-flex flex-row justify-content-center align-items-center">
                        <div className="club-bio d-flex flex-column ">
                            <div className="club-bio-header d-flex flex-row justify-content-around">
                                <h3>{
                                    props.name
                                }</h3>
                                <p className='ms-3'>
                                    {
                                    props.numberOfMember
                                }
                                    members</p>
                                <p className='ms-3'>
                                    {
                                    props.numberOfEvents
                                }
                                    events</p>
                            </div>
                            <div className="club-bio-body d-flex flex-column ">
                                <p>socials</p>
                                <p>{
                                    props.description
                                }</p>
                                {
                                props.isStudent ? <></> : <div className="">
                                    <Link to={
                                        {
                                            pathname: '/editClubProfile',
                                            state: {
                                                description: props.description,
                                                name: props.name,
                                                id: props.clubId
                                            }
                                        }
                                    }>
                                        <button className="my-3  mx-1 btn btn-primary btn-block">Edit Profile</button>
                                    </Link>
                                    <Link>
                                        <Link to={
                                            {
                                                pathname: '/club/assignments'
                                            }
                                        }>
                                        <button className="btn btn-primary btn-block">See Assignments</button>

                                    </Link>
                                  </Link>
                                </div>
                            } </div>
                        </div>
                    </div>
                </div>
                <div className="row mt-5"></div>

            </div>
        </div>
    )
}

export default ClubProfile
