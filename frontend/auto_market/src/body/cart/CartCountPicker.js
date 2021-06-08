import React from "react";

// export default Header;

class CartCountPicker extends React.Component {

    constructor () {
        super();

        this.data = [];
    }

    onItemClick(evt) {
        if (this.props.onItemClick) {
            this.props.onItemClick(this.props.detailId, +evt.target.value);
        }


        // controlFunctions.setItemToCartAndUpdate(this.props.detailId, +evt.target.value)
    }

    render() {
        let value = this.props.value;
        this.detaiId = this.props.detailId;

        let optionList = [];

        let max = (this.props.maxQuantity) ? (this.props.maxQuantity + 1) : 145;

        for (let i = 0; i < max; i++) {

            if (value === i) {
                optionList.push(<option key={this.props.keyItem} selected value={i}>{i}</option>);
            } else {
                optionList.push(<option key={this.props.keyItem} value={i}>{i}</option>);
            }
        }

        return (<select className="quantity_selection"
                        data-item-id={this.props.detailId}
                        onChange={this.onItemClick.bind(this)}>
            {optionList}
        </select>);
    }
}

export default CartCountPicker;