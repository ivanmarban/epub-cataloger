<%@ include file="/common/taglibs.jsp" %>

<head>
    <title>Data Access Error</title>
</head>

<p>
    <h2>Data Access Failure</h2>
    <% out.print( ((Exception) request.getAttribute("exception")).getMessage());%>
</p>

<!--
<% 
((Exception) request.getAttribute("exception")).printStackTrace(new java.io.PrintWriter(out)); 
%>
-->

<a href="mainMenu" onclick="history.back();return false">&#171; Back</a>
