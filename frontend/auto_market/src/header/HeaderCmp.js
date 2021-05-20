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
        controlFunctions.updateCartPage();

        getHistory().push("/cart");
    }

    toCabinet () {
        getHistory().push("/cabinet");
    }

    render() {
        let self = this;

        let userInfo = null;

        if (self.state.name && self.state.surname) {
            userInfo = (<div className="user_statistics__person_info">
                {self.state.name} {self.state.surname}
                <span className="lk span_anchor" onClick={this.toCabinet.bind(this)}>Личный кабинет</span>
            </div>);
        } else {
            userInfo = (<div className="user_statistics__person_info"></div>);
        }

        return (
            <div className = "wrapper">
                <div className = "flex_header">
                    <h1>Auto Market</h1>
                    <div className="user_statistics">
                        {userInfo}
                        <div className="user_statistics__cart_info span_anchor" onClick = {this.toCart.bind(this)}>
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