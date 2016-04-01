<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html>
<html>
<head>
<title>Immagini trovate</title>
</head>

<body>
	<f:view>
		<div align="center">
			<div>
				Keyword usata:
				<h:outputText value="#{imgs.title}">
					</h:outputText>
			</div>
		</div>
	</f:view>
</body>
</html>