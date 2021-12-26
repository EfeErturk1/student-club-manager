import React from 'react'
import {Navbar, Nav, NavLink, Container} from 'react-bootstrap'
import {Link, useHistory} from 'react-router-dom'

const ClNav = (props) => {
    let history = useHistory()

    if (props.nav == 1) {
        return (
            <div>
                <Navbar bg="dark" variant="dark" collapseOnSelect expand="lg">
                    <Container>
                        <Navbar.Brand>ohmygoat.com</Navbar.Brand>
                        <Navbar.Toggle aria-controls="responsive-navbar-nav"/>
                        <Navbar.Collapse id="responsive-navbar-nav">
                            <Nav className="me-auto ">
                                <NavLink as={Link}
                                    to="/club/home">Home</NavLink>
                                <NavLink as={Link}
                                    to="/club/assignments">Assignment</NavLink>
                                <NavLink as={Link}
                                    to="/club/create-event">Create Events</NavLink>
                                <NavLink as={Link}
                                    to="/club/documents">Documents</NavLink>
                                <NavLink as={Link}
                                    to="/club/notifications">Notification</NavLink>
                                <NavLink as={Link}
                                    to="/club/profile">Club Profile</NavLink>
                                <NavLink as={Link}
                                    to="/"
                                    onClick={
                                        () => {
                                            localStorage.removeItem("clubId")
                                            localStorage.removeItem("roleOfStudent")
                                            localStorage.onclub = "false";
                                            history.push("/home")
                                            window.location.reload();
                                        }
                                }>Exit from Club System</NavLink>
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
                                            props.setNav(1)
                                            history.push("/club/home")
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

export default ClNav
