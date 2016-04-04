<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<head>
<title>Documenti trovati</title>
</head>

<body>
	<f:view>
		<div align="center">
			<div>
				<c:forEach var="doc" items="#{documentsController.docs}">
						<tr>
							<td>${doc.score}</td>
							<td>${doc.doc.title}</td>
						</tr>
					</c:forEach>
			</div>
		</div>
	</f:view>	
</body>
</html>