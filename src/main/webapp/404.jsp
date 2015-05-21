<%@ include file="/common/taglibs.jsp"%>

<page:applyDecorator name="default">

<head>
    <title><fmt:message key="404.title"/></title>
    <meta name="heading" content="<fmt:message key='404.title'/>"/>
</head>

<div class="hero-unit center">
	<h1>Error 404</h1><br />
    <h5>
    	<fmt:message key="404.message">
        	<fmt:param><c:url value="/mainMenu"/></fmt:param>
    	</fmt:message>
	</h5>
</div>

</page:applyDecorator>