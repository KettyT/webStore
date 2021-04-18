let historyElm = null;

export const setHistory = function (history) {
    if (!historyElm) {
        historyElm = history;
    }
};

export const getHistory = function () {
    return historyElm;
};