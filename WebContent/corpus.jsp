<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap -->
<link href="css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="css/bootstrap-theme.css" rel="stylesheet" type="text/css">
</head>

<body>>
<div align="center">
		<f:view>
			<h:form id="keyword" styleClass="form-horizontal">
				<h:message for="insertKeyword" styleClass="error alert alert-danger" />
				<div>
					<h1>Chi vuoi cercare?</h1>
				</div>
				<div class="form-group">
					<label for="keyword" class="col-sm-1 control-label col-lg-offset-4">Chi vuoi cercare?</label>
					<div class="col-sm-2">
						<h:inputText styleClass="form-control"
							value="#{documentsController.keyword}" required="true"
							requiredMessage="Il campo e' obbligatorio!" id="keyword" />
						<h:message for="keyword" style="color:red" />
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-5 col-sm-2">
						<h:commandButton id="searchDoc" styleClass="btn btn-success" value="Cerca Documenti"
							action="#{documentsController.searchDocs}" />
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-5 col-sm-2">
						<h:commandButton id="searchImage" styleClass="btn btn-success" value="Cerca Immagini"
							action="#{documentsController.searchImgs}" />
					</div>
				</div>
			</h:form>
		</f:view>
	</div>
</body>


<!-- include javascript, jQuery FIRST -->
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script
	src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>