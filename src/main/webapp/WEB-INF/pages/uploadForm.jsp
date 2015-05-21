<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="upload.title"/></title>
	<meta name="menu" content="EpubMenu"/>
</head>

<div class="span2">
	<h2><fmt:message key="upload.heading"/></h2>
	<p><fmt:message key="upload.message"/></p>
</div>

<div class="span7">
	<s:form action="uploadFile!upload" enctype="multipart/form-data" method="post" id="uploadForm" cssClass="well form-horizontal">
		<div class="fileupload fileupload-new" data-provides="fileupload">
			<div class="input-append">
				<div class="uneditable-input span5">
					<i class="icon-file fileupload-exists"></i> <span
						class="fileupload-preview"></span>
				</div>
				<span class="btn btn-file"><span class="fileupload-new"><i class="icon-folder-open"></i></span>
				<span class="fileupload-exists"><i class="icon-folder-open"></i></span>
				<input name="file" type="file" /></span><a href="#" class="btn fileupload-exists" data-dismiss="fileupload"><i class="icon-remove"></i></a>
			</div>
		</div>

		<div id="actions" class="form-actions">
	          <s:submit type="button" key="button.upload" name="upload" cssClass="btn btn-primary" theme="simple">
	            <i class="icon-upload icon-white"></i>
	            <fmt:message key="button.upload"/>
	        </s:submit>
	
	        <a class="btn" href="listEpubs" >
	            <i class="icon-remove"></i>
	            <fmt:message key="button.cancel"/>
	        </a>
	    </div>
	</s:form>
</div>


<script type="text/javascript">
    $(document).ready(function() {
        $("input[type='text']:visible:enabled:first", document.forms['uploadForm']).focus();
    });
</script>

