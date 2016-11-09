<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="flight.FlightService" %>
<%@page import="entity.Flight" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>flights</title>
</head>
<body>
	<h1>Current flights</h1>
	<ul>
	<% 
	  FlightService service = new FlightService();
	   for(Flight flight: service.getFlights()) { %>
	   		<li><%= flight.getFlightNumber() %>
	<% }%>
	</ul>
</body>
</html>
