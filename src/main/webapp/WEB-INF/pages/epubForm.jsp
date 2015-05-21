<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="epubDetail.title"/></title>
    <meta name="heading" content="<fmt:message key='epubDetail.heading'/>"/>
</head>

<c:set var="delObject" scope="request"><fmt:message key="epubList.epub"/></c:set>
<script type="text/javascript">var msgDelConfirm =
   "<fmt:message key="delete.confirm"><fmt:param value="${delObject}"/></fmt:message>";
</script>

<script type="text/javascript">
	$(document).ready(function() {
		$("#epubForm_epub_authors").tooltip({
			placement : 'top',
			title : '<fmt:message key="epubTooltip.Authors"></fmt:message>',
			delay : { show: 500, hide: 100 },
			trigger : 'focus',
			animation : 'true'
		});
		$("#epubForm_epub_subjects").tooltip({
			placement : 'top',
			title : '<fmt:message key="epubTooltip.Labels"></fmt:message>',
			delay : { show: 500, hide: 100 },
			trigger : 'focus',
			animation : 'true'
		});
	});
</script>

<div class="span2">
    <h2><fmt:message key="epubDetail.heading"/></h2>
    <p><fmt:message key="epubDetail.message"/></p>
</div> 

<div class="span9">
    <s:form id="epubForm" action="saveEpub" method="post" validate="true" cssClass="well form-horizontal">     
		<s:url action="ImageAction" var="imgTag">
			<s:param name="imageId">
				<s:property value="epub.md5"/>
			</s:param>
		</s:url>
		<div class="row-fluid">
			<div class="span3">
				<img width="170" class="img-polaroid" src="<s:property value="#imgTag" />"/>
			</div>
			<s:hidden key="epub.id"/>
	        <s:textfield key="epub.title" required="true" maxlength="255" cssClass="span12"/>
	        <s:textfield key="epub.authors" required="true" maxlength="255" cssClass="span12"/>
	        <s:textfield key="epub.subjects" required="false" maxlength="255" cssClass="span12"/>
	        <s:textfield key="epub.publication" required="false" maxlength="255" size="11" title="date" datepicker="true"/>
	        <s:textarea key="epub.description" required="false" maxlength="4000" rows="10" cssClass="span12"/>  
	        <s:hidden key="epub.md5" required="false" maxlength="255" />
	        <s:hidden key="epub.username" required="false" maxlength="50" />
		</div>
        <div id="actions" class="form-actions">
            <s:submit type="button" cssClass="btn btn-primary" method="save" key="button.save" theme="simple">
                <i class="icon-ok icon-white"></i> <fmt:message key="button.save"/>
            </s:submit>
            <c:if test="${not empty epub.id}">
                <s:submit type="button" cssClass="btn btn-warning" method="delete" key="button.delete"
                    onclick="return confirmMessage(msgDelConfirm)" theme="simple">
                    <i class="icon-trash icon-white"></i> <fmt:message key="button.delete"/>
                </s:submit>
            </c:if>
            <s:submit type="button" cssClass="btn" method="cancel" key="button.cancel" theme="simple">
                <i class="icon-remove"></i> <fmt:message key="button.cancel"/>
            </s:submit>
        </div>

    </s:form>
</div>

<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/scripts/datepicker/css/datepicker.css'/>" />
<script type="text/javascript" src="<c:url value='/scripts/datepicker/js/bootstrap-datepicker.js'/>"></script>
<c:if test="${pageContext.request.locale.language != 'en'}">
<script type="text/javascript" src="<c:url value='/scripts/datepicker/js/locales/bootstrap-datepicker.${pageContext.request.locale.language}.js'/>"></script>
</c:if>
<c:if test="${pageContext.request.locale.language != 'es'}">
<script type="text/javascript" src="<c:url value='/scripts/datepicker/js/locales/bootstrap-datepicker.${pageContext.request.locale.language}.js'/>"></script>
</c:if>
<script type="text/javascript">
    $(document).ready(function() {
        $("input[type='text']:visible:enabled:first", document.forms['epubForm']).focus();
        $('.input-append.date').datepicker({autoclose: true, format: "<fmt:message key='calendar.format'/>", weekStart: "<fmt:message key='calendar.weekstart'/>", language: '${pageContext.request.locale.language}'});
    });
</script>
