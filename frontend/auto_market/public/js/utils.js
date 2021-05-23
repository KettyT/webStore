
(function () {

    var utils = {};

    utils.host = "";
    utils.activeLanguage = "EN";
    utils.routing = null;

/*    utils.changeLanguage = function (evt) {
        console.log(evt);
        utils.activeLanguage = evt.value;

        utils.routing.route("/");
    };*/

    utils.getHttpPromise = function (config) {
        return new Promise(function(resolve, reject) {

            var xhr = new XMLHttpRequest();
            xhr.open(config.method, config.url, true);
            xhr.setRequestHeader("Content-type", config.contentType);
            /*            xhr.setRequestHeader("Origin", "http://127.0.0.1:63342");
                        xhr.setRequestHeader("Access-Control-Allow-Origin", "http://127.0.0.1:63342");*/

            xhr.onload = function() {
                if (this.status == 200) {
                    resolve(this.response);
                } else {

                    var error;
                    if (this.response) {
                        error = this.response;
                    } else {
                        error = new Error(this.statusText);
                        error.code = this.status;
                    }

                    reject(error);
                }
            };

            xhr.onerror = function() {
                reject(new Error("Network Error"));
            };

            xhr.send(JSON.stringify(config.jsonData));
        });
    };

    window.utils = utils;
})();

