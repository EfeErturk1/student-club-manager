import React from 'react'
import {Navbar, Nav, NavLink, Container} from 'react-bootstrap'
import {Link} from 'react-router-dom'
import {useHistory} from "react-router-dom";

const StNav = (props) => {
    let history = useHistory()

    if (props.nav2 == 1) {
        return (
            <div>

                <Navbar bg="dark" variant="dark" collapseOnSelect expand="lg">
                    <Container>
                        <Navbar.Brand>ohmygoat.com</Navbar.Brand>
                        <Navbar.Toggle aria-controls="responsive-navbar-nav"/>
                        <Navbar.Collapse id="responsive-navbar-nav">
                            <Nav className="me-auto ">
                                <NavLink as={Link}
                                    to="/home">Home</NavLink>
                                <NavLink as={Link}
                                    to="/assignments">Assignment</NavLink>
                                <NavLink as={Link}
                                    to="/clubs">Clubs</NavLink>
                                <NavLink as={Link}
                                    to="/notifications">Notification</NavLink>
                                <NavLink as={Link}
                                    to="/myprofile">My Profile</NavLink>
                                <NavLink as={Link}
                                    to="/"
                                    onClick={
                                        () => {
                                            localStorage.clear()
                                            history.push("/")
                                            window.location.reload();
                                        }
                                }>Logout</NavLink>
                            </Nav>
                        </Navbar.Collapse>
                    </Container>
                </Navbar>
            </div>
        )
    } else {
        return (
            <div>

                <Navbar bg="dark" variant="dark" collapseOnSelect expand="lg">
                    <Container>
                        <Navbar.Brand>ohmygoat.com</Navbar.Brand>
                        <Navbar.Toggle aria-controls="responsive-navbar-nav"/>
                        <Navbar.Collapse id="responsive-navbar-nav">
                            <Nav className="me-auto ">
                                <NavLink as={Link}
                                    to="/"
                                    onClick={
                                        () => {
                                            props.setNav2(1)
                                            history.push("/home")
                                            localStorage.removeItem("clubId")
                                            window.location.reload();
                                        }
                                }>Back to Main Menu</NavLink>
                            </Nav>
                        </Navbar.Collapse>
                    </Container>
                </Navbar>
            </div>
        )
    }

}

export default StNav
