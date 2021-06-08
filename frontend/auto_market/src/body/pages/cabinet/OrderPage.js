import React from "react";
import Refund from "./Refund";
import {getGlobalController} from "../../../component/GlobalController";

class OrderPage extends React.Component {

    constructor () {
        super();

        this.state = {
            data: {},
            pickedOrderDetailIds: null,
            mode: null,
        };

        this.formatter = new Intl.NumberFormat('ru-RU', {
            style: 'currency',
            currency: 'RUB',
        });

        getGlobalController.backToOrder = this.backToOrder.bind(this);
    }

    backToOrder (callback) {
        let self = this;

        self.getOrder(function (){
            self.setState(Object.assign(self.state, {
                mode: ""
            }));

            callback();
        });
    }

    getOrder (callback) {
        let self = this;

        let href = window.location.href;
        let lastSlash = href.lastIndexOf("/");
        let orderId = href.substr(lastSlash + 1);

        window.utils.getHttpPromise({
            method: "POST",
            url: "/api/order/getOrderById",
            contentType: "application/json",
            jsonData: {
                id: +orderId
            }
        }).then(function (response) {
            let data = JSON.parse(response);
            console.log(data);

            self.setState(Object.assign(self.state, {
                data: {
                    order: data
                }
            }));

            if (callback) {
                callback();
            }
        });
    }

    doRefundAction (evt) {
        let self = this;

        let orderCheckboxList = document.querySelectorAll(".selected_order_checkbox");

        let pickedOrderDetailIds = [];

        for (let i = 0; i < orderCheckboxList.length; i++) {
            if (orderCheckboxList[i].checked) {
                pickedOrderDetailIds.push(+orderCheckboxList[i].getAttribute("data-order-id"));
            }
        }

        this.setState(Object.assign(self.state, {
            mode: "REFUND",
            pickedOrderDetailIds: pickedOrderDetailIds
        }));
    }

    componentDidMount () {
        this.getOrder();
    }


    render() {
        let self = this;

        if (!self.state.data.order) {
            return (<div>

            </div>);
        }

        if (self.state.mode === "REFUND") {
            return (<Refund order={self.state.data.order} pickedOrderDetailIds={this.state.pickedOrderDetailIds}/>);
        }

        return (<div className="">
            <div className="classic_row">
                <div className="classic_row__title">
                    Дата заказа
                </div>
                <div className="classic_row__value">
                    {self.state.data.order.dateCreate}
                </div>
            </div>

            <div className="classic_row">
                <div className="classic_row__title">
                    Дата получения
                </div>
                <div className="classic_row__value">
                    {self.state.data.order.dateDelivery}
                </div>
            </div>

            <div className="classic_row">
                <div className="classic_row__title">
                    Статус заказа
                </div>
                <div className="classic_row__value">
                    {self.state.data.order.status}
                </div>
            </div>
            <br/><br/>

            <table className="classic_table_style">
                <tr>
                    <th></th>
                    <th>№</th>
                    <th>Название</th>
                    <th>Количество</th>
                    <th>Возврат</th>
                    <th>Цена</th>
                    <th>Общая сумма</th>
                </tr>

                {self.state.data.order.orderDetailDtoList && self.state.data.order.orderDetailDtoList.map(function (elm, idx) {
                    return (
                        <tr key={idx}>
                            <td><input type = "checkbox" className="selected_order_checkbox" data-order-id={elm.id}/></td>
                            <td>{idx + 1}</td>
                            <td>{elm.name}</td>
                            <td className="column_center">
                                {elm.quantity}
                                {/*<CartCountPicker detailId={elm.detailId} value={elm.quantity}/>*/}
                            </td>
                            <td className="column_center">
                                {elm.refundedQuantity}
                            </td>
                            <td>{self.formatter.format(elm.price)}</td>
                            <td>{self.formatter.format(elm.quantity * elm.price)}</td>
                        </tr>);
                })}

            </table>

            <div>
                <button onClick={this.doRefundAction.bind(this)}>Оформить возврат</button>
            </div>

            {/*orderRefundRequestDtoList*/}

            <h3>Заявки на возврат</h3>
            <table className="classic_table_style">
                <tr>
                    <th>№</th>
                    <th>Название</th>
                    <th>Количество</th>
                    <th>Цена</th>
                    <th>Общая сумма</th>
                    <th>Статус</th>
                </tr>

                {self.state.data.order.orderDetailDtoList && self.state.data.order.orderRefundRequestDtoList.map(function (elm, idx) {
                    return (
                        <tr key={idx}>
                            <td>{idx + 1}</td>
                            <td>{elm.orderDetailName}</td>
                            <td className="column_center">
                                {elm.quantity}
                                {/*<CartCountPicker detailId={elm.detailId} value={elm.quantity}/>*/}
                            </td>
                            <td className="column_center">{self.formatter.format(elm.orderDetailPrice)}</td>
                            <td className="column_center">{self.formatter.format(elm.quantity * elm.orderDetailPrice)}</td>
                            <td className="column_center">{elm.status}</td>
                        </tr>);
                })}

            </table>

        </div>);
    }
}

export default OrderPage;