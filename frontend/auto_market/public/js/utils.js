
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