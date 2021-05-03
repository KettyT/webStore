


export const getGlobalController = {
    updateHeaderCartInfo: null,
    catalogStateFunction: null
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
    }
};

/*export const catalogStateUpdater = function (key, objToCatalogState) {
    getGlobalController.catalogState(Object.assign());
};*/


