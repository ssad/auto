<%@ page session="false" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<div id="messageContainer" class="row">
    <div class="span12" id='messageContent'>
        <tiles:insertAttribute name="successMessageTemplate" ignore="true"/>
        <tiles:insertAttribute name="errorMessageTemplate" ignore="true"/>
    </div>
</div>

<%@include file="/WEB-INF/views/templates/validation/pageMessages.jsp" %>
<tiles:insertAttribute name="formTemplate"/>
