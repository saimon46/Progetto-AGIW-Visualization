<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<head>
<title>LabelSearch - Documenti categorizzati trovati</title>
</head>

<body>
	<f:view>
		<jsp:include page="header.jsp" />
		<div class="col-sm-4">
			<font style="font-size: x-small;">Tempo di ricerca: <b>${documentsController.timeSearch}</b> s</font>
		</div>
		<br>
		<div class="table-responsive">
			<table class="table">
				<tr>
					<td>
						<div class="col-md-11">
							<c:forEach var="document" items="#{documentsController.docs}">
								<table>
									<tr>
										<td><font style="font-size: larger;" color="blue"><a
												href="${document.doc.url}">${document.doc.title}</a></font> <font
											style="font-size: x-small;">(Score: <b>${document.score}</b>)
										</font></td>
									<tr>
										<td><font color="green">${document.doc.url}</font> - <span
											class="label label-pill label-info">${document.doc.category}</span></td>
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
				</tr>
			</table>
		</div>
		<jsp:include page="bottonsDocCategorized.jsp" />
	</f:view>
</body>
</html>