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
			<label for="usr"><font size="30">User "${username}" Votes: </font></label>
		
			<table class="table table-bordered">  
				<tr>
					<th>Election Title</th>
	    			<th>College</th> 
	    			<th>Department</th>
	    			<th>Date</th>
				</tr> 
				
      			<c:forEach items="${votes}" var="vote">
	            	<tr>
		                <td>${vote.election}</td>
		                <td>${vote.college}</td>
		                <td>${vote.department}</td>
		                <td>${vote.date}</td>
	            	</tr>
        		</c:forEach>
      		</table>
		</div>
	</body>
</html>