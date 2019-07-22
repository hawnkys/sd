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
			<form action="RegisteUser">
				<div class="form-group">
					<label for="usr">Username:</label>
					<input type="text" class="form-control" id="username" name="username" required>
				</div>
				
				<div class="form-group">
					<label for="pwd">Password:</label>
					<input type="password" class="form-control" id="password" name="pw" required>
				</div>
				
				<div class="form-group">
					<label for="sel1">Select Job (Select One):</label>
					<select	class="form-control" id="job" name="job">
							<option value="estudante">Estudante</option>
							<option value="docente">Docente</option>
							<option value="funcionario">Funcionario</option>
					</select>
				</div>
				
				<div class="form-group">
					<label for="sel1">Select College (Select One):</label>
					<select	class="form-control" id="college" name="college">
						<c:forEach items="${colleges}" var="row">
							<option value="${row.name}">${row.name}</option>
				        </c:forEach>
					</select>
				</div>
				
				<div class="form-group" id="${row.id}">
					<label for="sel1">Select Department (Select One):</label>
					<select class="form-control" id="department" name="department">
						<c:forEach items="${colleges}" var="row">
							<c:forEach items="${row.depList}" var="dep">
								<option value="${dep.name}">${dep.name}</option>
			        		</c:forEach>
		        	 	</c:forEach>
					</select>
				</div>
				
				<div class="form-group">
					<label for="addr">Address:</label> 
					<input type="text" class="form-control" id="addr" name="addr" required>
				</div>
				
				<div class="form-group">
					<label for="phone">Phone number:</label>
					<input type="text" class="form-control" id="phone" name="phone" required>
				</div>
				
				<div class="form-group">
					<label for="ccNumber">CC Number:</label>
					<input type="text" class="form-control" id="cc_number" name="cc_number" required>
				</div>
				
				<div class="form-group">
					<label>CC Date:</label>
					<div class="input-group date" id="datetimepicker">
			       		<input type="text" class="form-control" name="date" required/>
					</div>
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
