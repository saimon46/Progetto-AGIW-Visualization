<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
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
				Keyword Doc usata:
				<h1>${documentsController.docs.title}</h1>
			</div>
			<div>
			<h5>Che e' successo? Viene creato un oggetto JsonDoc col titolo in base alla keyword 
				e stampato il suo attributo. Passo successivo: Manipolare il Json di Elastic</h5>
			</div>
		</div>
	</f:view>	
</body>
</html>