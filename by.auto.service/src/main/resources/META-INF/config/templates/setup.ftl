<#--
 * message
 *
 * Macro to translate a message code into a message.
 -->
<#macro msg key, args=arguments><#if locale?exists && messages?exists>${messages.getMessage(key, args, locale)}</#if></#macro>
