<%@ page session="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx" %>

<s:eval expression="@webProperties['application.version']" var="applicationVersion" scope="request"/>

<!DOCTYPE html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]> <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]> <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--><html class="no-js"> <!--<![endif]-->
<%@ include file="/WEB-INF/layouts/standard/routes.jspf" %>

    <head>
        <tilesx:useAttribute id="i18_title" name="title" scope="request"/>
        <tilesx:useAttribute id="i18_description" name="description" scope="request" ignore="true"/>
        <meta charset="utf-8">
        <!--[if IE]>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
        <![endif]-->

        <title><s:message code="${i18_title}" htmlEscape="true" text="${i18_title}"/> | <s:message code="auto.title" htmlEscape="false"/></title>

        <tiles:insertAttribute name="meta" ignore="true"/>
        <c:choose>
            <c:when test="${not empty i18_description}">
                <meta name="description" content="<s:message code="${i18_description}" htmlEscape="true" />">
            </c:when>
        </c:choose>
        <meta name="viewport" content="width=1024, maximum-scale=1" />

        <%--<link rel="icon" href="<c:url value='/resources/favicon.png' />" type="image/x-icon"/>--%>
        <%--<link rel="shortcut icon" href="<c:url value='/resources/favicon.png' />" type="image/x-icon"/>--%>
        <%--<link href='//fonts.googleapis.com/css?family=Roboto:400,300,500&amp;subset=latin,cyrillic-ext' rel='stylesheet'--%>
              <%--type='text/css'>--%>

        <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.css?v=${applicationVersion}"/>">
        <link rel="stylesheet" href="<c:url value="/resources/css/style.css?v=${applicationVersion}"/>">
        <link rel="stylesheet" href="<c:url value="/resources/css/animate.css?v=${applicationVersion}"/>">
        <link rel="stylesheet" href="<c:url value="/resources/font-awesome/css/font-awesome.css?v=${applicationVersion}"/>">
        <link rel="stylesheet" href="<c:url value="/resources/css/plugin/toastr/toastr.min.css?v=${applicationVersion}"/>">

        <style type="text/css"></style>

        <tiles:insertAttribute name="styles" ignore="true"/>

        <!--[if lt IE 9]>
        <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
        <!--[if IE]>
        <script type="text/javascript" src="<c:url value="/resources/js/ie.js"/>"></script><![endif]-->

        <script src="<c:url value="/resources/js/vendor/modernizr-2.6.2-respond-1.1.0.min.js"/>"></script>

        <script src="<c:url value="/resources/js/generated/messages_en.js?v=${applicationVersion}"/>"></script>

        <%@ include file="/WEB-INF/layouts/standard/amd.jspf" %>

        <%@ include file="/WEB-INF/layouts/standard/analytics.jspf" %>

        <c:if test="${not empty yandexVerificationCode}">
            <meta name='yandex-verification' content='${yandexVerificationCode}' />
        </c:if>
    </head>

    <body class="top-navigation  pace-done">
        <%@ include file="/WEB-INF/layouts/standard/analytics_ya.jspf" %>
        <!--[if lt IE 7]>
        <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade
            your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to
            improve your experience.</p>
        <![endif]-->

        <div class="pace  pace-inactive">
            <div class="pace-progress" data-progress-text="100%" data-progress="99"
                 style="-webkit-transform: translate3d(100%, 0px, 0px); transform: translate3d(100%, 0px, 0px);">
                <div class="pace-progress-inner"></div>
            </div>
            <div class="pace-activity"></div>
        </div>

        <c:set value="false" var="showCleanHeader" scope="request"/>
        <tilesx:useAttribute id="showCleanHeader" name="showCleanHeader" ignore="true"/>

        <div id="wrapper">
            <div id="page-wrapper" class="gray-bg" style="min-height: 380px;">
                <c:if test="${showCleanHeader eq false}">
                    <tiles:insertAttribute name="header"/>
                </c:if>

                <div class="wrapper wrapper-content">
                    <tiles:insertAttribute name="content"/>
                </div>
                <tiles:insertAttribute name="footer"/>
            </div>
        </div>

        <tiles:insertAttribute name="scripts" ignore="true"/>

    </body>
</html>
