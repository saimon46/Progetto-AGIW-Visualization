<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap -->
<link href="css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="css/bootstrap-theme.css" rel="stylesheet" type="text/css">
</head>

<body>
	<div align="center">
		<h:form id="keyword" styleClass="form-horizontal">
			<div class="form-group form-group-lg" align="center">
				<h:message for="insertKeyword" styleClass="error alert alert-danger" />
				<div class="col-sm-offset-4 col-md-4">
					<h:inputText styleClass="form-control" value="#{keywordsearch}"
						required="true" requiredMessage="Il campo e' obbligatorio!"
						id="keyword" />
					<h:message for="keyword" style="color:red" />
				</div>
			</div>
			<div class="form-group form-group-lg" align="center">
				<h:commandButton id="searchDoc" styleClass="btn btn-success"
					value="Cerca Documenti"
					action="#{documentsController.searchDocs_begin}">
					<f:setPropertyActionListener
						target="#{documentsController.keyword}" value="#{keywordsearch}" />
					<f:setPropertyActionListener target="#{imagesController.keyword}"
						value="#{keywordsearch}" />
				</h:commandButton>
			</div>
			<div class="form-group form-group-lg" align="center">
				<h:commandButton id="searchImage" styleClass="btn btn-success"
					value="Cerca Immagini"
					action="#{imagesController.searchImgs_begin}">
					<f:setPropertyActionListener
						target="#{documentsController.keyword}" value="#{keywordsearch}" />
					<f:setPropertyActionListener target="#{imagesController.keyword}"
						value="#{keywordsearch}" />
				</h:commandButton>
			</div>
		</h:form>
	</div>
</body>


<!-- include javascript, jQuery FIRST -->
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script
	src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>