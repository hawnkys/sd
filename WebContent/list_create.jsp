<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Register User</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
	  	
	  	<!-- Bootstrap css imports -->
	  	<link href="bootstrap/css/datepicker.css" rel="stylesheet" type="text/css" />
	</head>
	
	<body>
		<jsp:include page="NavBar.jsp"/>
		
		<div class="container">
			<form action="CreateList">
				<div class="form-group">
					<label for="usr">List Name:</label>
					<input type="text" class="form-control" id="name" name="name" required>
				</div>
				
				<div class="form-group">
					<label for="sel1">Select List Type (Select One):</label>
					<select	class="form-control" id="type" name="type">
							<option value="estudante">Estudante</option>
							<option value="docente">Docente</option>
							<option value="funcionario">Funcionario</option>
					</select>
				</div>
				
				<div class="form-group">
					<label for="usr">Members:</label>
					<br>
					<label for="note"><font color="red">Please separate them by comma without spaces(Ex: "User X,User Y"")</font></label>
					<br>
					<label for="note"><font color="green">Users: ${names}</font></label>
					<br>
					<textarea name="listUsers" rows="10" cols="160" required></textarea>
				</div>
				
				<input type="submit" value="Create"/>
			</form>
		</div>
	</body>
</html>