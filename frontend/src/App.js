import "bootstrap/dist/css/bootstrap.min.css";
import React, {useState} from "react";
import {BrowserRouter, Route} from "react-router-dom";
import {Login, SignUp, Buffer} from "./Auth"
import {Profile} from "./Pages/Student"
import {
    StHome,
    StNotifications,
    StClubs,
    StAssignments,
    StClubView
} from "./Pages/Student"
import {
    ClHome,
    CreateEvent,
    ClNotifications,
    ClAssignments,
    ClCreateAssignment,
    ClProfilePage
} from "./Pages/Club"
import {CreateClub, AdmHome} from "./Pages/Admin"
import {AdvHome, AdvNotifications, AdvClubMembers, AdvClubProfile} from "./Pages/Advisor"
import {StNav, AdvNav, ClNav, AdmNav} from "./Components"
import 'bootstrap/dist/css/bootstrap.min.css';
import "./App.css"
import EditProfile from './Pages/Student/Profile/EditProfile';
import EditEvent from "./Components/Event/EditEvent";
import UploadAssignment from './Pages/Student/StAssignments/UploadAssignment';
import EditClubProfile from "./Pages/Club/ClProfilePage/EditClubProfile";

function App() { // If there are no logged in users, render this part

    const [nav, setNav] = useState(1);
    const [nav2, setNav2] = useState(1);

    if (!localStorage.getItem('token')) {
        return (
            <div>
                <BrowserRouter>
                    <Route path="/signup"
                        component={SignUp}/>
                    <Route exact path="/"
                        component={Login}/>
                    <Route exact path="/redirecting"
                        component={Buffer}/>
                </BrowserRouter>
            </div>
        )
    }

    // If the logged in user is an admin, render this part else 
    else if (localStorage.role == "ROLE_ADMIN") {
        return (
            <div className="">
                <BrowserRouter>
                    <AdmNav/>
                    <Route path="/home"
                        component={AdmHome}/>
                    <Route path="/create-club"
                        component={CreateClub}/>
                </BrowserRouter>
            </div>
        )
    }

    // If the logged in user is a student, render this part 
    else if (localStorage.role == "ROLE_STUDENT") { // If the student is not visiting the clubs system, render this part

        if (localStorage.onclub == "false") {
            return (
                <div>
                    <BrowserRouter>
                        <StNav nav2={nav2}
                            setNav2={setNav2}/>
                        <Route path="/home"
                            component={StHome}/>
                        <Route path="/myProfile">
                            <Profile pp={"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSgKLehlQVLtvwtdN6ml4nyhaoZ5PrkdI1fBQ&usqp=CAU"}
                                name={"can"}
                                ge250_251={"taken"}
                                bio={"Nisi excepteur do cupidatat duis qui sunt."}/>
                        </Route>
                        <Route path='/editStudentProfile'>
                            <EditProfile/>
                        </Route>
                        <Route path="/notifications"
                            component={StNotifications}/>
                        <Route path="/clubs">
                            <StClubs setNav2={setNav2}/>
                        </Route>
                        <Route path="/assignments"
                            component={StAssignments}/>
                        <Route path="/uploadAssignment">
                            <UploadAssignment/>
                        </Route>
                        <Route path="/view-club">
                            <StClubView nav2={nav2}
                                setNav2={setNav2}/>
                        </Route>
                    </BrowserRouter>
                </div>
            );
        }
        // If the student is  visiting the clubs system, render this part 
        else {
            return (
                <div>
                    <BrowserRouter>
                        <ClNav nav={nav}
                            setNav={setNav}/>
                        <Route path="/club/home"
                            component={ClHome}/>
                        <Route path="/club/create-event">
                            <CreateEvent/>
                        </Route>
                        <Route path='/editEvent'>
                            <EditEvent></EditEvent>
                        </Route>
                        <Route path="/club/notifications"
                            component={ClNotifications}/>
                        <Route path="/club/assignments">
                            <ClAssignments nav={nav}
                                setNav={setNav}/>
                        </Route>
                        <Route path="/club/create-assignment"
                            component={ClCreateAssignment}/>
                        <Route path="/club/profile"
                            component={ClProfilePage}/>
                        <Route path="/editClubProfile"
                            component={EditClubProfile}/>
                    </BrowserRouter>
                </div>
            );
        }

    }
    // If the logged in user is an admin, render this part 
    else if (localStorage.role == "ROLE_ADVISOR") {
        return (
            <div className="">
                <BrowserRouter>
                    <AdvNav/>
                    <Route path="/home"
                        component={AdvHome}/>
                    <Route path="/notifications"
                        component={AdvNotifications}/>
                    <Route path="/club-members"
                        component={AdvClubMembers}/>
                    <Route path="/view-club"
                        component={AdvClubProfile}/>

                </BrowserRouter>
            </div>
        )
    }
}

export default App;
