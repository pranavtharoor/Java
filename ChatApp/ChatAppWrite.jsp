<%@ page import="java.sql.*" %>
<%! String msg;%>
<%
try {
	Class.forName("com.mysql.jdbc.Driver");
	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
	PreparedStatement insertMsg = con.prepareStatement("INSERT INTO ChatApp VALUES (?)");
	
	msg = request.getParameter("msg").trim();
	if(msg != "") {
	 	insertMsg.setString(1, msg);
		insertMsg.executeUpdate();
	}
} catch(Exception e) {
	%>Error<%
}
%>