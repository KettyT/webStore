import React from "react";
import {Route, Router, Switch} from "react-router-dom";
import Catalog from "../body/Catalog";
import {createBrowserHistory} from 'history'
import {setHistory} from "./history";
import CabinetMain from "../body/pages/cabinet/CabinetMain";
import {getGlobalController} from "./GlobalController";
import PictureDetail from "../body/pages/PictureDetail";

// import {controlFunctions, getGlobalController} from "../component/GlobalController";

class WebStoreRouter extends React.Component {

    /*constructor () {
        super();

        this.data = [];
    }*/

    updateCartStatistics() {

        window.utils.getHttpPromise({
            method: "GET",
            url: "/api/cart/getCartStatistics",
            contentType: "application/json",
            jsonData: {
            }
        }).then(function (response) {
            let data = JSON.parse(response);
            console.log(data);

            getGlobalController.updateHeaderCartInfo(data.count, data);
        });


    }

    componentDidMount() {
        let self = this;

        this.updateCartStatistics();

    }

    render() {
        let history = createBrowserHistory();
        setHistory(history);

        /*setTimeout(function () {
            history.push("/alskdjflaksdjf");
        }, 3000);*/

        return <Router  history={history}>
            <Switch>
                <Route path="/cabinet">
                    <CabinetMain/>
                </Route>
                <Route path="/cabinet/**">
                    <CabinetMain/>
                </Route>
                <Route path="/pictureDetail/:detailGroupListId">
                    <PictureDetail/>
                </Route>
                <Route path="/*">
                    <Catalog/>
                </Route>
            </Switch>

        </Router>
    }
}

export default WebStoreRouter;