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
 * Servlet for searching all names
 * @author Jason Tsui, 400073151
 *
 */
public class searchAll extends HttpServlet { 
	
	/**
	 * Receives an array of all charities directs to OutputAll.jsp
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)	throws IOException, ServletException {  
		String c = request.getParameter("All");
		Return client = new Return();         
		Charity[] result = client.getAll(); 
		request.setAttribute("styles", result); 
		RequestDispatcher view = request.getRequestDispatcher("OutputAll.jsp"); 
		view.forward(request, response);      
	} 
}	