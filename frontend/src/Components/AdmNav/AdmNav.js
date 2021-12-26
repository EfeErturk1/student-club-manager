import React from 'react'
import {Navbar, Nav, NavLink, Container} from 'react-bootstrap'
import {Link} from 'react-router-dom'
import {useHistory} from "react-router-dom";

const AdmNav = () => {
    let history = useHistory()
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
                                to="/create-club">Create Club</NavLink>
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
}

export default AdmNav
