import React from "react";
import {getHistory} from "../../component/history";

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
        let detailId = evt.currentTarget.getAttribute("data-detail-id");

        this.props.clickHandler (detailId);

        evt.stopPropagation();
        evt.preventDefault();
    }

    handleDetailGroupList (evt) {
        let detailGroupListId = evt.currentTarget.getAttribute("data-detail-group-list-id");

        // this.props.clickHandler (detailId);
        getHistory().push("/pictureDetail/" + detailGroupListId);

        evt.stopPropagation();
        evt.preventDefault();
    }

    render() {

        /*let details = [];

        for (let i = 0; i < this.state.data.length;i++) {
            details.push();
        }*/
        let self = this;
        let detailGroupInfoArr = this.props.detailGroupInfo;
        console.log("detailGroupInfoArr");
        console.log(detailGroupInfoArr);

        return (
            <div>
                <span id="SPAN_1569">{detailGroupInfoArr && detailGroupInfoArr.length > 0 && detailGroupInfoArr[0].name}</span>
                <div className = "detail_group_info">
                    <div id="dg_DIV_1">
                        <ul id="dg_UL_2">
                            {detailGroupInfoArr && detailGroupInfoArr.map(function (elm) {

                                return (<div className="detail_group_list_item">
                                    <div className="detail_group_list_item__image">
                                        <img src={elm.url} width="200" />
                                    </div>
                                    <div className="detail_group_list_item__details">
                                        <span className="detail_group_list_item__detail_list_title">Деталь найдена в узле 4102</span>
                                        <ul>
                                            {elm.detailDtoList && elm.detailDtoList.map(function (detailInfoDto) {
                                                return (<li className="detail_group_list_item__detail">
                                                    <div class="detail_group_list_item__code_title">
                                                        <span className="detail_group_list_item__code">
                                                            {detailInfoDto.originalArticle}
                                                        </span>
                                                        <span className="detail_group_list_item__title">
                                                        <a onClick={self.handleDetailInfo.bind(self)} data-detail-id={detailInfoDto.id}>
                                                            {detailInfoDto.name}
                                                        </a>
                                                    </span>
                                                    </div>
                                                    <span className="detail_group_list_item__price">
                                                        <a onClick={self.handleDetailInfo.bind(self)} data-detail-id={detailInfoDto.id}>
                                                            Цена
                                                        </a>
                                                    </span>
                                                </li>);
                                            })}
                                        </ul>

                                        <a onClick={self.handleDetailGroupList.bind(self)} data-detail-group-list-id={elm.id}>
                                            Посмотреть на схеме
                                        </a>
                                    </div>
                                </div>)


                                /*return <li id="dg_LI_3" data-detail-id = {elm.detailId} onClick = {self.handleDetailInfo.bind(self)}>
                                    <span>{elm.detailName}</span>
                                    <img src="https://img.avtosoft.net/KIA201903/200/1/REURPH01/3232711.gif?s=7172&amp;k=516b7e35ce12be5672707f3d8da5117d"
                                         alt={elm.detailName}
                                         id="dg_IMG_5" />
                                </li>*/
                            })}
                        </ul>
                    </div>
                </div>


            </div>



        )
    }
}

export default DetailGroupInfo;
