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
	<jsp:include page="header.jsp" />
	<br><br>
		<div align="left">
			<div class="col-sm-offset-1">
				<c:forEach var="image" items="#{imagesController.imgs}">
					<table>
						<tr><td><font color="blue">${image.img.titleSource}</font> (Score: <b>${image.score}</b>)</td></tr>
						<tr><td><font color="green">${image.img.urlSource}</font></td></tr>
					</table>
				<br><br>
				</c:forEach>
				
			</div>
		</div>
	<jsp:include page="bottonsImg.jsp" />
	</f:view>	
</body>
</html>