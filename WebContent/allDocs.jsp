<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<head>
<title>Documenti trovati</title>
</head>

<body>
Cazzoooo
	<f:view>
		<!--<jsp:include page="corpus.jsp" />-->
		<div align="center">
			<div>
			<h:form>
				<c:forEach var="doc" items="#{documentsController.docs}">
					${doc.title}
				</c:forEach>
				</h:form>
			</div>
		</div>
	</f:view>
</body>
</html>