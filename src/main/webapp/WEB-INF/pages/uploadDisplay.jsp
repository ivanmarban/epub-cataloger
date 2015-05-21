<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="display.title"/></title>
    <meta name="menu" content="AdminMenu"/>
</head>

<div class="span10">
    <h2><fmt:message key="display.heading"/></h2>
    <p><fmt:message key="epubUploaded.message"/></p>

    <table class="table-striped" cellpadding="5">
        <tr>
            <th align="left"><fmt:message key="epubUploadInfo.filename"/></th>
            <td><s:property value="fileFileName"/></td>
        </tr>
        <tr>
            <th align="left"><fmt:message key="epubUploadInfo.filesize"/></th>
            <td><s:property value="file.length()"/> bytes</td>
        </tr>
    </table>
    
    <div id="actions" class="form-actions">
        <a class="btn btn-primary" href="listEpubs" >
            <i class="icon-ok icon-white"></i>
            <fmt:message key="button.done"/>
        </a>
        <a class="btn" href="uploadFile" >
            <i class="icon-upload"></i>
            <fmt:message key="epubUploadInfo.uploadAnother"/>
        </a>
    </div>
</div>
