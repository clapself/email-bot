import React, {useEffect, useState} from 'react';
import axios from 'axios' ;
import {Link, useHistory} from "react-router-dom";
import ConfirmDialog from "./ConfirmDialog";
import Modal from "react-bootstrap/Modal";
import {Button, Form, FormGroup} from "react-bootstrap";
import {Label} from "reactstrap";

const baseURL='http://localhost:8080/clapself/api/users';

let isActive;

const UserComponent = () => {
    const [users, setUser] = useState([]);
    const [confirmDialog, setConfirmDialog] = useState({ isOpen: false, title: '', subTitle: '' })

    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const [addshow, setAddShow] = useState(false);
    const handleAddShow = () => setAddShow(true);
    const handleAddClose = () => setAddShow(false);


    let history = useHistory();

    useEffect(() => {
        loadUsers();
    }, []);

    const loadUsers = async () => {
        const result = await axios.get(`${baseURL}`);
        setUser(result.data);
    };
    const [editUser, setEditUser] = useState([]);
    const loadUser = async (id) => {
        const result = await axios.get(`${baseURL}/${id}`);
        setEditUser(result.data);
    };

    const onInputChange = e => {
        setEditUser({ ...editUser, [e.target.name]: e.target.value });
    };

    const deleteUser = async id => {
        setConfirmDialog({
            ...confirmDialog,
            isOpen: false
        })
        await axios.delete(`http://localhost:8080/clapself/api/user/${id}`);
        loadUsers();
    };

    const sendMail = async user => {
        await axios.post(`http://localhost:8080/clapself/api/sendnotif`,user).then(res =>{
            console.log(res.user)})

        loadUsers();

    };

    const deleteUsersByIds = () => {

        let arrayIds = [];
        users.forEach(d => {
            if (d.select) {
                arrayIds.push(d.id);
            }
        });
        setConfirmDialog({
            ...confirmDialog,
            isOpen: false
        })
        axios.delete(`${baseURL}/${arrayIds}`)
            .then(data => {
                console.log(data);
                 loadUsers();
            })
            .catch(err => alert(err));
    };

    const sendBulkMails=() =>{
        let arrayUsers = [];

        users.forEach(d => {
            if (d.select) {
                arrayUsers.push(d);
            }
        });
        setConfirmDialog({
            ...confirmDialog,
            isOpen: false
        })
        axios.post(`http://localhost:8080/clapself/api/sendnotifs/`,arrayUsers).then(res =>{
            console.log(res.user)})
        loadUsers();
    }
    const [addUser, setAddUser]= useState({});
    const AddUser =() => {
        postUser(addUser);
    };

    const postUser =async(user) => {
        await axios.post(`${baseURL}`, user).then(res =>{
            console.log(res.user)})

        history.push("/");

    };

    const updateUser = async() => {
        await axios.put(`${baseURL}`, editUser);
        history.push("/");
    };

    return (
        <div className="container">
            <button onClick={handleAddShow}> Add Users</button>

            <Modal show={addshow} onHide={handleAddClose}>
                <Modal.Header >
                    <Modal.Title>Add A User</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={AddUser}>

                        <FormGroup>
                            <Label> Username</Label>
                            <input  className="form-control form-control-lg" type ="text" placeholder="Enter Name of the user" id="Username" onChange={(e) => {
                                setAddUser({ ...addUser, name:e.target.value})
                            }}/>
                        </FormGroup>

                        <FormGroup>
                            <Label > Email</Label>
                            <input className="form-control form-control-lg" type ="text" placeholder="Enter email id of the user" id="email"   onChange={(e) => {
                                setAddUser({ ...addUser, email:e.target.value})
                            }}/>
                        </FormGroup>
                        <button className="btn btn-warning btn-block" type="submit">Add User</button>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleAddClose}>
                        Close
                    </Button>
                </Modal.Footer>
            </Modal>


            <div className="py-4">
                <h1>Home Page</h1>
                <table class="table border shadow">
                    <thead class="thead-dark">
                    <tr>
                        <th>
                            <input
                                type="checkbox"
                                onChange={e => {
                                    isActive = e.target.checked;
                                    let value = e.target.checked;
                                    setUser(
                                        users.map(d => {
                                            d.select = value;
                                            return d;
                                        })
                                    );
                                }}
                            /></th>

                        <td>ID</td>
                        <td>Username</td>
                        <td>Email</td>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {users.map((user, index) => (
                        <tr>
                            <td>
                                <input
                                    type="checkbox"
                                    checked={user.select}
                                    onChange={e => {
                                        isActive = e.target.checked;
                                        let value = e.target.checked;
                                       setUser(
                                            users.map(sd => {
                                                if (sd.id === user.id) {
                                                    sd.select = value;
                                                }
                                                return sd;
                                            })
                                        );
                                    }}
                                />
                            </td>

                            <td>{user.id}</td>
                            <td>{user.name}</td>
                            <td>{user.email}</td>
                            <td>
                                <button
                                    class="btn btn-outline-primary mr-2"
                                    onClick={() =>{
                                        loadUser(user.id);
                                        handleShow();

                                    }}
                                >
                                    Edit
                                </button>

                                <Modal show={show} onHide={handleClose}>
                                    <Modal.Header >
                                        <Modal.Title>Edit A User</Modal.Title>
                                    </Modal.Header>
                                    <Modal.Body>
                                        <form onSubmit={e => updateUser(e)}>
                                            <div className="form-group">
                                                <input
                                                    type="text"
                                                    className="form-control form-control-lg"
                                                    placeholder="Enter user ID"
                                                    name="name"
                                                    value={editUser.id}
                                                    onChange={e => onInputChange(e)}
                                                />
                                            </div>
                                            <div className="form-group">
                                                <input
                                                    type="text"
                                                    className="form-control form-control-lg"
                                                    placeholder="Enter Your Username"
                                                    name="name"
                                                    value={editUser.name}
                                                    onChange={e => onInputChange(e)}
                                                />
                                            </div>
                                            <div className="form-group">
                                                <input
                                                    type="email"
                                                    className="form-control form-control-lg"
                                                    placeholder="Enter Your E-mail Address"
                                                    name="email"
                                                    value={editUser.email}
                                                    onChange={e => onInputChange(e)}
                                                />
                                            </div>

                                            <button className="btn btn-warning btn-block">Update User</button>
                                        </form>
                                    </Modal.Body>
                                    <Modal.Footer>
                                        <Button variant="secondary" onClick={handleClose}>
                                            Close
                                        </Button>
                                    </Modal.Footer>
                                </Modal>


                                    &nbsp;&nbsp;&nbsp;
                                <Link
                                    class="btn btn-danger"
                                    onClick={() =>{
                                        setConfirmDialog({
                                            isOpen: true,
                                            title: 'Are you sure to delete data of '+(user.name)+'?',
                                            subTitle: "You can't undo this operation",
                                            onConfirm: () => { deleteUser(user.id) }
                                        })

                                    }}
                                >
                                    Delete
                                </Link>
                                &nbsp;&nbsp;&nbsp;
                                <Link
                                    class="btn btn-outline-primary mr-2"
                                    onClick={() =>{
                                         sendMail(user)

                                    }}
                                >
                                    Send Mail
                                </Link>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>

            </div>
            <div>
            { isActive ? <button
                className="btn btn-danger btn-sm m-2"
                onClick={() => {
                    let arrayName=[];
                    users.forEach(d => {
                        if (d.select) {
                            arrayName.push(d.name);
                        }
                    });
                    setConfirmDialog({
                        isOpen: true,
                        title: 'Are you sure to delete data of '+arrayName+'?',
                        subTitle: "You can't undo this operation",
                        onConfirm: () => {  deleteUsersByIds();
                            isActive = false;
                        }
                    })
                }}
            >
                Delete Users
            </button>: null }

                { isActive ?  <button className="btn btn-danger btn-sm m-2"
                                      onClick={() => {
                                          let arrayName=[];
                                          users.forEach(d => {
                                              if (d.select) {
                                                  arrayName.push(d.name);
                                              }
                                          });
                                          setConfirmDialog({
                                              isOpen: true,
                                              title: 'Are you sure to send emails to '+arrayName+'?',
                                              subTitle: "You can't undo this operation",
                                              onConfirm: () => {  sendBulkMails();
                                                  isActive = false;
                                              }
                                          })
                                      }}

                    >
                    Send Emails
                </button>: null }

            </div>
            <ConfirmDialog
                confirmDialog={confirmDialog}
                setConfirmDialog={setConfirmDialog}
            />

        </div>

    );
};


export default UserComponent
