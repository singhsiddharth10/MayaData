package test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;


//This is logic for url shortening in which have used random number to generate unique random key.

public class UrlShortening {
	Connection connection;
	Statement stmt;
	String username;

	private static Connection getConnection() throws Exception {
		Connection con = DatabaseConnector.getConnection();
		return con;
	}
	private String domain; 
	private char myChars[]; 
							
	private Random myRand; 
	private int keyLength; 

	UrlShortening() {
		myRand = new Random();
		keyLength = 8;
		myChars = new char[62];
		for (int i = 0; i < 62; i++) {
			int j = 0;
			if (i < 10) {
				j = i + 48;
			} else if (i > 9 && i <= 35) {
				j = i + 55;
			} else {
				j = i + 61;
			}
			myChars[i] = (char) j;
		}
		domain = "http://fkt.in";
	}

	UrlShortening(int length, String newDomain, String username) {
		this();
		this.keyLength = length;
		this.username=username;
		if (!newDomain.isEmpty()) {
			newDomain = sanitizeURL(newDomain);
			domain = newDomain;
		}
	}

	public String shortenURL(String longURL) {
		String shortURL = "";
		
		try {
			connection = getConnection();
			stmt = connection.createStatement();
			
			
			if (validateURL(longURL)) {
				longURL = sanitizeURL(longURL);
				String sql = "Select count(original_url) as total from url where original_url = '" + longURL + "' and uid = '"+ username + "'";
				ResultSet rs = stmt.executeQuery(sql);
				//rs.next();
				int count = 0;
				while(rs.next()) {
					count = rs.getInt("total");
				}
				
				if (count == 1) {
					//shortURL = domain + "/" + valueMap.get(longURL);
						//String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
						sql = "Select random_string_url from url where original_url = '" + longURL + "' and uid = '"+ username + "'";
						rs = stmt.executeQuery(sql);
						while(rs.next()) {
							shortURL = rs.getString("random_string_url");
						}
						
					
				} else {
					shortURL = domain + "/" + getKey(longURL);
				}
				
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return shortURL;
	}

	public String expandURL(String shortURL) {
		String longURL = "";
		String key = shortURL;
		//longURL = keyMap.get(key);
		try {
			//String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			connection = getConnection();
			stmt = connection.createStatement();
			String sql = "Select original_url from url where random_string_url = '" + shortURL + "'";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				longURL = rs.getString("original_url");
			}
			connection.close();
			stmt.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return longURL;
	}

	boolean validateURL(String url) {
		return true;
	}

	String sanitizeURL(String url) {
		if (url.substring(0, 7).equals("http://"))
			url = url.substring(7);

		if (url.substring(0, 8).equals("https://"))
			url = url.substring(8);

		if (url.charAt(url.length() - 1) == '/')
			url = url.substring(0, url.length() - 1);
		return url;
	}


	private String getKey(String longURL) {
		String key;
		key = generateKey();
		try {
			String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
			connection = getConnection();
			String sql = "Insert into url values(?,?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, longURL);
			preparedStatement.setString(2, key);
			preparedStatement.setString(3, timeStamp);
			preparedStatement.setString(4, username);
			
			preparedStatement.execute();
			
			
			connection.close();
			preparedStatement.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return key;
	}

	// generateKey
	private String generateKey() {
		String key = "";
		try {
			connection = getConnection();
			boolean flag = true;
			while (flag) {
				key = "";
				for (int i = 0; i <= keyLength; i++) {
					key += myChars[myRand.nextInt(62)];
				}
				stmt = connection.createStatement();
				String sql = "Select count(random_string_url) as total from url where random_string_url = " + "'" + key + "'";
				ResultSet rs = stmt.executeQuery(sql);
				int count = 0;
				while(rs.next()) {
					count  = rs.getInt("total");
				}
				
				
				if(count == 0) {
					flag = false;
				}
				
				
			}
			connection.close();
			stmt.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return key;
		
		
	}


}
