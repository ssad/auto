<%@ page session="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <title>Internal Error | Auto</title>
    <meta http-equiv="content-type" content="text/html;charset=utf-8"/>
    <meta name="description" content="Auto"/>
    <meta name="keywords" content=""/>
</head>

<body>
<h2>Internal Error</h2>
<p>&nbsp;</p>
<pre style="display: none">
<%
    try {
        // The Servlet spec guarantees this attribute will be available
        final Throwable exception = (Throwable) request.getAttribute("javax.servlet.error.exception");

        if (exception != null) {
            if (exception instanceof ServletException) {
                // It's a ServletException: we should extract the root cause
                final ServletException sex = (ServletException) exception;
                Throwable rootCause = sex.getRootCause();
                if (rootCause == null)
                    rootCause = sex;
                out.println("** Root cause is: "+ rootCause.getMessage());
                rootCause.printStackTrace(new java.io.PrintWriter(out));
            }
            else {
                // It's not a ServletException, so we'll just show it
                exception.printStackTrace(new java.io.PrintWriter(out));
            }
        }
        else  {
            out.println("No error information available");
        }

        // Display cookies
        out.println("\nCookies:\n");
        final Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                out.println(cookies[i].getName() + "=[" + cookies[i].getValue() + "]");
            }
        }

    } catch (final Exception ex) {
        ex.printStackTrace(new java.io.PrintWriter(out));
    }
%>
</pre>
<p>&nbsp;</p>

</body>
