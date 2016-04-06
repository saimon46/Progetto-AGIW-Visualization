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
		<jsp:include page="header.jsp" />
		<br>
		<table>
			<tr>
				<td>
					<div class="col-sm-offset-1">
						<c:forEach var="document" items="#{documentsController.docs}">
							<table>
								<tr>
									<td><font color="blue"><a href="${document.doc.url}">${document.doc.title}</a></font>
										(Score: <b>${document.score}</b>)</td>
								<tr>
									<td><font color="green">${document.doc.url}</font></td>
								</tr>
								<tr>
									<td>${document.doc.description}</td>
								</tr>
							</table>
							<br>
							<br>
						</c:forEach>

					</div>
				</td>
				<td style="vertical-align:top">
					<div class="col-sm-offset-1">
						<h4>Macro Categorie associate</h4>
						<c:forEach var="cat"
							items="#{documentsController.categorybyKeyword}">
						${cat.key} (${cat.value})
						<br>
						</c:forEach>
					</div>
				</td>
			</tr>
		</table>
		<jsp:include page="bottonsDoc.jsp" />
	</f:view>
</body>
</html>