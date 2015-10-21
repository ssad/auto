var SignUpModel = function (options) {
    var self = this;

    self.account = {
        login:ko.observable(""),
        emailPhone: ko.observable(""),
        password:ko.observable(""),
        confirmPassword:ko.observable(""),
        roleName:ko.observable(""),
        licenseAgreement: ko.observable(true),
        isCompany: ko.observable(true),
        company: ko.observable(""),
        category: ko.observable("")
    };

    self.categories = ko.observableArray();

    self.i18nCategories = function(list){
        var categories = [];
        $.each(list, function (index, value) {
            categories[index] = {
                category: value.id,
                categoryText: value.name
            };
        });
        return categories;
    };

    self.categories(self.i18nCategories(options.categories));

    self.signUp = function() {
        if (self.account.licenseAgreement()) {
            var res = {
                login: self.account.login(),
                emailPhone: self.account.emailPhone(),
                licenseAgreement: self.account.licenseAgreement(),
                password: self.account.password(),
                confirmPassword: self.account.confirmPassword(),
                roleName: self.account.isCompany() ? "Company" : "User"
            };
            if (self.account.isCompany()) {
                res.company = self.account.company();
                res.categoryId = self.account.category();
            }

//            auto.api.overlay('show');
            $.ajax(options["signUpUrl"], {
                contentType:auto.constants.APPLICATION_JSON_CHARSET_UTF8,
                type:"POST",
                validate:self,
                data:ko.toJSON(res),
                success:function (data) {
                    if (auto.api.isSuccess(data)) {
                        window.location.href = options["homeUrl"];
                    }
                },
                error:function () {
                }
            }).always(function () {
//                auto.api.overlay('hide');
            });
        }
    };
};