(function (){

    function CTextSearcher (config) {
        var self = this;

        this.wrapperElement = document.getElementById(config.wrapperElement);
        this.openSelectElement = this.wrapperElement.querySelector(".selected_container_button");
        this.selectedFields = this.wrapperElement.querySelector("." + config.selectedFields);
        this.input = this.wrapperElement.querySelector("." + config.input);
        self.localData = {};
        self.lastQuery = null;
        self.selection = null;
        self.htmlContent = null;
        self.url = config.url;
        self.templateId = config.templateId;
        self.fields = config.fields;
        self.minChars = 3;
        self.events = config.events;
        self.searchFields = config.searchFields;

        self.selectedFields.addEventListener("click", function (e) {
            var target = e.target;
            target = target.closest(".service_line");

            if (!target) {
                return;
            }

            var id = target.getAttribute("data-id");

            self.select(id);
        });

        self.wrapperElement.addEventListener("blur", function (e) {
            self.closeSelect();
        }, true);

        self.input.addEventListener("input", function (e) {
            e.preventDefault();
            e.stopPropagation();

            if (self.input.value.length == 0) {
                self.disable();
                return;
            }

            if (self.input.value.length < self.minChars) {
                return;
            }

            self.lastInputDate = new Date();
            setTimeout(function () {
                if (new Date() - self.lastInputDate >= 1000) {
                    self.getServices();
                }
            }, 1100)
        }, true);

        self.openSelectElement.addEventListener("click", function (e) {
            self.getServices();
        }, true);
    }

    CTextSearcher.prototype.select = function(id) {
        var self = this;

        self.selection = self.findInLocalDateById((id != 0) ? id : false);

        if (self.selection.aliasList && self.selection.aliasList.length > 0) {
            self.input.value = self.selection.aliasList[0];
        } else {
            self.input.value = self.selection[self.fields.displayField];
        }


        self.closeSelect();

        if (!self.events || !self.events.onSelect) {
            return;
        }

        self.events.onSelect.call(this);
    };

    CTextSearcher.prototype.disable = function(id) {
        var self = this;

        self.selection = null;
        self.localData = null;
        self.closeSelect();

        if (!self.events || !self.events.onDisable) {
            return;
        }

        self.events.onDisable();
    };

    CTextSearcher.prototype.getServices = function () {
        var self = this;

        /*if (self.selectedFields.classList.contains("selected_field__with_data")) {
            self.selectedFields.classList.remove("selected_field__with_data");
            return;
        }*/

        var query = self.input.value;
        if (self.lastQuery == query) {
            self.buildSelect(self.localData);
            return;
        }

        var config = {
            method: "POST",
            url: self.url,
            contentType: "application/json",
            jsonData: {
                limit: 10000,
                page: 1,
                start: 0,
                query: query
            }
        };

        if (self.searchFields) {
            for (var value in self.searchFields) {
                config.jsonData[value] = self.searchFields[value];
            }
        }

        self.setLoad();

        utils.getHttpPromise(config).then(function (response) {
            var data;
            try {
                data = JSON.parse(response);
            } catch (e) {
                data = {};
            }

            // data = data.data;
            self.localData = data;

            self.buildSelect(self.localData);

            self.hideLoad();
        });
    };

    CTextSearcher.prototype.findInLocalDateById = function(id) {
        var self = this;

        if (!id) {
            return;
        }

        for (var i = 0; i < self.localData.length; i++) {
            if (self.localData[i][self.fields.idField] == id) {
                return self.localData[i];
            }
        }
    };

    CTextSearcher.prototype.buildSelect = function (data) {
        var self = this;

        if (!data || data.length < 1) {
            data = [];

            var emptyObject = {};
            emptyObject[self.fields.displayField] = "Ничего не найдено";
            emptyObject[self.fields.idField] = 0;
        }

        var template = "<%data.forEach(function (element, i) { %>\n" +
            "      <div class=\"service_line\" data-id=\"<%=element.id%>\">\n" +
            "        <div class=\"service_line__name\"><%=element.name%></div>\n" +
            "        <div class=\"service_line__alias\">" +
            "           <%element.aliasList.forEach(function (alias, i) { %>" +
            "               <div class=\"service_line__alias_name\"><%=alias%></div>" +
            "           <%}); %>" +
            "        </div>\n" +
            "      </div>\n" +
            "      <%});%>";
        if (!template) {
            console.log("Шаблон указан неверно");
            return;
        }

        self.htmlContent = _.template(template);
        self.selectedFields.innerHTML = self.htmlContent({data: data});
        self.selectedFields.classList.add("selected_field__with_data");
    };

    CTextSearcher.prototype.closeSelect = function () {
        var self = this;
        if (self.selectedFields.classList.contains("selected_field__with_data")) {
            self.selectedFields.classList.remove("selected_field__with_data");
        }
    };

    CTextSearcher.prototype.getValue = function () {
        if (!this.selection) {
            return null;
        }
        return this.selection[this.fields.idField];
    };

    CTextSearcher.prototype.getRow = function () {
        if (!this.selection) {
            return null;
        }
        return this.selection;
    };

    CTextSearcher.prototype.validate = function () {
        if (!this.selection) {
            this.input.classList.add("alert_standart_input");
            return false;
        }

        return true;
    };

    CTextSearcher.prototype.setLoad = function () {
        var loader = this.wrapperElement.querySelector(".preloader_wait");
        loader.style.display = "flex";
    };

    CTextSearcher.prototype.hideLoad = function () {
        var loader = this.wrapperElement.querySelector(".preloader_wait");
        loader.style.display = "none";
    };

    CTextSearcher.prototype.getSearchText = function () {
        return this.input.value;
    };

    CTextSearcher.prototype.getInputElm = function () {
        return this.input;
    };

    window.CTextSearcher = CTextSearcher;
})();

document.addEventListener("DOMContentLoaded", function () {
    var waresSearcher = new CTextSearcher({
        wrapperElement: "main_searcher",
        selectedFields: "selected_field",
        input: "standart_input",
        url: "/api/details/searchPath",
        templateId: "CWaressSearcherTemplate",
        fields: {
            displayField: "name",
            idField: "id"
        },
        searchFields: {
            parentId: null
        },
        events: {
            onSelect: function () {
                /*var selections = this.wrapperElement.parentNode.querySelector(".sections");
                var catName = selections.getAttribute("category_id");
                if (catName && catName !== "") {
                    var categoryParam = "&category_id=" + catName;
                } else {
                    categoryParam = "";
                }
                location.href = "/index.php?route=product/search&search=" + waresSearcher.getRow().name + categoryParam;*/
            }
        }
    });

    let popupWindow = document.querySelector(".popup_window");
    let overlay = document.querySelector(".overlay");

    window.waresSearcher = waresSearcher;
});