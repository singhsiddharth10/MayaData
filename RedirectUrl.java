package test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RedirectUrl
 */
@WebServlet("/")
public class RedirectUrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RedirectUrl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    //this api is used to redirect the link to correct source.
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uri = request.getRequestURI();
		String newuri = uri.substring(1,uri.length());
		System.out.println(newuri);
		try {
			Connection con = DatabaseConnector.getConnection();
			Statement stmt = con.createStatement();
			String sql = "Select original_url from url where random_string_url = '" + newuri + "'";
			ResultSet rs = stmt.executeQuery(sql);
			String url = "https://";
			while(rs.next()) {
				url += rs.getString("original_url");
			}
			
			System.out.println(url);
			response.sendRedirect(url);
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	

}
