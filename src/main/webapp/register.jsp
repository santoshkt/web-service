<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration page</title>
<link rel="stylesheet" type="text/css" href="style.css" media="screen" />
</head>
<body>

	<div id="container">
		<div id="header">Registration Page</div>
		<div id="main">
			<br />
			<%
				if (request.getAttribute("successMsg") != null) {
			%>
			<div id="success-msg">${successMsg}</div>
			<%
				}

				else {

					if (request.getAttribute("errorMsg") != null) {
			%>
			<div id="error-msg">${errorMsg}</div>
			<%
				}
			%>

			<br />
			<center>Please enter values for all fields.</center>
			<br />
			<form action="RegisterServlet" method="post">
				<table align="center">
					<tr>
						<td>First Name:</td>
						<td><input type="text" name="firstname"></td>
					</tr>
					<tr>
						<td>Last Name:</td>
						<td><input type="text" name="lastname"></td>
					</tr>
					<tr>
						<td>Email Address:</td>
						<td><input type="text" name="email"></td>
					</tr>
					<tr>
						<td>Password:</td>
						<td><input type="password" name="pwd"></td>
					</tr>
					<tr>
						<td>Confirm Password:</td>
						<td><input type="password" name="cpwd"></td>
					</tr>
					<tr>
						<td colspan="2" style="text-align: center"><input
							type="submit" value="Register"></td>
					</tr>
				</table>
			</form>

			<%
				}
			%>
		</div>

		<div id="footer">This page is designed by Santosh K Tadikonda -
			stadikon@gmu.edu</div>
	</div>
</body>
</html>