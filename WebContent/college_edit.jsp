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
			<form action="EditCollege">
				<div class="form-group">
					<label for="usr">College Name:</label>
					<input type="text" class="form-control" id="name" name="name" placeholder="${col.name}">
				</div>
				
				<div class="form-group">
					<label for="usr">Initials:</label>
					<input type="text" class="form-control" id="initials" name="initials" placeholder="${col.initials}">
				</div>
				
				<div class="form-group">
					<label for="usr">Departments:</label>
					<br>
					<label for="note"><font color="red">Please separate them by comma without spaces (Ex: "Department X,Department Y"")</font></label>
					<br>
					<label for="note"><font color="green">Current departments: ${oldDeps}</font></label>
					<br>
					<textarea name="listDepartments" rows="10" cols="160"></textarea>
				</div>
				
				<input type="submit" value="Edit College"/>
			</form>
		</div>
	</body>
</html>