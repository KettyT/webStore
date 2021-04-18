import React from "react";
import {Link} from "react-router-dom";

// export default Header;

class MenuItem extends React.Component {

    /*constructor () {
        super();

        this.data = [];
    }*/

    onItemClick() {
        let children = this.props.children;
        let to = (children.length > 0) ? "/parentDetailGroup/" : "/detailGroupInfo/";

        this.props.clickHandler (to, this.props.id);
    }

    render() {
        // this.props.children

        let children = this.props.children;
        let childArr = [];
        for (let i = 0; i < children.length; i++) {
            childArr.push(<MenuItem onClick={this.onItemClick.bind(this)}
                                    id={children[i].id}
                                    name = {children[i].name}
                                    children = {children[i].detailGroupDtoList}
                                    clickHandler = {this.props.clickHandler}
                            />);
        }

        let to = (children.length > 0) ? "/parentDetailGroup" : "/detailGroupInfo/" + this.props.id;

        return <li>
            {/*<Link to={to}>{this.props.name}</Link>*/}
            <span onClick={this.onItemClick.bind(this)}>{this.props.name}</span>
            {childArr.length > 0 && (<ul>{childArr}</ul>)}
        </li>;
    }
}

export default MenuItem;