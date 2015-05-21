<%@ include file="/common/taglibs.jsp"%>

<page:applyDecorator name="default">

<head>
    <title><fmt:message key="403.title"/></title>
    <meta name="heading" content="<fmt:message key='403.title'/>"/>
</head>

<div class="hero-unit center">
	<h1>Error 403</h1><br />
    <h5>
    	<fmt:message key="403.message">
        	<fmt:param><c:url value="/mainMenu"/></fmt:param>
    	</fmt:message>
	</h5>
</div>
</page:applyDecorator>