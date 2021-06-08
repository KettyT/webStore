import React from "react";
import {getHistory} from "../../component/history";
import {Link} from "react-router-dom";

class PictureDetail extends React.Component {

    constructor () {
        super();

        this.state = {
            data: {

            },
            lastDetailGroupInfoId: null
        };

        this.rectabngleMap = {};
        this.detailRowItemMap = {};
    }

    componentDidMount() {
    }

    toDetailPage (evt) {

        evt.stopPropagation();
        evt.preventDefault();

        let target = evt.target;

        let tr = target.closest("tr");
        let detailId = tr.getAttribute("data-detail-id");

        getHistory().push("/detailInfo/" + detailId);
    }

    updatePictureDetailInfo () {
        let self = this;

        let href = window.location.href;
        let lastSlash = href.lastIndexOf("/");
        let detailGroupListId = +href.substr(lastSlash + 1);

        if (detailGroupListId === this.state.lastDetailGroupInfoId) {
            return;
        }

        window.utils.getHttpPromise({
            method: "POST",
            url: "/api/details/getDetailGroupInfoListById",
            contentType: "application/json",
            jsonData: {
                id: detailGroupListId
            }
        }).then(function (response) {
            let data = JSON.parse(response);
            console.log(data);

            self.setState({
                data: data,
                lastDetailGroupInfoId: detailGroupListId
            });
        });
    }

    onDetailHover (evt) {
        let target = evt.target;

        let tr = target.closest("tr");
        let detailId = tr.getAttribute("data-detail-id");
        let rectangle = this.rectabngleMap[detailId];

        rectangle.classList.add("picture_info__popup_rectangle__active");
        tr.classList.add("detail_row_item__active");
    }

    onDetailMouseLeave (evt) {
        let target = evt.target;

        let tr = target.closest("tr");
        let detailId = tr.getAttribute("data-detail-id");
        let rectangle = this.rectabngleMap[detailId];

        rectangle.classList.remove("picture_info__popup_rectangle__active");
        tr.classList.remove("detail_row_item__active");
    }

    onRectangleHover (evt) {
        if (!evt) {
            return;
        }
        let target = evt.target;

        // debugger;
        let detailId = target.getAttribute("data-detail-id");
        let detailRow = this.detailRowItemMap[detailId];

        target.classList.add("picture_info__popup_rectangle__active");
        detailRow.classList.add("detail_row_item__active");
    }

    onRectangleMouseLeave (evt) {
        if (!evt) {
            return;
        }
        let target = evt.target;

        // let tr = target.closest("tr");
        let detailId = target.getAttribute("data-detail-id");
        let detailRow = this.detailRowItemMap[detailId];

        target.classList.remove("picture_info__popup_rectangle__active");
        detailRow.classList.remove("detail_row_item__active");
    }

    render() {

        this.updatePictureDetailInfo();

        if (!this.state.lastDetailGroupInfoId) {
            return (<div></div>);
        }

        let self = this;

        return (<div className="wrapper">
            <div className="column_wrapper">
                <div className="picture_info__left_column">
                    <table>
                        <tr>
                            <th className="picture_info__first_col">№</th>
                            <th className="picture_info__second_col">Описание</th>
                            <th className="picture_info__third_col"></th>
                        </tr>
                        {this.state.data.detailDtoList && this.state.data.detailDtoList.map(function (detailDto) {
                            return (
                                <tr data-detail-id={detailDto.id} className="detail_row_item"
                                    onMouseEnter={self.onDetailHover.bind(self)} onMouseLeave={self.onDetailMouseLeave.bind(self)}>
                                    <td>{detailDto.originalArticle}</td>
                                    <td><Link to={"/detailInfo/" + detailDto.id}>{detailDto.name}</Link></td>
                                    {/*<td><a onClick={self.toDetailPage.bind(self)} href="#">{detailDto.name}</a></td>*/}
                                    <td><a onClick={self.toDetailPage.bind(self)} href="#">Цена</a></td>
                                </tr>
                            );
                        })}
                    </table>
                </div>
                <div className="picture_info__right_column">
                    <div className="picture_info__image_wrapper">
                        <img src={this.state.data.url} alt={this.state.data.textTitle} />

                        {this.state.data.detailImagePositionDtoList && this.state.data.detailImagePositionDtoList.map(function (detailImagePositionDto) {

                            /*let elmStyle = "top: " + detailImagePositionDto.y + "; left: " + detailImagePositionDto.x + "; width: " + detailImagePositionDto.width
                                + "; height: " + detailImagePositionDto.height + ";";*/

                            let elmStyle = {
                                top: detailImagePositionDto.y + "%",
                                left : detailImagePositionDto.x + "%",
                                width: detailImagePositionDto.width + "%",
                                height: detailImagePositionDto.height + "%"
                            };

                            return (<div className="picture_info__popup_rectangle"
                                         style={elmStyle} data-detail-id={detailImagePositionDto.detailId}
                                         onMouseEnter={self.onRectangleHover.bind(self)} onMouseLeave={self.onRectangleMouseLeave.bind(self)}>

                            </div>);
                        })

                        }
                    </div>
                </div>
            </div>
        </div>);

    }

    componentDidUpdate () {
        console.log("componentDidUpdate");

        this.rectabngleMap = {};
        this.detailRowItemMap = {};

        let picturePopupRectangleList = document.querySelectorAll(".picture_info__popup_rectangle");

        for (let i = 0; i < picturePopupRectangleList.length; i++) {
            let detailId = picturePopupRectangleList[i].getAttribute("data-detail-id");

            this.rectabngleMap[detailId] = picturePopupRectangleList[i];
        }

        let detailRowItemList = document.querySelectorAll(".detail_row_item");

        for (let i = 0; i < detailRowItemList.length; i++) {
            let detailId = detailRowItemList[i].getAttribute("data-detail-id");

            this.detailRowItemMap[detailId] = detailRowItemList[i];
        }
    }
}

export default PictureDetail;
