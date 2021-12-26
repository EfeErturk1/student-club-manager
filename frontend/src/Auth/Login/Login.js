import React, {useState, useEffect} from 'react'
import "./Login.css"
import Logo from "../../Assets/bilkent_logo.png"
import {useHistory} from "react-router-dom";


const Login = () => {
    const [username, setUserName] = useState();
    const [password, setPassword] = useState();
    let history = useHistory();

    const handleSubmit = (event) => {
        event.preventDefault();

        fetch("http://localhost:8080/auth/login", {
            method: "POST",
            headers: {
                "Content-type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify(
                {username, password}
            )
        }).then((r) => {
            if (r.ok) {
                return r;
            }
            if (r.status === 401 || r.status === 403 || r.status === 500) {
                return Promise.reject(new Error("hata oluştu"));
            }
            return Promise.reject(new Error("bilinmeyen hata"));
        }).then((r) => r.json()).then((response) => {
            localStorage.setItem("token", response.token);
            localStorage.setItem('email', response.email);
            localStorage.setItem('id', response.id);
            localStorage.setItem("role", response.roles[0]);
            localStorage.setItem("onclub", "false");
            localStorage.setItem("clubId", response.clubId);
            history.push('/redirecting');
        }).catch((e) => {
            console.log("here");
        });

    };

    return (
        <div className="login-body">
            <div className="card">
                <div className="intro">
                    <img src={Logo}
                        width="260"/>
                </div>
                <h5 className="mt-2">Please Log In</h5>
                <form className='d-flex flex-column justify-content-center align-items-center'
                    onSubmit={handleSubmit}>
                    <label>
                        <input type="text" className="form-control" placeholder="Username"
                            onChange={
                                e => setUserName(e.target.value)
                            }/>
                    </label>
                    <label>
                        <input type="password" className="mt-3 form-control" placeholder="Password"
                            onChange={
                                e => setPassword(e.target.value)
                            }/>
                    </label>
                    <div>
                        <button className="mt-3 btn btn-primary btn-block" type="submit">Login</button>
                    </div>
                </form>
                <p className="text-center form-check"
                    onClick={
                        () => history.push("/signup")
                }>Already have an account? Click here to register</p>
            </div>
        </div>
    )
}

export default Login
