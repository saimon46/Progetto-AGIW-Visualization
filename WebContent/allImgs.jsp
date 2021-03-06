<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>LabelSearch - Immagini trovate</title>
</head>

<body>
	<f:view>
		<jsp:include page="header.jsp" />
		<div>
			<div class="text-left col-sm-4">
				<font style="font-size: x-small;">Tempo di ricerca: <b>${imagesController.timeSearch}</b> s</font>
			</div>
			<div class="text-center col-sm-4">
				<font style="font-size: x-small;">Immagini Trovate: <b>${imagesController.countImgs}</b></font>
			</div>
			<div class="text-right col-sm-4">
				<font style="font-size: x-small;">Pagina: <b>${imagesController.numberPage}</b></font>
			</div>
		</div>
		<br>
		<div align="left">
			<div class="table-responsive">
				<br>
				<table id="ImgsTable" class="table">
					<tr>
						<c:forEach var="image" items="#{imagesController.imgs}"
							varStatus="status">
							<jsp:useBean id="status"
								type="javax.servlet.jsp.jstl.core.LoopTagStatus" />
							<c:if test="<%=status.getCount() < 5%>">
								<td><a href="${image.img.urlSource}"><img width="220"
										class="img-thumbnail img-responsive" alt="Responsive image"
										src="${image.img.urlImg}"></a></td>
							</c:if>
							<c:if test="<%=status.getCount() >= 5 && status.getCount() < 9%>">
								<c:if test="<%=status.getCount() == 5%>">
									<tr>
								</c:if>
								<td><a href="${image.img.urlSource}"><img width="220"
										class="img-thumbnail img-responsive" alt="Responsive image"
										src="${image.img.urlImg}"></a></td>
							</c:if>
							<c:if test="<%=status.getCount() >= 9%>">
								<c:if test="<%=status.getCount() == 9%>">
									<tr>
								</c:if>
								<td><a href="${image.img.urlSource}"><img width="220"
										class="img-thumbnail img-responsive" alt="Responsive image"
										src="${image.img.urlImg}"></a></td>
							</c:if>
						</c:forEach>
					</tr>
				</table>
			</div>
		</div>
		<jsp:include page="bottonsImg.jsp" />
	</f:view>
</body>
</html>