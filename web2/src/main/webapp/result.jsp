<%@ page import="java.util.*"%>
<html>
<body>
	<h1 align="center">Beer Recommendations JSP</h1>
	<p>
		<%
			List<String> styles = (List<String>) request.getAttribute("styles");
			Iterator<String> it = styles.iterator();
			while (it.hasNext()) {
				out.print("<br>try: " + it.next());
			}
		%>
	
</body>
</html>
