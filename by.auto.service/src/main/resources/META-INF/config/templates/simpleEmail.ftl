<#import "setup.ftl" as setup>
<html>
<body>
Hi ${to},
<@setup.msg key="email.test.body.arguments" args=args/>

<@setup.msg "email.test.body"/>

<p style='color:green;'>${body}</p>

Regards,<br/>
${from}.
</body>
</html>
