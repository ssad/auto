(function (window, undefined) {
    var auto = {
        config: {},
        localized: {
        },
        api: {
            isSuccess: function (response) {
                return response && response.type === "SUCCESS";
            },
            isError: function (response) {
                return response && response.type === "ERROR";
            }
        },
        util: {
        },
        validate: {
        },
        constants: {
            APPLICATION_JSON_CHARSET_UTF8: "application/json; charset=utf-8",
            PAGE_SIZES: [20, 40, 60],
            DEFAULT_PAGE_SIZE: 20,
            MAX_LENGTH: 1000,
            DATEPICKER_DEFAULT_FORMAT: "dd-mm-yy",
            MOMENT_DEFAULT_FORMAT: "DD-MM-YYYY",
            MOMENT_ISO_FORMAT: "YYYY-MM-DD",
            AUTO_CLOSE_INTERVAL: 5000
        }
    };

    window.auto = $.extend({}, window.auto || {}, auto);

})(window);