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
				<h3>La ricerca dei documenti non ha avuto alcun esito...</h3>
			</div>
		</div>
	<br><br>
	<jsp:include page="backDoc.jsp" />
	</f:view>
</body>
</html>