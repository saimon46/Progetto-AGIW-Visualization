<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<!DOCTYPE html>
<html>
<head>
<title>LabelSearch - Ricerca fallita</title>
</head>

<body>
	<f:view>
		<jsp:include page="header.jsp" />
		<br>
		<div align="left">
			<div class="col-md-12">
				<h4>La ricerca dei documenti per categoria non ha avuto alcun esito...</h4>
			</div>
		</div>
	<br><br>
	<jsp:include page="backDocCategorized.jsp" />
	</f:view>
</body>
</html>