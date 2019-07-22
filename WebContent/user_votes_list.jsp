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
			<form action="ListUserVotes">
				<table class="table table-bordered">  
					<tr>
						<th>Username</th>
		    			<th>Job</th> 
		    			<th>College</th>
		    			<th>Department</th>
					</tr> 
					
	      			<c:forEach items="${users}" var="user">
	            	<tr>
		                <td>${user.username}</td>
		                <td>${user.job}</td>
		                <td>${user.college}</td>
		                <td>${user.department}</td>
		           		<td><button type="submit" name="username" value="${user.username}" style="width:100%">Check Votes</button></td>
	            	</tr>
	        		</c:forEach>
	      		</table>
			</form>
		</div>
	</body>
</html>