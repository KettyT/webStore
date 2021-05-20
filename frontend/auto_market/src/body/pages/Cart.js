import React from "react";

// export default Header;

class Cart extends React.Component {

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
        if (this.state.data.successMessage) {
            return (<div>{this.state.data.successMessage}</div>);
        }

        if (!this.props.cartInfo.cartDetailsDtooList) {
            return (<div></div>);
        }

        return (<div>
            <table>
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
                            <td>{elm.quantity}</td>
                            <td>{elm.price}</td>

                            <td>{elm.amountInPackage}</td>
                            <td>{elm.quantity * elm.price}</td>
                        </tr>);
                })}

            </table>

            <div>
                Всего товаров в корзине: {this.props.cartInfo.count}
            </div>
            <div>
                На сумму: {this.props.cartInfo.totalSumm}
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