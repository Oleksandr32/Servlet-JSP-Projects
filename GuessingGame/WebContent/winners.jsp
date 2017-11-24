<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>The Winners List</title>
		<link rel="shortcut icon" href="img/guessing.ico" type="image/x-icon">
		<link rel="stylesheet" href="css/style.css">
	</head>
	<body>
		<div class="container center">
	 		<!--------------------------- Message ---------------------------->
	 		<p>${ infoMessage }</p>
	 		<!--------------------------- Message ---------------------------->
	 		<h1>This is the list of our best winners!</h1>
		 	<a href="${pageContext.request.contextPath}">Play Again!</a>

			<table>
				<thead>
					<tr>
						<th>Name</th>
						<th>Number of Guesses</th>
					</tr>	
				</thead>
				<tbody>
				<!------------------- JAVA List of Winners -------------------->
				<c:forEach items="${ winners }" var="winner">
					<tr>
						<td>${ winner.name }</td>
						<td>${ winner.numOfTries }</td>
					</tr>	
				</c:forEach>
				<!------------------- JAVA List of Winners -------------------->
				</tbody>
			</table>


	 		<a href="${pageContext.request.contextPath}">Play Again!</a>	
 		</div>
	</body>
</html>