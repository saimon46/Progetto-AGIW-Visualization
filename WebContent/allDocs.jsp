<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Documenti trovati</title>
</head>

<body>
	<f:view>
		<jsp:include page="corpus.jsp" />
		<div align="center">
			<div>
				<c:forEach var="doc" items="#{documentsController.searchDocs}">
					${doc.title}
				</c:forEach>
			</div>
		</div>
	</f:view>
</body>
</html>