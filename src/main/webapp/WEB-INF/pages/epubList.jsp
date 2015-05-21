<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="epubList.title"/></title>
    <meta name="menu" content="EpubMenu"/>
</head>


<script type="text/javascript">
	//var msgDelConfirm = "<fmt:message key="delete.confirm"><fmt:param value="${delObject}"/></fmt:message>";
	var msgDelConfirm="";
</script>

<script type="text/javascript">
	$(document).ready(function() {
		var countChecked = function() {
			var n = $("input:checked").length;
			if (n == 0) {
				$('#buttonDelete').prop('disabled', true);
			} else {
				$('#buttonDelete').prop('disabled', false);
				if (n>1){
					msgDelConfirm = "<fmt:message key="delete.confirm.various.epubs"/>";
				}
				else{
					msgDelConfirm = "<fmt:message key="delete.confirm.epub"/>";
				}
			}
		};
		countChecked();
		$("input[type=checkbox]").on("click", countChecked);
	});
</script>

<!-- <div class="span10"> -->
<div>
    <h2><fmt:message key="epubList.heading"/></h2>

    <form method="get" action="${ctx}/listEpubs" id="searchForm" class="form-search">
    <div id="search" class="input-append">
        <input type="text" size="20" name="q" id="query" value="${param.q}"
               placeholder="<fmt:message key="search.enterTerms"/>" class="input-medium search-query"/>
        <button id="button.search" class="btn" type="submit">
            <i class="icon-search"></i> <fmt:message key="button.search"/>
        </button>
    </div>
   

    <fmt:message key="epubList.message"/>

    <div id="actions" class="form-actions">
        <a class="btn btn-primary" href="<c:url value='/uploadEpub'/>" >
            <i class="icon-plus icon-white"></i> <fmt:message key="button.add"/>
        </a>
        <a class="btn" href="<c:url value="/mainMenu"/>" >
            <i class="icon-ok"></i> <fmt:message key="button.done"/>
        </a>
        <s:submit type="button" cssClass="btn btn-danger" method="deleteList" key="button.delete"
        	onclick="return confirmMessage(msgDelConfirm)" theme="simple" id="buttonDelete">
            <i class="icon-trash"></i>
            <fmt:message key="button.delete"/>
        </s:submit>
    </div>

	<display:table name="epubs" class="table table-condensed table-striped table-hover" 
    	requestURI="" id="epubList" export="true" pagesize="10"
    	decorator="com.github.ivanmarban.util.EpubDecorator">
        <display:column media="html"><input id="deleteEpub" type="checkbox" name="deleteList" value="${epubList.id}|${epubList.md5}"/></display:column>
        <display:column property="title" sortable="true" titleKey="epub.title"/>
        <display:column property="authors" sortable="true" titleKey="epub.authors"/>
        <display:column property="subjects" sortable="true" titleKey="epub.subjects"/>
        <display:column sortProperty="publication" sortable="true" titleKey="epub.publication">
             <fmt:formatDate value="${epubList.publication}" pattern="${datePattern}"/>
        </display:column>
        <display:column property="description" media="html" sortable="false" titleKey="epub.description" maxWords="10"/>
        <display:column property="actions" media="html" titleKey="epub.actions" />
        
        <display:setProperty name="paging.banner.item_name"><fmt:message key="epubList.epub"/></display:setProperty>
        <display:setProperty name="paging.banner.items_name"><fmt:message key="epubList.epubs"/></display:setProperty>

        <display:setProperty name="export.excel.filename"><fmt:message key="epubList.title"/>.xls</display:setProperty>
        <display:setProperty name="export.csv.filename"><fmt:message key="epubList.title"/>.csv</display:setProperty>
        <display:setProperty name="export.pdf.filename"><fmt:message key="epubList.title"/>.pdf</display:setProperty>
    </display:table>
    </form>
</div>

