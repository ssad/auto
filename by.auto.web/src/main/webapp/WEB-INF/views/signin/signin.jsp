<%@ page session="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="middle-box text-center loginscreen animated fadeInDown">
    <div class="ibox">
        <h3><s:message code="signin.title" htmlEscape="false"/> <s:message code="auto.title" htmlEscape="false"/></h3>
        <form class="m-t" role="form" action="<c:url value="/signin/authenticate"/>" method="post">
            <div class="form-group">
                <input tabindex="1" id="login" name="j_username" type="text" class="form-control" placeholder="<s:message code="login.label" htmlEscape="false"/>">
            </div>
            <div class="form-group">
                <input tabindex="2" id="password" name="j_password" type="password" class="form-control" placeholder="<s:message code="password.label" htmlEscape="false"/>">
            </div>
            <div class="form-group">
                <div>
                    <label>
                        <input name="_spring_security_remember_me" type="checkbox"> <s:message code="remember.me.label" htmlEscape="false"/>
                    </label>
                </div>
            </div>
            <c:if test="${not empty error}">
                <div class="alert alert-danger">
                    <s:message code="signin.error" htmlEscape="false"/>
                </div>
            </c:if>

            <button type="submit" class="btn btn-primary block full-width m-b"><s:message code="signin"/></button>

            <a href="#"><small>Forgot password?</small></a>
            <p class="text-muted text-center"><small>Не имеете аккаунта?</small></p>
            <a class="btn btn-sm btn-white btn-block" href="<c:url value="${ROUTE_SIGNUP}"/>"><s:message code="signup" htmlEscape="false"/></a>
        </form>
    </div>
</div>
