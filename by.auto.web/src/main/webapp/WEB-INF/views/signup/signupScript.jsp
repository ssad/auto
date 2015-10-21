<%@ page session="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<s:url value="${ROUTE_API_SIGNUP}" htmlEscape="true" var="signUpUrl"/>
<s:url value="${ROUTE_HOME}" htmlEscape="true" var="homeUrl"/>

<script type="text/javascript">
    $script.ready("knockout", function () {
        $script("<c:url value="/resources/js/app/controllers/signUp.js?v=${applicationVersion}"/>", "signUpViewModel");
    });

    $script.ready(["signUpViewModel"], function () {
        $(document).ready(function () {
            var options = {
                "signUpUrl":"${signUpUrl}",
                "homeUrl":"${homeUrl}",
                "categories": [
                    <c:forEach items="${categories}" var="value" varStatus="loop">
                    {
                        "id": "<s:escapeBody javaScriptEscape="true">${value.id}</s:escapeBody>",
                        "name": "<s:escapeBody javaScriptEscape="true">${value.name}</s:escapeBody>"
                    }
                    <c:if test="${not loop.last}">,</c:if>
                    </c:forEach>
                ]
            };
            var viewModel = new SignUpModel(options);
            ko.applyBindings(viewModel, document.getElementById("signUpForm"));
        });
    });
</script>