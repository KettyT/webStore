import React from "react";
import {getHistory} from "../../../component/history";

class OrdersPage extends React.Component {

    constructor () {
        super();

        this.state = {
            data: {}
        };
    }

    getOrders () {
        let self = this;

        window.utils.getHttpPromise({
            method: "GET",
            url: "/api/order/getAllOrder",
            contentType: "application/json"
        }).then(function (response) {
            let data = JSON.parse(response);
            console.log(data);

            self.setState(Object.assign(self.state, {
                data: {
                    orders: data
                }
            }));
        });
    }

    toOrder (evt) {
        let target = evt.target;

        target = target.closest("tr");

        let orderId = target.getAttribute("data-order-id");

        getHistory().push("/cabinet/orders/" + orderId);
    }

    componentDidMount () {
        this.getOrders();
    }


    render() {
        let self = this;
        let orderArr = [];

        for (let i = 0; i < orderArr.length; i++) {
            orderArr.push();
        }

        return (<div className="">
                <table className="order_table">
                    <tr>
                        <th>№</th>
                        <th>Дата заказа</th>
                        <th>Дата доставки</th>
                        <th>Статус</th>
                    </tr>
            {this.state.data.orders && this.state.data.orders.map(function (elm, i) {
                return (<tr onClick={self.toOrder.bind(this)} data-order-id={elm.id}>
                    <td>{i + 1}</td>
                    <td>{elm.dateCreate}</td>
                    <td>{elm.dateDelivery}</td>
                    <td>{elm.status}</td>
                </tr>);
            })}
                </table>
        </div>);
    }
}

export default OrdersPage;