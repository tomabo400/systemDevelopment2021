package jp.ac.isc.cloud;

import java.io.*;
import java.sql.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class UserSelectServlet extends HttpServlet {
 private static final long serialVersionUID = 1L;
 protected void doGet(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {

	 try {
		   Connection users = null;
		   try {
		    Class.forName("com.mysql.jdbc.Driver");
		    users = DriverManager.getConnection("jdbc:mysql://localhost/servlet_db", "root","");
		    ArrayList<Member> list = new ArrayList<Member>();
		    Statement state = users.createStatement();
		    ResultSet result = state.executeQuery("SELECT * FROM user_table");
		    while(result.next()) {
		     String id = result.getString("id");
		     String name = result.getString("name");
		     String picture = result.getString("picture");
		     list.add(new Member(id,name,picture));
		    }
		    result.close();
		    state.close();
		    users.close();
		    request.setAttribute("list",list);
		RequestDispatcher rd =
		getServletContext().getRequestDispatcher("/WEB-INF/select.jsp");
		    rd.forward(request,response);
		   //クラスが存在しなかったらエラーを表示
		   } catch(ClassNotFoundException e) {
		    e.printStackTrace();
		   }
		 //SQL実行時エラーが発生したら、エラーを表示
	 } catch(SQLException e) {
	  e.printStackTrace();
	 }
	 }
}