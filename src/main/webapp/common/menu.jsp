<%@ include file="/common/taglibs.jsp"%>

<menu:useMenuDisplayer name="Velocity" config="navbarMenu.vm" permissions="rolesAdapter">
<div class="nav-collapse collapse">
<ul class="nav">
    <c:if test="${empty pageContext.request.remoteUser}">
        <li class="active">
            <a href="<c:url value="/login"/>"><fmt:message key="login.title"/></a>
        </li>
    </c:if>
    
    <menu:displayMenu name="MainMenu"/>
    <menu:displayMenu name="UserMenu"/>
    <menu:displayMenu name="AdminMenu"/>
    <menu:displayMenu name="EpubMenu"/>
    <menu:displayMenu name="Logout"/>
</ul>
<ul class="nav pull-right">
	<c:if test="${pageContext.request.locale.language != 'en'}">
    	<li>
            <a href="<c:url value="/?locale=en"/>"><fmt:message key="webapp.name"/> in English</a>
        </li>
    </c:if>
	<c:if test="${pageContext.request.locale.language != 'es'}">
    	<li>
            <a href="<c:url value="/?locale=es"/>"><fmt:message key="webapp.name"/> en Espa&ntilde;ol</a>
        </li>
    </c:if>
</ul>
</div>
</menu:useMenuDisplayer>