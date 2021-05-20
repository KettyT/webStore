import React from "react";
import {Link, Route, Switch} from "react-router-dom";
import ProfilePage from "./ProfilePage";
import OrdersPage from "./OrdersPage";
import OrderPage from "./OrderPage";

class CabinetMain extends React.Component {

    constructor () {
        super();

        this.state = {
            data: {}
        };
    }

    doOrder () {
        let self = this;

        window.utils.getHttpPromise({
            method: "GET",
            url: "/api/order/doOrder",
            contentType: "application/json",
            jsonData: {
            }
        }).then(function (response) {
            let data = JSON.parse(response);
            console.log(data);

            self.setState({
                data: {
                    successMessage: "Ваш заказ успешно оформлен. Обращайтесь ещё."
                }
            });
        }, function (response) {
            window.location.href = "/login";
        });
    }

    render() {
        return (<div className="wrapper">
            <div className="column_wrapper">
                <div className="cabinet__left_column">
                    <Link className="cabinet__item_link" to="/cabinet">Профиль</Link>
                    <Link className="cabinet__item_link" to="/cabinet/orders">Заказы</Link>
                </div>
                <div className="cabinet__right_column">
                    <Switch>
                        <Route path = "/cabinet/orders/:orderId">
                            <OrderPage />
                        </Route>
                        <Route exact path="/cabinet/orders">
                            <OrdersPage/>
                        </Route>
                        <Route path="/*">
                            <ProfilePage/>
                        </Route>
                    </Switch>
                </div>
            </div>
        </div>);
    }
}

export default CabinetMain;