import React from "react";

class DetailGroupInfo extends React.Component {

    /*constructor () {
        super();

        this.state = {
            data: []
        };
    }*/

    componentDidMount() {
    }

    handleDetailInfo (evt) {
        let detailId = evt.currentTarget.getAttribute("data-detail-id")

        this.props.clickHandler (detailId);
    }

    render() {

        /*let details = [];

        for (let i = 0; i < this.state.data.length;i++) {
            details.push();
        }*/
        let self = this;
        let detailGroupInfoArr = this.props.detailGroupInfo;

        return (
            <div>
                <span id="SPAN_1569">{detailGroupInfoArr && detailGroupInfoArr.length > 0 && detailGroupInfoArr[0].name}</span>
                <div className = "detail_group_info">
                    <div id="dg_DIV_1">
                        <ul id="dg_UL_2">
                            {detailGroupInfoArr && detailGroupInfoArr.map(function (elm) {
                                return <li id="dg_LI_3" data-detail-id = {elm.detailId} onClick = {self.handleDetailInfo.bind(self)}>
                                    <span>{elm.detailName}</span>
                                    <img src="https://img.avtosoft.net/KIA201903/200/1/REURPH01/3232711.gif?s=7172&amp;k=516b7e35ce12be5672707f3d8da5117d"
                                         alt={elm.detailName}
                                         id="dg_IMG_5" />
                                </li>
                            })}
                        </ul>
                    </div>
                </div>


            </div>



        )
    }
}

export default DetailGroupInfo;
