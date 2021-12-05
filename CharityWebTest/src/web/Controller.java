package web;

import javax.servlet.*; 
import javax.servlet.http.*;

import model.*;

import java.io.*; 
import java.util.*;

/**
 * Controller for home page
 * @author Jason Tsui, 400073151
 *
 */
public class Controller extends HttpServlet { 
	
	/**
	 * Directs user input
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)	throws IOException, ServletException {  
		PrintWriter writer = response.getWriter();
		writer.println("<html>");
		String c = request.getParameter("input"); 
		int command;
		try {
	    	command = Integer.parseInt(c);
	    }
	    catch (NumberFormatException e) {
	      command = -1;
	    }
		
	    if (command == 1) {
			response.sendRedirect("searchName.jsp");
	    }
	    else if (command == 2) {
	    	response.sendRedirect("searchBnum.jsp");
	    }
	    else if(command == 3) {
	    	response.sendRedirect("searchAll.jsp");
	    }
	    else {
	    	response.sendRedirect("Home.html");    
	     }
	} 
}	
	

