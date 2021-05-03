import React from "react";
import {controlFunctions, getGlobalController} from "../component/GlobalController";
import {getHistory} from "../component/history";

// export default Header;

class HeaderCmp extends React.Component {

    constructor () {
        super();

        this.state = {
            cartCount: 0
        };

        getGlobalController.updateHeaderCartInfo = this.updateCartHeader.bind(this);
    }

    updateCartHeader(count, cartStatistics) {

        if (cartStatistics) {
            this.setState(Object.assign(this.state, {
                cartCount: count,
                name: cartStatistics.name,
                surname: cartStatistics.surname
            }));
            return;
        }

        this.setState(Object.assign(this.state, {
            cartCount: count
        }));

    }

    toCart () {

        /*window.utils.getHttpPromise({
            method: "GET",
            url: "/api/cart/getFullCartInfo/",
            contentType: "application/json"
        }).then(function (response) {
            let data = JSON.parse(response);
            console.log(data);

            getGlobalController.catalogStateFunction("cartInfo", data);
        });*/
        controlFunctions.updateCartPage();

        getHistory().push("/cart");
    }

    render() {
        return (
            <div className = "wrapper">
                <div className = "flex_header">
                    <h1>Auto Market</h1>
                    <div className="user_statistics">
                        <div className="user_statistics__person_info">{this.state.name} {this.state.surname}</div>
                        <div className="user_statistics__cart_info" onClick = {this.toCart.bind(this)}>
                            <div className="user_statistics__cart_items">
                                {this.state.cartCount}
                            </div>
                            {/*Товаров в корзине: {this.state.cartCount}*/}
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default HeaderCmp;