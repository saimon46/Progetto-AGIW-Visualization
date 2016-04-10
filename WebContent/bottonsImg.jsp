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

<body>
	<div align="left">
		<h:form id="bottons" styleClass="form-horizontal">
			<div class="col-md-11">
				<div class="form-group">
					<div class="col-sm-2">
						<h:commandButton id="Imgnext" styleClass="btn btn-info"
							value="Avanti" action="#{imagesController.addPages}" />
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-1">
						<h:commandButton id="Imgback" styleClass="btn btn-info"
							value="Indietro" action="#{imagesController.removePages}" />
					</div>
				</div>
			</div>
		</h:form>
	</div>
</body>


<!-- include javascript, jQuery FIRST -->
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script
	src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>