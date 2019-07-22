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
			<form action="CreateTable">
				<div class="form-group">
					<label for="usr">Table IP Address:</label>
					<input type="text" class="form-control" id="table_ip" name="table_ip" required>
				</div>
				
				<div class="form-group">
					<label for="usr">Insert Department ID:</label>
					<br>
					<label for="note"><font color="green">${colDep}</font></label>
					<br>
					<input type="text" class="form-control" id="dep_id" name="dep_id" required>
				</div>
				
				<div class="form-group">
					<label for="usr">Insert Elections:</label>
					<br>
					<label for="note"><font color="red">Please separate them by comma without spaces (Ex: "Election X,Election Y")</font></label>
					<br>
					<label for="note"><font color="green">Elections: ${elString}</font></label>
					<br>
					<textarea name="electionsList" rows="10" cols="160" required></textarea>
				</div>

				<input type="submit" value="Create"/>
			</form>
		</div>
		
	</body>
</html>
