import React from "react";
import {Router, Route, Switch} from "react-router-dom";
import Catalog from "../body/Catalog";
import { createBrowserHistory } from 'history'
import {setHistory} from "./history";

class WebStoreRouter extends React.Component {

    /*constructor () {
        super();

        this.data = [];
    }*/


    render() {
        let history = createBrowserHistory();
        setHistory(history);

        /*setTimeout(function () {
            history.push("/alskdjflaksdjf");
        }, 3000);*/

        return <Router  history={history}>
            <Switch>
                <Route path="/*">
                    <Catalog/>
                </Route>
            </Switch>

        </Router>
    }
}

export default WebStoreRouter;