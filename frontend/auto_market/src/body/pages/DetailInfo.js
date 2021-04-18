


import React from "react";
import { withRouter } from "react-router";
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
            console.log(data);

            debugger;
            // count
            // totalSumm
            getGlobalController.updateHeaderCartInfo(data.count);
        });

        // getGlobalController.updateHeaderCartInfo(5);
    }

    render() {

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
                            {/*<div id="dinfo_DIV_7">
                                <button type="button" id="dinfo_BUTTON_8">
                                </button>
                                <div id="dinfo_DIV_9">
                                    <svg id="dinfo_svg_10">
                                        <mask id="dinfo_mask_11">
                                            <path id="dinfo_path_12">
                                            </path>
                                        </mask>
                                        <path id="dinfo_path_13">
                                        </path>
                                        <path id="dinfo_path_14">
                                        </path>
                                        <circle id="dinfo_circle_15">
                                        </circle>
                                    </svg>
                                </div>
                            </div>*/}
                            <div id="dinfo_DIV_16">
                                <span id="dinfo_SPAN_17"><a rel="noreferrer" id="dinfo_A_18">{this.props.detailInfo.detailName}</a></span>
                                <div id="dinfo_DIV_19">
                                    <a rel="noreferrer" id="dinfo_A_20">{this.props.detailInfo.article}</a>
                                </div><span id="dinfo_SPAN_21">{this.props.detailInfo.detailGroupName}</span>
                            </div>
                        </div>
                        <div id="dinfo_DIV_22">
                            <div id="dinfo_DIV_23">
                                <div id="dinfo_DIV_24">
                                    <div id="dinfo_DIV_25">
                                        <div id="dinfo_DIV_26">
                                            <div id="dinfo_DIV_27">
                                                <div id="dinfo_DIV_33">
                                                    {this.props.detailInfo.quantity} шт.
                                                </div>
                                                <div id="dinfo_DIV_34">
                                                    упаковка {this.props.detailInfo.amountInPackage} шт.
                                                </div>
                                                <div id="dinfo_DIV_35">
                                                    <span id="dinfo_SPAN_36">{this.props.detailInfo.price}</span>
                                                    
                                                    <span id="dinfo_SPAN_37"><span id="dinfo_SPAN_38">руб.</span></span>
                                                </div>
                                                <div id="dinfo_DIV_34">
                                                    {this.props.detailInfo.producerName}
                                                </div>
                                                <div id="dinfo_DIV_34">
                                                    {this.props.detailInfo.countryName}
                                                </div>
                                            </div>
                                            <div id="dinfo_DIV_39">
                                                <button onClick={this.addToCart.bind(this)}
                                                        className="button_style"
                                                        type="button"
                                                        data-detail-id={this.props.detailInfo.id}>
                                                    <div class="flex_center">
                                                        В корзину
                                                    </div>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                {/*<div id="dinfo_DIV_59">
                                    <span id="dinfo_SPAN_60"><span id="dinfo_SPAN_61"><span id="dinfo_SPAN_62">Ещё предложения от 623</span> <span id="dinfo_SPAN_63"><span id="dinfo_SPAN_64">руб.</span></span> <span id="dinfo_SPAN_65">и 4 дней</span></span></span>
                                    <svg id="dinfo_svg_66">
                                        <path id="dinfo_path_67">
                                        </path>
                                    </svg>
                                </div>*/}
                            </div>
                            {/*<div id="dinfo_DIV_68">
                                <h3 id="dinfo_H3_69">
                                    Другие пункты выдачи в вашем городе
                                </h3>
                                <div id="dinfo_DIV_70">
                                    <div id="dinfo_DIV_71">
                                        <div id="dinfo_DIV_72">
                                            <div id="dinfo_DIV_73">
                                                <div id="dinfo_DIV_74">
                                                    <div id="dinfo_DIV_75">
                                                        <div id="dinfo_DIV_76">
                                                            <div id="dinfo_DIV_77">
                                                                4,6
                                                            </div>
                                                            <svg id="dinfo_svg_78">
                                                                <path id="dinfo_path_79">
                                                                </path>
                                                            </svg>
                                                        </div>
                                                    </div>
                                                    <div id="dinfo_DIV_80">
                                                        91 шт.
                                                    </div>
                                                    <div id="dinfo_DIV_81">
                                                        Завтра
                                                        <div id="dinfo_DIV_82">
                                                            СТО Автоцентр КИА "Вега-Моторс"
                                                        </div>
                                                    </div>
                                                    <div id="dinfo_DIV_83">
                                                        <span id="dinfo_SPAN_84">568</span> <span id="dinfo_SPAN_85"><span id="dinfo_SPAN_86">руб.</span></span>
                                                    </div>
                                                </div>
                                                <div id="dinfo_DIV_87">
                                                    <div id="dinfo_DIV_88">
                                                        СТО Автоцентр КИА "Вега-Моторс"
                                                    </div>
                                                    <button type="button" id="dinfo_BUTTON_89">
                                                    </button>
                                                    <div id="dinfo_DIV_90">
                                                        В корзину
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div id="dinfo_DIV_91">
                                            <div id="dinfo_DIV_92">
                                                <div id="dinfo_DIV_93">
                                                    <div id="dinfo_DIV_94">
                                                        <div id="dinfo_DIV_95">
                                                            <div id="dinfo_DIV_96">
                                                                4,7
                                                            </div>
                                                            <svg id="dinfo_svg_97">
                                                                <path id="dinfo_path_98">
                                                                </path>
                                                            </svg>
                                                        </div>
                                                    </div>
                                                    <div id="dinfo_DIV_99">
                                                        10 шт.
                                                    </div>
                                                    <div id="dinfo_DIV_100">
                                                        2 дня
                                                        <div id="dinfo_DIV_101">
                                                            Магазин на ул Карпинского, 109
                                                        </div>
                                                    </div>
                                                    <div id="dinfo_DIV_102">
                                                        <span id="dinfo_SPAN_103">748</span> <span id="dinfo_SPAN_104"><span id="dinfo_SPAN_105">руб.</span></span>
                                                    </div>
                                                </div>
                                                <div id="dinfo_DIV_106">
                                                    <div id="dinfo_DIV_107">
                                                        Магазин на ул Карпинского, 109
                                                    </div>
                                                    <button type="button" id="dinfo_BUTTON_108">
                                                    </button>
                                                    <div id="dinfo_DIV_109">
                                                        В корзину
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>*/}
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default DetailInfo;
