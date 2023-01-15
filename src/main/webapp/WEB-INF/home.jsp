<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
	<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="ISO-8859-1">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Fredoka+One'>
	<link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Figtree'>
	<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" href="/css/style.css"/>
	<title>Home | Fitfolio</title>
</head>
<body>
    <div class="container">
		<div class="header mb-3">
	       	<h1 style="font-family: 'Fredoka One', sans-serif;" class="page-title fw-bolder">Fitfolio</h1>
			<a class="btn btn-danger logout" href="/logout">Logout</a>
		</div>
		<div class="header table-header">
			<h4>Baby Names:</h4>
			<a class="btn btn-success new-project" href="/names/new">+ New Name</a>
		</div>
		<div class="table-holder rounded">
			<table class="table table-hover text-center mb-5">
				<thead>
				    <tr class="fs-5">
				        <th class="table-action">Action</th>
				        <th>Name</th>
				        <th>Gender</th>
				        <th>Origin</th>
				        <th>Votes</th>
				    </tr>
				</thead>
				<tbody>
				<c:forEach var='oneBabyName' items='${babyNames}'>
				    <tr class="align-middle">
				        <c:choose>
				        <c:when test="${votedFor.contains(oneBabyName)}">
					        <td><a class="btn btn-danger form-btn" href="<c:url value='/remove/vote/${user.id}/${oneBabyName.id}'/>">
					        Remove Vote</a></td>
				        </c:when>
				        <c:otherwise>
					        <td><a class="btn btn-success form-btn" href="<c:url value='/vote/${user.id}/${oneBabyName.id}'/>">
					        UpVote!</a></td>
				        </c:otherwise>
				        </c:choose>
				        <td><a class="text-decoration-none" href="<c:url value='/names/${oneBabyName.id}'/>">
				        <c:out value="${oneBabyName.name}"/></a></td>
				        <td><c:out value="${oneBabyName.gender}"/></td>
				        <td><c:out value="${oneBabyName.origin}"/></td>
				        <td><c:out value="${oneBabyName.users.size()}"/></td>
				    </tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
    </div>
    <script type="text/javascript" src="/js/app.js"></script>
    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>