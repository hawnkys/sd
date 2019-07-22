<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1">
	  	
	  	<!-- Bootstrap imports -->
	  	<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
	  	<script type="text/javascript" src="bootstrap/js/jquery-3.2.1.min.js"></script>
		<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
	</head>
	
	<body>
		<nav class="navbar navbar-inverse">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="NavBar.jsp">HOME</a>
				</div>
				<ul class="nav navbar-nav">
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">User<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><form action="ColDepInfo"><input type="submit" value="Register" style="width:100%"/></form></li>
							<li><form action="ListUsers"><input type="submit" value="Edit" style="width:100%"/></form></li>
							<li><form action="DeleteUsersList"><input type="submit" value="Delete" style="width:100%"/></form></li>
							<li><form action="CheckUserVotes"><input type="submit" value="Check Votes" style="width:100%"/></form></li>
						</ul></li>
						
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">Manage Colleges/Departments<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><form action="NoNeed"><input type="submit" value="Create College" style="width:100%"/></form></li>
							<li><form action="ListCollegers"><input type="submit" value="Edit College" style="width:100%"/></form></li>
							<li><form action="DeleteListCollegers"><input type="submit" value="Delete College" style="width:100%"/></form></li>
						</ul></li>	
						
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">Elections<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><form action="ListsInfo"><input type="submit" value="Create Election" style="width:100%"/></form></li>
							<li><form action="GetUsersInfo"><input type="submit" value="Create List" style="width:100%"/></form></li>
							<li><form action="GetListsInfo"><input type="submit" value="Edit Lists" style="width:100%"/></form></li>
							<li><form action="GetLists"><input type="submit" value="Delete List" style="width:100%"/></form></li>
							<li><form action="GetElectionsList"><input type="submit" value="Edit Election" style="width:100%"/></form></li>
							<li><form action="GetPastElectionsList"><input type="submit" value="Get Results" style="width:100%"/></form></li>
						</ul></li>	
						
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">Manage Tables<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><form action="GetInfoForTable"><input type="submit" value="Create" style="width:100%"/></form></li>
							<li><form action="GetTableList"><input type="submit" value="Delete" style="width:100%"/></form></li>
							<li><form action="GetTableStatus"><input type="submit" value="Check Active/Deactivate" style="width:100%"/></form></li>
						</ul></li>
				</ul>
			</div>
		</nav>
	</body>
</html>