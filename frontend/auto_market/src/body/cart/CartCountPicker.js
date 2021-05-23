import React from "react";

// export default Header;

class CartCountPicker extends React.Component {

    constructor () {
        super();

        this.data = [];
    }

    onItemClick(evt) {
        console.log(evt);

        console.log(evt.target.value);
    }

    render() {
        let value = this.props.value;
        this.detaiId = this.props.detailId;

        let optionList = [];

        for (let i = 0; i < 145; i++) {

            if (value === i) {
                optionList.push(<option selected value={i}>{i}</option>);
            } else {
                optionList.push(<option value={i}>{i}</option>);
            }
        }

        return (<select onChange={this.onItemClick.bind(this)}>
            {optionList}
        </select>);
    }
}

export default CartCountPicker;