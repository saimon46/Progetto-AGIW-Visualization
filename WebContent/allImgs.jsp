<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Immagini trovate</title>
</head>

<body>
	<f:view>
		<div align="center">
			<div>
				<c:forEach var="img" items="#{documentsController.imgs}">
						<tr>
							<td>${img.score}</td>
							<td>${img.img.titleSource}</td>
						</tr>
					</c:forEach>
			</div>
		</div>
	</f:view>	
</body>
</html>