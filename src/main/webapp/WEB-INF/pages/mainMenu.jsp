<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.github.ivanmarban.Constants" %>
<head>
    <title><fmt:message key="mainMenu.title"/></title>
    <meta name="menu" content="MainMenu"/>
</head>
<body class="home">
	<h2><fmt:message key="mainMenu.heading"/></h2>
	<p><fmt:message key="mainMenu.message"/></p>
	
	<ul class="glassList">
	    <li>
	        <a href="<c:url value='/editProfile'/>"><fmt:message key="menu.user"/></a>
	    </li>
	    <% if (request.isUserInRole(Constants.USER_ROLE)){%>
		    <li>
		        <a href="<c:url value='/listEpubs'/>"><fmt:message key="menu.epubs.list"/></a>
		    </li>
		    <li>
		        <a href="<c:url value='/uploadEpub'/>"><fmt:message key="menu.epubs.add"/></a>
		    </li>
		<% } %>
		<% if (request.isUserInRole(Constants.ADMIN_ROLE)){%> 
			<li>
		        <a href="<c:url value='/admin/users'/>"><fmt:message key="menu.admin.users"/></a>
		    </li>
			<li>
		        <a href="<c:url value='/admin/activeUsers'/>"><fmt:message key="mainMenu.activeUsers"/></a>
		    </li>
		    <li>
		        <a href="<c:url value='/admin/reload'/>"><fmt:message key="menu.admin.reload"/></a>
		    </li>
		<% } %> 
	</ul>
</body>