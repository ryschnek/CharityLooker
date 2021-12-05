package web;

import javax.servlet.*; 
import javax.servlet.http.*;

import model.*;

import java.io.*; 
import java.util.*;

/**
 * Controller for viewing charity information
 * @author Jason Tsui, 400073151
 *
 */
public class ControllerCharity extends HttpServlet { 
	
	/**
	 * Directs user input
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)	throws IOException, ServletException { 
		
		PrintWriter writer = response.getWriter();
		writer.println("<html>");
		String c = request.getParameter("input"); 
		int command = Integer.parseInt(c);
		
		
		String temp= request.getParameter("object");
		System.out.println(command);
		System.out.println(temp);
		System.out.println(temp);
		System.out.println(temp);
		Return client = new Return();         
		Charity current = client.getName(temp); 
		
		
	    if (command == 1) {
	    	String[] result2 = new String[2];
	    	result2[0] = "Description: "+current.getDesc();
	    	result2[1] = "Business number: "+current.getBnum();
			request.setAttribute("styles", result2); 
			RequestDispatcher view = request.getRequestDispatcher("OutputAllString.jsp"); 
			view.forward(request, response);   
	    }
	    else if (command == 2) {
	    	int count = 0; 
	    	for (int i=0; i<current.getProg().length/2; i++){
				if (!current.getProg(i).equals("")) {
					System.out.println(current.getProg(i)+": "+current.getProg(i+3));
					count = i; 
				}
	    	}		
	    	count = count + 1;
	    	String[] result2 = new String[3+count];
	    	result2[0] = "Home Country: "+current.getDesc();
	    	result2[1] = "Headquarters: "+current.getBnum();
	    	result2[2] = "Operating Country: "+current.getOland();
	    	
	    	count = 3; 
	    	for (int i=0; i<current.getProg().length/2; i++){
				if (!current.getProg(i).equals("")) {
					result2[count] = (current.getProg(i)+": "+current.getProg(i+3));
					count++;
				}
	    	}
	    	
			request.setAttribute("styles", result2); 
			RequestDispatcher view = request.getRequestDispatcher("OutputAllString.jsp"); 
			view.forward(request, response);   
	    }
	    else if(command == 3) {
	    	String[] result2 = new String[8];
	    	result2[0] = "Total revenue: $"+current.getStats(0);
	    	result2[1] = "Total expenditure: $"+current.getStats(1);
	    	result2[2] = "Total assets: $"+current.getStats(2);
	    	result2[3] = "Liabilities: $"+current.getStats(3);
	    	result2[4] = "Assets not used towards programs: $"+current.getStats(4);
	    	result2[5] = "Cash and short term investment: "+current.getStats(5);
	    	result2[6] = "Long term investment: "+current.getStats(6);
	    	result2[7] = "Is the charity privately owned: "+current.getServ(4);
	    													
			request.setAttribute("styles", result2); 
			RequestDispatcher view = request.getRequestDispatcher("OutputAllString.jsp"); 
			view.forward(request, response);   
	    	
	    }
	    else if(command == 4) {
	    	String[] result2 = new String[5];
	    	result2[0] = "Charity category: "+current.getServ(0);
	    	result2[1] = "Number of full time positions: "+current.getServ(1);
	    	result2[2] = "Number of part time positions: "+current.getServ(2);
	    	result2[3] = "Expenditure on positions: $"+current.getServ(3);
	    	result2[4] = "Ran political campaign: "+current.getServ(5);
	    	
			request.setAttribute("styles", result2); 
			RequestDispatcher view = request.getRequestDispatcher("OutputAllString.jsp"); 
			view.forward(request, response);   
			
	    }else {
	    	response.sendRedirect("Home.html");    
	     }
	} 
}	
	