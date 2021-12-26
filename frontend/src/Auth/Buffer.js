import React, { useEffect } from 'react';
import {useHistory} from "react-router-dom";

/**
 * This page redirects the user from login page to the home page
 * This is necessary because of the structure of App.js
 * The App.js file will return a route logic for unauthorised pages (login, signup)
 * but after the login, the autheticated routers (Student system, Admin System and Advisor system)
 * will be returned. But the page will not initially render them since it requires one reload of this page.
 * This page is reponsible for redirecting the user from /login to /home and refresh the page.
 */ 
const Buffer = () => {
    let history = useHistory();

    useEffect(() => {
        history.push("/home")
        window.location.reload();
      });

    return (
        <div>
            <p className="m-3">
                Redirecting to the home page...
            </p>
        </div>
    )
}

export default Buffer
