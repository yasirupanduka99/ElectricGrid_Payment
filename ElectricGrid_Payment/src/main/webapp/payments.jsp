<%@ page import="model.Payment" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    
       
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Electric Grid</title>
	<link rel="stylesheet" href="css/bootstrap.css">
	<script src="js/jquery-3.6.0.min.js" type="text/javascript"></script>
	<script src="js/validation.js" type="text/javascript"></script>
	<script src="js/payments.js" type="text/javascript"></script>
</head>
<body>


<div class="container">
	 <div class="row">
		 <div class="col">
		 	
		 	
		 	<h1>ELECTRIC GRID SYSTEM</h1>
		 	<br><br>
		 	
		 	<h2>Payment Management</h2>
		 	<br>
			<form id="formPayment" name="formPayment">
			
				 Payment Code: 
				<input id="p_code" name="p_code" type="text" class="form-control form-control-sm">
				<br><!--Payment Status: 
				 <input id="p_status" name="p_status" type="text" class="form-control form-control-sm">
				-->
				
				<!-- Payment Status -->
				<div class="input-group input-group-sm mb-3">
					<div class="input-group-prepend">
						 <span class="input-group-text" id="lblName">Payment Status: </span>
					</div>
					<select id="p_status" name="p_status">
						 <option value="0">Select Payment Status</option>
						 <option value="Completed">Completed</option>
						 <option value="Incomplete">Incomplete</option>
					</select>
				</div>

				Payment Date: 
				<input id="p_date" name="p_date" type="text" class="form-control form-control-sm">
				<br> Payment Card No.: 
				<input id="p_crdnumber" name="p_crdnumber" type="text" class="form-control form-control-sm">
				<br> Payment CVV: 
				<input id="p_cvv" name="p_cvv" type="text" class="form-control form-control-sm">
				<br> Payment Amount: 
				<input id="p_amount" name="p_amount" type="text" class="form-control form-control-sm">
				<br>
				
				<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
				
				<input type="hidden" id="hidPaymentIDSave" name="hidPaymentIDSave" value="">
			</form>
			
			<br>
			
			<%--Insert Status Message print --%>
			<div id="alertSuccess" class="alert alert-success"></div>
			<div id="alertError" class="alert alert-danger"></div>
		 	
			
			<br><br>
			
			<%-- Data fetch to this Table --%>
			<div id="divPaymentsGrid">
				<%
					 Payment paymentObj = new Payment(); 
					 out.print(paymentObj.readPayment()); 
				%>
			</div>
			
		 	
		 </div>
	 </div>
</div>


</body>
</html>