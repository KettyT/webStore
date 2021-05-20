import React from "react";

class OrderPage extends React.Component {

    constructor () {
        super();

        this.state = {
            data: {}
        };
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
                    orders: data
                }
            }));
        });
    }

    componentDidMount () {
        this.getOrder();
    }


    render() {
        let self = this;
        /*let orderArr = [];

        for (let i = 0; i < orderArr.length; i++) {
            orderArr.push();
        }*/

        return (<div className="">

        </div>);
    }
}

export default OrderPage;