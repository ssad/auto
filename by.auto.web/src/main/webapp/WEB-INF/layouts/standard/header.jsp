<%@ page session="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<div class="row border-bottom white-bg">
    <nav class="navbar navbar-static-top" role="navigation">
        <div class="navbar-header">
            <button aria-controls="navbar" aria-expanded="false" data-target="#navbar" data-toggle="collapse" class="navbar-toggle collapsed" type="button">
                <i class="fa fa-reorder"></i>
            </button>
            <a href="<c:url value="${ROUTE_HOME}"/>" class="navbar-brand">Auto</a>
        </div>
        <div class="navbar-collapse collapse" id="navbar">
            <ul class="nav navbar-nav">
                <li class="dropdown">
                    <a aria-expanded="false" role="button" href="#" class="dropdown-toggle" data-toggle="dropdown"> <s:message code="header.catalog.label" htmlEscape="false"/> <span class="caret"></span></a>
                    <ul role="menu" class="dropdown-menu">
                        <li><a href="">СТО, Мойки, Шиномонтаж</a></li>
                        <li><a href="">Красота</a></li>
                        <li><a href="">Хавчик</a></li>
                    </ul>
                </li>
                <li>
                    <a aria-expanded="false" role="button" href="#"> <s:message code="header.poster.label" htmlEscape="false"/></a>
                </li>
                <li>
                    <a aria-expanded="false" role="button" href="#"> <s:message code="header.discounts.label" htmlEscape="false"/></a>
                </li>
            </ul>
            <ul class="nav navbar-top-links navbar-right">
                <sec:authorize access="isAnonymous()">
                    <li>
                        <a href="<c:url value="${ROUTE_SIGNIN}"/>">
                            <i class="fa fa-sign-in"></i> <s:message code="signin" htmlEscape="false"/>
                        </a>
                    </li>
                    <li>
                        <a href="<c:url value="${ROUTE_SIGNUP}"/>">
                            <i class="fa fa-ticket"></i> <s:message code="signup" htmlEscape="false"/>
                        </a>
                    </li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false">
                            <i class="fa fa-tasks"></i>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="<c:url value="${ROUTE_PROFILE}"/>">
                                    <div>
                                        <i class="fa fa-database"></i> <s:message code="profile.title" htmlEscape="false"/>
                                    </div>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li>

                    </li>
                    <li>
                        <a href="<c:url value="${ROUTE_SIGNOUT}"/>">
                            <i class="fa fa-sign-out"></i> <s:message code="signout" htmlEscape="false"/>
                        </a>
                    </li>
                </sec:authorize>
            </ul>
        </div>
    </nav>
</div>