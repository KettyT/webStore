import React from "react";
import {Route, Switch} from "react-router-dom";
import MenuItem from "./detailsTree/MenuItem";
import DetailGroupInfo from "./pages/DetailGroupInfo";
import ParentDetailGroupInfo from "./pages/ParentDetailGroupInfo";
import {getHistory} from "../component/history";
import DetailInfo from "./pages/DetailInfo";
import Cart from "./pages/Cart";
import {controlFunctions, getGlobalController} from "../component/GlobalController";
// import {} from 'react-router-dom';

// export default Header;

class Catalog extends React.Component {

    constructor () {
        super();

        this.state = {
            data: [],
            cartInfo: {}
        };

        getGlobalController.catalogStateFunction = this.updateState.bind(this);
    }

    updateState (key, object) {
        let obj = {};
        obj[key] = object;

        this.setState(Object.assign(this.state, obj));
    }

    handleLindToDetailGroup () {
        const history = getHistory();
        let self = this;

        return function (linkTo, detailGroupId) {
            window.utils.getHttpPromise({
                method: "POST",
                url: "/api/details/getDetailGroupInfoById",
                contentType: "application/json",
                jsonData: {
                    id: +detailGroupId
                }
            }).then(function (response) {
                let data = JSON.parse(response);

                self.setState(Object.assign(self.state, {
                    detailGroupInfo: data
                }));
            });

            history.push(linkTo + detailGroupId);
        }

    }

    getDetailInfo() {
        const history = getHistory();
        let self = this;

        return function (detailId) {
            window.utils.getHttpPromise({
                method: "POST",
                url: "/api/details/getDetailInfo",
                contentType: "application/json",
                jsonData: {
                    id: +detailId
                }
            }).then(function (response) {
                let data = JSON.parse(response);
                console.log(data);

                self.setState(Object.assign(self.state, {
                    detailInfo: data
                }));
            });

            history.push("/detailInfo/" + detailId);
        }
    }

    /*
        представление деталей на диаграмме.
     */
    getDetailGropListInfoById() {
        const history = getHistory();
        let self = this;

        return function (detailId) {
            window.utils.getHttpPromise({
                method: "POST",
                url: "/api/details/getDetailInfo",
                contentType: "application/json",
                jsonData: {
                    id: +detailId
                }
            }).then(function (response) {
                let data = JSON.parse(response);
                console.log(data);

                self.setState(Object.assign(self.state, {
                    detailInfo: data
                }));
            });

            history.push("/detailInfo/" + detailId);
        };
    }

    onTreeSearchKeyDown (evt) {
        let self = this;

        if (evt.keyCode !== 13) {
            return;
        }

        let searchText = window.waresSearcher.getSearchText();

        if (searchText.length < 5) {
            window.waresSearcher.getInputElm.value = "Для поиска, введите не менее 5 символов";
            return;
        }

        window.utils.getHttpPromise({
            method: "POST",
            url: "/api/details/findInDetailGroupTree",
            contentType: "application/json",
            jsonData: {
                query: searchText
            }
        }).then(function (response) {
            let data = JSON.parse(response);
            console.log(data);

            self.setState({
                data: data
            });
        });

    }

    componentDidMount() {
        let self = this;

        // this.updateCartStatistics();

        window.utils.getHttpPromise({
            method: "GET",
            url: "/api/details/getDetailGroupTree",
            contentType: "application/json",
            jsonData: {
            }
        }).then(function (response) {
            let data = JSON.parse(response);
            console.log(data);

            self.setState({
                data: data
            });
        });

        let href = window.location.href;
        if (href.indexOf("detailGroupInfo") !== -1) {
            let lastSlash = href.lastIndexOf("/");
            let detailId = window.location.href.substr(lastSlash + 1);

            self.handleLindToDetailGroup()("/detailGroupInfo/", +detailId);

        }

        if (href.indexOf("detailInfo") !== -1) {
            let lastSlash = href.lastIndexOf("/");
            let detailId = window.location.href.substr(lastSlash + 1);

            self.getDetailInfo()(+detailId);
        }

        if (href.indexOf("/cart") !== -1) {
            controlFunctions.updateCartPage();
        }



    }

