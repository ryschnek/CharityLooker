package web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ClientExperimental;
import javax.servlet.*; 
import javax.servlet.http.*;
import model.*;
import java.io.*; 
import java.util.*;

/**
 * Servlet for searching a business numbers
 * @author Jason Tsui, 400073151
 *
 */
public class searchBnum extends HttpServlet { 
	
	/**
	 * Takes user input, selects charity and directs to Output.jsp
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)	throws IOException, ServletException {  
		
		String c = request.getParameter("Bnum");
		c = c.toUpperCase();
		Return client = new Return();         
		Charity result = client.getBnum(c); 
		if(result == null) {
			String[] result2 = client.getFuzzyBnum(c);
			request.setAttribute("styles", result2); 
			RequestDispatcher view = request.getRequestDispatcher("OutputAllString.jsp"); 
			view.forward(request, response);   
		}else {
			request.setAttribute("styles", result); 
			RequestDispatcher view = request.getRequestDispatcher("Output.jsp"); 
			view.forward(request, response);    
		}
	} 
}	