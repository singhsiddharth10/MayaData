package test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetLongUrl
 */
@WebServlet("/GetLongUrl")
public class GetLongUrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetLongUrl() {
        super();
        // TODO Auto-generated constructor stub
    }

	//This api is used to convert the given url into short url.
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		//For pagination purpose
		String url = "";
		String user = "";
		
		//Retriving data from client side
		if(request.getParameter("url") != null) {
			url = request.getParameter("url");
			System.out.println("url "+ url);
			
		}
		if(request.getParameter("user") != null) {
			user = request.getParameter("user");
			System.out.println("user "+ user);
			
		}
		
		UrlShortening u = new UrlShortening(5, "http://localhost:3000/",user);
		System.out.println("URL:" + url + "\tTiny: "
				+ u.shortenURL(url) + "\tExpanded: "
				+ u.expandURL(u.shortenURL(url)));
		
	}

	

}
