import React from "react";
import CartCountPicker from "../../cart/CartCountPicker";
import {controlFunctions, getGlobalController} from "../../../component/GlobalController";

class Refund extends React.Component {

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

    componentDidMount () {
        // this.getOrder();
    }

    findInOrderList (orderDetailDtoList, orderId) {
        for (let i = 0; i < orderDetailDtoList.length; i++) {
            if (orderDetailDtoList[i].id === orderId) {
                return orderDetailDtoList[i];
            }
        }

        return null;
    }

    doRefundAction (evt) {

        let quantitySelectorList = document.querySelectorAll(".quantity_selection");
        let rejectReasonElm = document.querySelector(".reject_reason");
        let rejectReason = rejectReasonElm.value;

        let refundedOrderList = [];

        for (let i = 0; i < quantitySelectorList.length; i++) {
            if (quantitySelectorList[i].value >  0) {
                let orderDetailId = quantitySelectorList[i].getAttribute("data-item-id");
                let refundQuantity = quantitySelectorList[i].value;

                let orderDetail = this.findInOrderList(this.props.order.orderDetailDtoList, +orderDetailId);
                orderDetail.refundedQuantity = refundQuantity;

                refundedOrderList.push(orderDetail);
            }
        }

        window.utils.getHttpPromise({
            method: "POST",
            url: "/api/order/doRefund",
            contentType: "application/json",
            jsonData: {
                refundReason: rejectReason,
                refundedOrderList: refundedOrderList
            }
        }).then(function (response) {
            let data = JSON.parse(response);

            if (data.success) {
                getGlobalController.backToOrder(function () {
                    controlFunctions.setUserMessage("", "Заявка успешно создана!");
                });

            }

        });


    }

    render() {
        let self = this;

        if (!self.props.order) {
            return (<div>

            </div>);
        }


        return (<div className="">

            <div>
                Выберите количество товаров:
            </div>
            <br/><br/>

            <table className="classic_table_style">
                <tr>
                    {/*<th></th>*/}
                    <th>№</th>
                    <th>Название</th>
                    <th>Количество</th>
                    <th>Возврат</th>
                    <th>Цена</th>
                    <th>Общая сумма</th>
                </tr>

                {self.props.order.orderDetailDtoList && self.props.order.orderDetailDtoList.map(function (elm, idx) {
                    if (self.props.pickedOrderDetailIds.indexOf(elm.id) !== -1 ) {
                        return (
                            <tr>
                                {/*<td><input type = "checkbox" className="selected_order_checkbox" data-order-id={elm.id}/></td>*/}
                                <td>{idx + 1}</td>
                                <td>{elm.name}</td>
                                <td className="column_center">
                                    {/*{elm.quantity}*/}
                                    <CartCountPicker onItemClick={self.onChangeQuantityInCart}
                                                     detailId={elm.id}
                                                     value={elm.quantity}
                                                     maxQuantity={elm.quantity}
                                    />
                                </td>
                                <td className="column_center">
                                    {elm.refundedQuantity}
                                </td>
                                <td>{self.formatter.format(elm.price)}</td>
                                <td>{self.formatter.format(elm.quantity * elm.price)}</td>
                            </tr>);
                    }
                })}

            </table>

            <br/><br/>

            <div>
                <div className="classic_row">
                    <div className="classic_row__title">
                        Причина возврата
                    </div>
                    <div className="classic_row__value">
                        <textarea className="textarea_style reject_reason" placeholder="Причина возврата"></textarea>
                    </div>
                </div>
            </div>

            <br/><br/>

            <div>
                <button onClick={this.doRefundAction.bind(this)}>Оставить завку на возврат</button>
            </div>
        </div>);
    }
}

export default Refund;