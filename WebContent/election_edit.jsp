<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Create Election</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
	  	
	  	<!-- Bootstrap css imports -->
	  	<link href="bootstrap/css/datepicker.css" rel="stylesheet" type="text/css" />
	  	
	</head>
	
	<body>
		<jsp:include page="NavBar.jsp"/>
		
		<div class="container">
			<form action="EditElection">
				<div class="form-group">
					<label for="usr">Title:</label>
					<input type="text" class="form-control" id="title" name="title" placeholder="${e.title}">
				</div>
				
				<div class="form-group">
					<label for="pwd">Description:</label>
					<br>
					<label for="note"><font color="green">Old Description: ${e.description}</font></label>
					<br>
					<textarea name="description" rows="10" cols="160"></textarea>
				</div>
				
				<div class="form-group">
					<label>Start Date:</label>
					<br>
					<label for="note"><font color="green">Old date: ${e.begin}</font></label>
					<div class="input-group date" >
			       		<input type="date" class="form-control" name="start_date"/>
			       		<input type="time" class="form-control" name="start_time"/>
					</div>
				</div>
				
				<div class="form-group">
					<label>End Date:</label>
					<br>
					<label for="note"><font color="green">Old date: ${e.end}</font></label>
					<div class="input-group date" >
			       		<input type="date" class="form-control" name="end_date"/>
			       		<input type="time" class="form-control" name="end_time"/>
					</div>
				</div>
				
				<div class="form-group">
					<label for="usr">Lists:</label>
					<br>
					<label for="note"><font color="red">Please separate them by comma without spaces (Ex: "List X,List Y")</font></label>
					<br>
					<label for="note"><font color="green">Lists available: ${listString}</font></label>
					<br>
					<label for="note"><font color="green">Old Lists: ${electionLists}</font></label>
					<br>
					<textarea name="lists" rows="10" cols="160"></textarea>
				</div>

				<input type="submit" value="Edit"/>
			</form>
		</div>
		
		<!-- Bootstrap js imports -->
		<script type="text/javascript" src="bootstrap/js/bootstrap-datepicker.js"></script>
		
		<script type="text/javascript">
			$(document).ready(function(){
				$('#datetimepicker input').datepicker({
					format: 'mm/dd/yyyy',
					todayHighlight: true,
					autoclose: true
				})
			});
		</script>
		
	</body>
</html>
