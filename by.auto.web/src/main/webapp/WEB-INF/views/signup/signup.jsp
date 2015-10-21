<%@ page session="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="middle-box text-center loginscreen animated fadeInDown">
    <div class="ibox">
        <h3><s:message code="signup.title" htmlEscape="false"/> <s:message code="auto.title" htmlEscape="false"/></h3>
        <form id="signUpForm">
            <div class="form-group text-left">
                <div>
                    <label>
                        <input type="checkbox" value="" data-bind="checked: account.isCompany"> <s:message code="company.label" htmlEscape="false"/>
                    </label>
                </div>
            </div>
            <div class="form-group">
                <input data-bind="value: account.login, hasfocus:true" type="text" class="form-control" placeholder="<s:message code="login.label" htmlEscape="false"/>">
            </div>
            <div class="form-group">
                <input data-bind="value: account.emailPhone" type="text" class="form-control" placeholder="<s:message code="email.phone.label" htmlEscape="false"/>">
            </div>
            <div class="form-group" data-bind="visible: account.isCompany">
                <input data-bind="value: account.company" type="text" class="form-control" placeholder="<s:message code="company.name.label" htmlEscape="false"/>">
            </div>
            <div class="form-group" data-bind="visible: account.isCompany">
                <select class="form-control m-b" data-bind="options: categories,
                                            optionsValue: 'category',
                                            optionsText: 'categoryText',
                                            value: account.category,
                                            optionsCaption: '<s:message code="category.label" htmlEscape="false"/>'"></select>

            </div>
            <div class="form-group">
                <input data-bind="value: account.password" type="password" class="form-control" placeholder="<s:message code="password.label" htmlEscape="false"/>">
            </div>
            <div class="form-group">
                <input data-bind="value: account.confirmPassword" type="password" class="form-control" placeholder="<s:message code="confirm.password.label" htmlEscape="false"/>">
            </div>
            <div class="form-group">
                <div>
                    <label>
                        <input type="checkbox" value="" data-bind="checked: account.licenseAgreement"> <s:message code="agreement.label" htmlEscape="false"/>
                    </label>
                </div>
            </div>
            <button type="submit" data-bind="click: signUp" class="btn btn-primary block full-width m-b"><s:message code="signup.register.btn.company"/></button>
        </form>
        <p class="text-muted text-center"><small>Уже зарегистрировались?</small></p>
        <a class="btn btn-sm btn-white btn-block" href="<c:url value="${ROUTE_SIGNIN}"/>"><s:message code="signin" htmlEscape="false"/></a>
    </div>
</div>