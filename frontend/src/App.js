import React from 'react';
import UserComponent from './components/UserComponent';
import UpdateUser from'./components/UpdateUser';
import {
    BrowserRouter as Router,
    Route,
    Switch,

} from "react-router-dom";

function App(){
    return(
        <Router>
            <div className="App">


                <Switch>
                    <Route exact path="/" component={UserComponent} />
                    {/*<Route exact path="/about" component={About} />*/}
                    {/*<Route exact path="/contact" component={Contact} />*/}
                    <Route exact path="/users/edit/:id" component={UpdateUser} />
                    {/*<Route exact path="/users/:id" component={User} />*/}
                    {/*<Route component={NotFound} />*/}
                </Switch>
            </div>
        </Router>
    )
}

export default App;


