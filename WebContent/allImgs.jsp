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
				Keyword Img usata:
				<h1>${documentsController.imgs.title}</h1>
			</div>
			<div>
			<h5>Che e' successo? Viene creato un oggetto JsonImg col titolo in base alla keyword 
				e stampato il suo attributo. Passo successivo: Manipolare il Json di Elastic</h5>
			</div>
		</div>
	</f:view>	
</body>
</html>