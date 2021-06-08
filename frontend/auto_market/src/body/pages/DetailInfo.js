import React from "react";
import {getGlobalController} from "../../component/GlobalController";

class DetailInfo extends React.Component {

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
    }

    addToCart(evt) {
        let detailId = evt.currentTarget.getAttribute("data-detail-id");
        let self = this;

        window.utils.getHttpPromise({
            method: "POST",
            url: "/api/cart/addToCart",
            contentType: "application/json",
            jsonData: {
                id: detailId,
                count: 1
            }
        }).then(function (response) {
            let data = JSON.parse(response);

            getGlobalController.updateHeaderCartInfo(data.count);
        });

        // getGlobalController.updateHeaderCartInfo(5);
    }

    render() {

        let self = this;

        if (!this.props.detailInfo) {
            return <div></div>;
        }

        return (
            <div id="dinfo_DIV_1">
                <div id="dinfo_DIV_2">
                    <h2 id="dinfo_H2_3">
                        Искомый товар
                    </h2>
                </div>
                <div id="dinfo_DIV_4">
                    <div className="ware_container">
                        <div id="dinfo_DIV_6">
                            <div id="dinfo_DIV_16">
                                <span id="dinfo_SPAN_17"><a rel="noreferrer" id="dinfo_A_18">{this.props.detailInfo.name}</a></span>
                                <div id="dinfo_DIV_19">
                                    <a rel="noreferrer" id="dinfo_A_20">{this.props.detailInfo.originalArticle}</a>
                                </div><span id="dinfo_SPAN_21">{
                                    this.props.detailInfo.detailInfoDtoList && this.props.detailInfo.detailInfoDtoList[0]
                                    && this.props.detailInfo.detailInfoDtoList[0].detailGroupListTextTitle
                                }</span>
                            </div>
                        </div>
                        <div id="dinfo_DIV_22">
                            <div id="dinfo_DIV_23">
                                <div id="dinfo_DIV_24">

                                    {this.props.detailInfo.detailInfoDtoList && this.props.detailInfo.detailInfoDtoList.map(function (detailInfoDto) {
                                        return (<div id="dinfo_DIV_25">
                                            <div id="dinfo_DIV_26">
                                                <div id="dinfo_DIV_27">
                                                    <div id="dinfo_DIV_33">
                                                        {detailInfoDto.quantity} шт.
                                                    </div>
                                                    <div className="dinfo_DIV_34">
                                                        упаковка {detailInfoDto.amountInPackage} шт.
                                                    </div>
                                                    <div className="dinfo_DIV_34">
                                                        {detailInfoDto.producerName}
                                                    </div>
                                                    <div className="dinfo_DIV_34">
                                                        {detailInfoDto.countryName}
                                                    </div>
                                                    <div id="dinfo_DIV_35">
                                                        <span id="dinfo_SPAN_36">{detailInfoDto.price}</span>

                                                        <span id="dinfo_SPAN_37"><span
                                                            id="dinfo_SPAN_38">руб.</span></span>
                                                    </div>
                                                </div>
                                                <div id="dinfo_DIV_39">
                                                    <button onClick={self.addToCart.bind(self)}
                                                            className="button_style"
                                                            type="button"
                                                            data-detail-id={detailInfoDto.id}>
                                                        <div className="flex_center">
                                                            В корзину
                                                        </div>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>);
                                    })}


                                </div>
                            </div>

                            <h2>Аналоги от других производителей</h2>
                            <div id="dinfo_DIV_23">
                                <div id="dinfo_DIV_24">

                                    {this.props.detailInfo.analogInfoDtoList && this.props.detailInfo.analogInfoDtoList.map(function (detailInfoDto) {
                                        return (<div className="row_full_width">
                                            <span id="dinfo_SPAN_17"><a rel="noreferrer" className="dinfo_A_18_cls">{detailInfoDto.detailName}</a></span>
                                            <div id="dinfo_DIV_25">
                                                <div id="dinfo_DIV_26">
                                                    <div id="dinfo_DIV_27">
                                                        <div id="dinfo_DIV_33">
                                                            {detailInfoDto.quantity} шт.
                                                        </div>
                                                        <div className="dinfo_DIV_34">
                                                            упаковка {detailInfoDto.amountInPackage} шт.
                                                        </div>
                                                        <div className="dinfo_DIV_34">
                                                            {detailInfoDto.producerName}
                                                        </div>
                                                        <div className="dinfo_DIV_34">
                                                            {detailInfoDto.countryName}
                                                        </div>
                                                        <div id="dinfo_DIV_35">
                                                            <span id="dinfo_SPAN_36">{detailInfoDto.price}</span>

                                                            <span id="dinfo_SPAN_37"><span
                                                                id="dinfo_SPAN_38">руб.</span></span>
                                                        </div>
                                                    </div>
                                                    <div id="dinfo_DIV_39">
                                                        <button onClick={self.addToCart.bind(self)}
                                                                className="button_style"
                                                                type="button"
                                                                data-detail-id={detailInfoDto.id}>
                                                            <div className="flex_center">
                                                                В корзину
                                                            </div>
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>);
                                    })}


                                </div>
                            </div>

                        </div>



                    </div>
                </div>
            </div>
        )
    }
}

export default DetailInfo;
