<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Ricerca fallita</title>
</head>

<body>
	<f:view>
	<br><br>
		<jsp:include page="header.jsp" />
		<div align="left">
			<div class="col-sm-offset-1">
				<h4>La ricerca dei documenti per categoria non ha avuto alcun esito...</h4>
			</div>
		</div>
	<br><br>
	<jsp:include page="backDocCategorized.jsp" />
	</f:view>
</body>
</html>