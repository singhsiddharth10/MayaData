package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Servlet implementation class loginCheck
 */
@WebServlet("/loginCheck")
public class loginCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginCheck() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
    //this api is used to check for user authentication i.e the user is valid or not.
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection con = null;
		Statement stmt = null;
		//String sql = "Select * "
		
		try {
			con = DatabaseConnector.getConnection();
			//preparedStatement = connection.prepareStatement(query);
			BufferedReader br =  new BufferedReader(new InputStreamReader(request.getInputStream()));
			 String data = "";
			 
			 String url = null;
			 data = br.readLine();
			 System.out.print(data);
			 //System.out.println("dgdfgdg");
		     //data=data.substring(8, data.length()-2);
			 JsonObject jsonObject = new JsonParser().parse(data).getAsJsonObject();
			 String username = jsonObject.get("username").getAsString();
			 String password = jsonObject.get("password").getAsString();
			 System.out.println(username);
			 System.out.println(password);
			 String sql = "Select count(*) as total from users where username = '" + username + "' and pass = '" + password + "'";
			 stmt = con.createStatement();
			 int count = 0;
			 ResultSet rs = stmt.executeQuery(sql);
			 while(rs.next())
			 	count = rs.getInt("total");
			
			 	
			System.out.print(count);
			if(count == 1) {
				System.out.print("yes");
				response.getWriter().write("yes");
			}else {
				response.getWriter().write("no");
			}
		     //System.out.println(data);
			
		}catch(Exception e) {
			
		}
	}

}
