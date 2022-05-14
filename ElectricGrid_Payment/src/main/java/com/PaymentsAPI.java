package com;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Payment;

import java.util.HashMap; 
import java.util.Map; 
import java.util.Scanner;


/**
 * Servlet implementation class PaymentsAPI
 */
@WebServlet("/PaymentsAPI")
public class PaymentsAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public PaymentsAPI() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Payment paymentObj = new Payment(); 

		String output = paymentObj.insertPayment(request.getParameter("p_code"), 
								request.getParameter("p_status"), 
								request.getParameter("p_date"), 
								request.getParameter("p_crdnumber"),
								request.getParameter("p_cvv"),
								request.getParameter("p_amount")); 
		
		response.getWriter().write(output); 
				
	}
	
	
	// Convert request parameters to a Map
	private static Map getParasMap(HttpServletRequest request) { 
		
		Map<String, String> map = new HashMap<String, String>(); 
		try
		{ 
			 Scanner scanner = new Scanner(request.getInputStream(), "UTF-8"); 
			 String queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : ""; 
			 scanner.close(); 
			 
			 String[] params = queryString.split("&"); 
			 for (String param : params) 
			 { 
				 String[] p = param.split("="); 
				 map.put(p[0], p[1]); 
			 } 
		} 
		catch (Exception e) 
		{ 
		} 
		
		return map; 
	}



	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Payment paymentObj = new Payment(); 
		
		Map paras = getParasMap(request); 
		
		String output = paymentObj.updatePayment(paras.get("hidPaymentIDSave").toString(), 
											paras.get("p_code").toString(), 
											paras.get("p_status").toString(), 
											paras.get("p_date").toString(), 
											paras.get("p_crdnumber").toString(),
											paras.get("p_cvv").toString(),
											paras.get("p_amount").toString()); 
		
		response.getWriter().write(output); 
		
	}


	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Payment paymentObj = new Payment(); 
		
		Map paras = getParasMap(request); 
		
		String output = paymentObj.deletePayment(paras.get("p_ID").toString()); 
		
		
		response.getWriter().write(output); 
		
	}

}
