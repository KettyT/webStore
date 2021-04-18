import React from "react";
import {getGlobalController} from "../component/GlobalController";

// export default Header;

class HeaderCmp extends React.Component {

    constructor () {
        super();

        this.state = {
            cartCount: 0
        };

        getGlobalController.updateHeaderCartInfo = this.updateCartHeader.bind(this);
    }

    updateCartHeader(count) {
        this.setState(Object.assign(this.state, {
            cartCount: count
        }));
    }

    render() {
        return (
            <div>
                <h1>This is Header</h1>
                <div>
                    Товаров в корзине: {this.state.cartCount}
                </div>
            </div>
        );
    }
}

export default HeaderCmp;