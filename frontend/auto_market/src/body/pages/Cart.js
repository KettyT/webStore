import React from "react";
import CartCountPicker from "../cart/CartCountPicker";
import {controlFunctions} from "../../component/GlobalController";
import {getHistory} from "../../component/history";

// export default Header;

class Cart extends React.Component {

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

    onChangeQuantityInCart (detailId, quantity) {
        controlFunctions.updateCartStatistics();
        controlFunctions.setItemToCartAndUpdate(detailId, quantity);
    }

    doOrder () {
        let self = this;

        let deliveryStyle = document.querySelector(".delivery_style");

        let radioButtonList = deliveryStyle.querySelectorAll("input");

        let deliveryChecked = false;

        for (let i = 0; i < radioButtonList.length; i++) {
            if (radioButtonList[i].checked) {
                deliveryChecked = true;
            }
        }

        if (!deliveryChecked) {
            controlFunctions.setUserMessage("Ошибка", "Нужно выбрать пункт выдачи");
            return;
        }

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

            getHistory().push("/cabinet/orders")

        }, function (response, statusCode) {
            let data = JSON.parse(response);

            if (data.status === 404) {
                window.location.href = "/login";
                return;
            }

            console.log(data);

            controlFunctions.setUserMessage("Произошла ошибка", data.message);
        });
    }

    clearCart () {
        controlFunctions.clearCart(function () {
            controlFunctions.updateCartStatistics();
        });
    }

    render() {
        let self = this;

        if (this.state.data.successMessage) {
            return (<div>{this.state.data.successMessage}</div>);
        }

        if (!this.props.cartInfo.cartDetailsDtooList) {
            return (<div></div>);
        }

        return (<div>
            <table className="classic_table_style">
                <tr>
                    <th>№</th>
                    <th>Название</th>
                    <th>Количество</th>
                    <th>Цена</th>
                    <th>Кол. в упаковке</th>
                    <th>Общая сумма</th>
                </tr>

                {this.props.cartInfo.cartDetailsDtooList.map(function (elm, idx) {
                    return (
                        <tr>
                            <td>{idx + 1}</td>
                            <td>{elm.itemName}</td>
                            <td className="flex_center">
                                <CartCountPicker onItemClick={self.onChangeQuantityInCart} keyItem={elm.id} detailId={elm.detailId} value={elm.quantity}/>
                            </td>
                            <td>{self.formatter.format(elm.price)}</td>

                            <td>{elm.amountInPackage}</td>
                            <td>{self.formatter.format(elm.quantity * elm.price)}</td>
                        </tr>);
                })}

            </table>

            <div className="gray_background">
                Всего товаров в корзине: {this.props.cartInfo.count}
            </div>
            <div className="gray_background">
                На сумму: {self.formatter.format(this.props.cartInfo.totalSumm)}
            </div>
            <div className="align_right_row">
                <button onClick = {this.clearCart.bind(this)}>Очистить корзину</button>
            </div>
            <br/>
            <br/>
            <br/>

            <div class="">
                <h2>Пункты выдачи в Перми:</h2>

                <div className="delivery_style">
                    <ul>
                        <li><input type="radio" name="delivery_addr"/> ул. Плеханова 23</li>
                        <li><input type="radio" name="delivery_addr"/> ул. Ленина 45</li>
                        <li><input type="radio" name="delivery_addr"/> ул. Комсомольский проспект 72</li>
                    </ul>
                </div>

                <div className="content_block">
                    <button onClick = {this.doOrder.bind(this)}>Заказать</button>
                </div>
            </div>

        </div>);
    }
}

export default Cart;