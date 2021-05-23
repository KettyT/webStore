import React from "react";

class OrderPage extends React.Component {

    constructor () {
        super();

        this.state = {
            data: {}
        };

        this.formatter = new Intl.NumberFormat('ru-RU', {
            style: 'currency',
            currency: 'RUB',
        });
    }

    getOrder () {
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
        });
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
        /*let orderArr = [];



        for (let i = 0; i < orderArr.length; i++) {
            orderArr.push();
        }*/

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
                    <th>№</th>
                    <th>Название</th>
                    <th>Количество</th>
                    <th>Возврат</th>
                    <th>Цена</th>
                    <th>Общая сумма</th>
                </tr>

                {self.state.data.order.orderDetailDtoList && self.state.data.order.orderDetailDtoList.map(function (elm, idx) {
                    return (
                        <tr>
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
        </div>);
    }
}

export default OrderPage;