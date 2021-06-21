
import React, { useState, useEffect } from "react";
import axios from "axios";
import { useHistory, useParams } from "react-router-dom";



const baseURL ='http://localhost:8080/clapself/api/users';

const UpdateUser = () =>{



    let history = useHistory();
    const { id } = useParams();
    const [user, setUser] = useState({
        id:"",
        name: "",
        email: ""
    });


    const onInputChange = e => {
        setUser({ ...user, [e.target.name]: e.target.value });
    };
    const loadUser = async () => {
        const result = await axios.get(`${baseURL}/${id}`);
        setUser(result.data);
    };

    useEffect(() => {
        loadUser();
    }, []);

    const onSubmit = async e => {
        e.preventDefault();
        await axios.put(`${baseURL}`, user);
        history.push("/");
    };


    return (
        <div className="container">
            <div className="w-75 mx-auto shadow p-5">
                <h2 className="text-center mb-4">Edit A User</h2>
                <form onSubmit={e => onSubmit(e)}>
                    <div className="form-group">
                        <input
                            type="text"
                            className="form-control form-control-lg"
                            placeholder="Enter user ID"
                            name="name"
                            value={id}
                            onChange={e => onInputChange(e)}
                        />
                    </div>
                    <div className="form-group">
                        <input
                            type="text"
                            className="form-control form-control-lg"
                            placeholder="Enter Your Username"
                            name="name"
                            value={user.name}
                            onChange={e => onInputChange(e)}
                        />
                    </div>
                    <div className="form-group">
                        <input
                            type="email"
                            className="form-control form-control-lg"
                            placeholder="Enter Your E-mail Address"
                            name="email"
                            value={user.email}
                            onChange={e => onInputChange(e)}
                        />
                    </div>

                    <button className="btn btn-warning btn-block">Update User</button>
                </form>
            </div>
        </div>
    );
};


export default UpdateUser;
