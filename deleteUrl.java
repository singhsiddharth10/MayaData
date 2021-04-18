package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Servlet implementation class deleteUrl
 */
@WebServlet("/deleteUrl")
public class deleteUrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deleteUrl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	

	//This api is used to delete the existing url from the database;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con = null;
		Statement stmt = null;

		
		try {
			con = DatabaseConnector.getConnection();
			//preparedStatement = connection.prepareStatement(query);
			BufferedReader br =  new BufferedReader(new InputStreamReader(request.getInputStream()));
			 String data = "";
			 String url = null;
			 data = br.readLine();
			 System.out.println(data);
			 JsonObject jsonObject = new JsonParser().parse(data).getAsJsonObject();
			 System.out.println(jsonObject.toString());
			 String user = jsonObject.get("user").getAsString();
			 String link = jsonObject.get("link").getAsString();
			 System.out.println(user);
			 System.out.println(link);
			 
			 stmt = con.createStatement();
			 String sql = "Delete from url where uid = '" + user + "' and original_url = '" + link + "'";
			 stmt.executeUpdate(sql);
			 
		     
		    
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
