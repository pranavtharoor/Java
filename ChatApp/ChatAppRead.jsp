<%@ page import="java.sql.*" %>
<%! String msg;%>
<%
try {
	Class.forName("com.mysql.jdbc.Driver");
	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
	PreparedStatement getMsgs = con.prepareStatement("SELECT * FROM ChatApp");
	ResultSet messages = getMsgs.executeQuery();
	while(messages.next()){  
		response.getWriter().println(messages.getString(1) + "<br />");  
	}  
} catch(Exception e) {
}
%>