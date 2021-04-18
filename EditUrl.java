package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Servlet implementation class EditUrl
 */
@WebServlet("/EditUrl")
public class EditUrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditUrl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	//This api is used to change the end point of the existing url.
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection con = null;
		Statement stmt = null;

		
		try {
			con = DatabaseConnector.getConnection();
			BufferedReader br =  new BufferedReader(new InputStreamReader(request.getInputStream()));
			 String data = "";
			 String url = null;
			 data = br.readLine();
			 System.out.println(data);
			 JsonObject jsonObject = new JsonParser().parse(data).getAsJsonObject();
			 System.out.println(jsonObject.toString());
			 String user = jsonObject.get("user").getAsString();
			 String link = jsonObject.get("link").getAsString();
			 String newlink = jsonObject.get("newlink").getAsString();
			 System.out.println(user);
			 System.out.println(link);
			 System.out.println(newlink);
			 
			 stmt = con.createStatement();
			 String sql = "Update url set original_url = '" + newlink + "' where original_url = '" + link + "' and uid = '" + user + "'";
			 
			 stmt.executeUpdate(sql);
			 
		     
		    
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
