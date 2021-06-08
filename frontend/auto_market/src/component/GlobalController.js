


export const getGlobalController = {
    updateHeaderCartInfo: null,
    catalogStateFunction: null,
    updatePictureDetailInfo: null,
    backToOrder: null // возврат к заказу после возврата.
};

export const controlFunctions = {
    updateCartPage: function () {
        window.utils.getHttpPromise({
            method: "GET",
            url: "/api/cart/getFullCartInfo/",
            contentType: "application/json"
        }).then(function (response) {
            let data = JSON.parse(response);
            console.log(data);

            getGlobalController.catalogStateFunction("cartInfo", data);
        });
    },
    setItemToCartAndUpdate: function (detailId, quantity) {
        window.utils.getHttpPromise({
            method: "POST",
            url: "/api/cart/setToCart/",
            contentType: "application/json",
            jsonData: {
                id: detailId,
                count: quantity
            }
        }).then(function (response) {
            let data = JSON.parse(response);
            console.log(data);

            controlFunctions.updateCartPage();
        }, function (response) {
            let data = JSON.parse(response);
            console.log(data);

            controlFunctions.setUserMessage("Произошла ошибка", data.message);
        });
    },
    clearCart: function (callback) {
        window.utils.getHttpPromise({
            method: "POST",
            url: "/api/cart/clearCart/",
            contentType: "application/json",
            jsonData: {

            }
        }).then(function (response) {
            let data = JSON.parse(response);
            console.log(data);

            controlFunctions.updateCartPage();

            if (callback) {
                callback();
            }

        }, function (response) {
            let data = JSON.parse(response);
            console.log(data);

            controlFunctions.setUserMessage("Произошла ошибка", data.message);
        });
    },
    setUserMessage: function (title, message) {
        let popupWindow = document.querySelector(".popup_window");
        let overlay = document.querySelector(".overlay");

        let titleElm = popupWindow.querySelector(".title");
        let messageBody = popupWindow.querySelector(".message_body");

        titleElm.innerHTML = title;
        messageBody.innerHTML = message;

        popupWindow.classList.remove("hidden");
        overlay.classList.remove("hidden");

        overlay.addEventListener("click", function () {
            popupWindow.classList.add("hidden");
            overlay.classList.add("hidden");
        });
    },
    logout: function () {
        window.utils.getHttpPromise({
            method: "GET",
            url: "/api/auth/logout",
            contentType: "application/json",
            jsonData: {

            }
        }).then(function (response) {
            let data = JSON.parse(response);
            console.log(data);

            controlFunctions.updateCartStatistics();
            controlFunctions.updateCartPage();
        }, function (response) {
            let data = JSON.parse(response);
            console.log(data);


            controlFunctions.setUserMessage("Произошла ошибка", data.message);
        });
    },
    updateCartStatistics: function () {

        window.utils.getHttpPromise({
            method: "GET",
            url: "/api/cart/getCartStatistics",
            contentType: "application/json",
            jsonData: {
            }
        }).then(function (response) {
            let data = JSON.parse(response);
            console.log(data);

            getGlobalController.updateHeaderCartInfo(data.count, data);
        });


    }
};

/*export const catalogStateUpdater = function (key, objToCatalogState) {
    getGlobalController.catalogState(Object.assign());
};*/


