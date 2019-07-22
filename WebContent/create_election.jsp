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
			<form action="CreateElection">
				<div class="form-group">
					<label for="usr">Title:</label>
					<input type="text" class="form-control" id="title" name="title" required>
				</div>
				
				<div class="form-group">
					<label for="pwd">Description:</label>
					<br>
					<textarea name="description" rows="10" cols="160"></textarea>
				</div>
				
				<div class="form-group">
					<label for="sel1">Select Job (Select One):</label>
					<select	class="form-control" id="job" name="type">
							<option value="Students Core">Students Core</option>
							<option value="General Council">General Council</option>
					</select>
				</div>
				
				<div class="form-group">
					<label>Start Date:</label>
					<div class="input-group date" >
			       		<input type="date" class="form-control" name="start_date" required/>
			       		<input type="time" class="form-control" name="start_time" required/>
					</div>
				</div>
				
				<div class="form-group">
					<label>End Date:</label>
					<div class="input-group date" >
			       		<input type="date" class="form-control" name="end_date" required/>
			       		<input type="time" class="form-control" name="end_time" required/>
					</div>
				</div>
				
				<div class="form-group">
					<label for="usr">Lists:</label>
					<br>
					<label for="note"><font color="red">Please separate them by comma without spaces (Ex: "List X,List Y")</font></label>
					<br>
					<label for="note"><font color="green">Lists available: ${listString}</font></label>
					<br>
					<textarea name="lists" rows="10" cols="160"></textarea>
				</div>

				<input type="submit" value="Create"/>
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
