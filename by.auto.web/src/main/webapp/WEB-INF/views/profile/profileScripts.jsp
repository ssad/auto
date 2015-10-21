<%@ page session="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript">
    $script.ready("knockout", function () {
        $script("<c:url value="/resources/js/app/controllers/profile.js?v=${applicationVersion}"/>", "profile");
    });

    $script.ready(["profile"], function () {
        $(document).ready(function () {
            var viewModel = new Profile();
            ko.applyBindings(viewModel, document.getElementById("profileForm"));
        });
    });
</script>