    render(props) {
        console.log(this.props.history);
        let tree = [];
        let data = this.state.data;
        let self = this;

        for (let i = 0; i < data.length; i++) {
            tree.push (<MenuItem clickHandler = {self.handleLindToDetailGroup()} id={data[i].id} name = {data[i].name} children = {data[i].detailGroupDtoList} />);
        }

        return (
            <div className = "wrapper">
                <table id="TABLE_1">
                    <tbody id="TBODY_2">
                    <tr id="TR_3">
                        <td id="TD_4">

                            <div id="DIV_5">
                                <div id="DIV_6">
                                    {/*<span id="SPAN_7">Классификатор:</span>*/}

                                    <div id="DIV_8">
                                        Общий каталог
                                    </div>

                                    {/*<div id="DIV_9">
                                        От производителя
                                    </div>*/}

                                    <div id="DIV_10">
                                    </div>
                                </div>
                                <div id="DIV_11">


                                    <div id="DIV_12">
                                        <div id="DIV_13">
                                            <span id="SPAN_14">Поиск:</span>

                                            <div id="main_searcher" className="main_searcher" tabIndex="2">
                                                <div className="form_wrapper__input">
                                                    <input id="street_field" onKeyDown={self.onTreeSearchKeyDown.bind(self)}
                                                           className="standart_input search_field" tabIndex="1"
                                                           placeholder="Для поиска введите не менее 5 символов ..."
                                                           autocomplete="off"/>
                                                    <div className="selected_container_button">
                                                        <span className="selected_button"></span>
                                                    </div>
                                                    <div className="preloader_wait">
                                                        <img className="ui_ico_loading" src="/images/loader.png"
                                                             alt=""/>
                                                    </div>
                                                </div>
                                                <div className="selected_field">

                                                </div>
                                            </div>

                                            {/*<div id="DIV_15">
                                                <div id="DIV_16">
                                                </div>
                                                <input type="text" placeholder="Введите название узла или детали"
                                                       id="INPUT_17"/>
                                                <div id="DIV_18">
                                                </div>
                                            </div>
                                            <div id="DIV_19">
                                            </div>*/}
                                        </div>

                                        <div className="catalog_cmp">
                                            <ul>
                                                {tree}
                                            </ul>
                                        </div>

                                        <div id="DIV_1554">
                                            <span id="SPAN_1555">По вашему запросу ничего не найдено.</span> <span
                                            id="SPAN_1556">Рекомендации:</span> <span id="SPAN_1557">Убедитесь, что все слова написаны без ошибок.<br
                                            id="BR_1558"/>Попробуйте использовать другие ключевые слова</span>
                                        </div>
                                    </div>


                                    <div id="DIV_1559">
                                        <ul id="UL_1560">
                                            <li id="LI_1561">
                                                ДВИГАТЕЛЬ
                                            </li>
                                            <li id="LI_1562">
                                                КУЗОВ
                                            </li>
                                            <li id="LI_1563">
                                                ОСНАЩЕНИЕ КУЗОВА
                                            </li>
                                            <li id="LI_1564">
                                                ТРАНСМИССИЯ
                                            </li>
                                            <li id="LI_1565">
                                                ШАССИ
                                            </li>
                                            <li id="LI_1566">
                                                ЭЛЕКТРИКА
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </td>
                        <td id="TD_1567">
                        </td>
                        <td id="TD_1568">


                            <Switch>
                                <Route path="/detailGroupInfo/:detailId">
                                    <DetailGroupInfo clickHandler = {this.getDetailInfo()} detailGroupInfo={this.state.detailGroupInfo}/>
                                </Route>
                                <Route exact path="/parentDetailGroup" component={ParentDetailGroupInfo} />
                                <Route path = "/detailInfo/:detailId">
                                    <DetailInfo detailInfo = {this.state.detailInfo}/>
                                </Route>
                                <Route path = "/cart">
                                    <Cart cartInfo = {this.state.cartInfo}/>
                                </Route>
                            </Switch>

                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        );
    }
}

export default Catalog;