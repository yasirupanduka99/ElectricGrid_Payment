
//Let’s hide the alerts on page load. 
$(document).ready(function() {  
	$("#alertSuccess").hide(); 
 	$("#alertError").hide(); 
});


// CLIENT-MODEL================================================================

//Form validation function
function validatePaymentForm() 
{ 
	// CODE
	if ($("#p_code").val().trim() == "") 
	{ 
	 	return "Insert Payment Code!"; 
	} 
	// STATUS
	if ($("#p_status").val().trim() == "") 
	{ 
	 	return "Insert Payment Status!"; 
	} 
	// PRICE-------------------------------
	if ($("#p_date").val().trim() == "") 
	{ 
	 	return "Insert Payment Date!"; 
	} 
	// Payment Card No & CVV are numerical value
	var tmpcrdNo = $("#p_crdnumber").val().trim(); 
	if (!$.isNumeric(tmpcrdNo)) 
	{ 
	 	return "Insert a numerical value for Payment Card No!"; 
	}
	
	var tmpCVV = $("#p_cvv").val().trim(); 
	if (!$.isNumeric(tmpCVV)) 
	{ 
	 	return "Insert a numerical value for Payment Card CVV!"; 
	}
	 
	// Amount------------------------
	if ($("#p_amount").val().trim() == "") 
	{ 
	 	return "Insert Payment Amount!"; 
	} 
	
	var tmpAmount = $("#p_amount").val().trim(); 
	if (!$.isNumeric(tmpAmount)) 
	{ 
	 	return "Insert a numerical value for Payment Amount!"; 
	} 
	 
	return true; 
}


//
function onPaymentSaveComplete(response, status) 
{ 
	 if (status == "success") 
	 { 
		 var resultSet = JSON.parse(response); 
		 
		 if (resultSet.status.trim() == "success") 
		 { 
			 $("#alertSuccess").text("Successfully saved."); 
			 $("#alertSuccess").show(); 
			 
			 $("#divPaymentsGrid").html(resultSet.data); 
			 
		 } else if (resultSet.status.trim() == "error") 
		 { 
			 $("#alertError").text(resultSet.data); 
			 $("#alertError").show(); 
		 } 
		 
	 } else if (status == "error") 
	 { 
		 $("#alertError").text("Error while saving."); 
		 $("#alertError").show(); 
	 } else
	 { 
		 $("#alertError").text("Unknown error while saving.."); 
		 $("#alertError").show(); 
	 }
	 
	 $("#hidPaymentIDSave").val(""); 
	 $("#formPayment")[0].reset(); 
}





function onPaymentDeleteComplete(response, status) 
{ 
	 if (status == "success") 
	 { 
		 var resultSet = JSON.parse(response); 
		 
		 if (resultSet.status.trim() == "success") 
		 { 
			 $("#alertSuccess").text("Successfully deleted."); 
			 $("#alertSuccess").show(); 
			 $("#divPaymentsGrid").html(resultSet.data); 
		 } else if (resultSet.status.trim() == "error") 
		 { 
			 $("#alertError").text(resultSet.data); 
			 $("#alertError").show(); 
		 }
		  
	 } else if (status == "error") 
	 { 
		 $("#alertError").text("Error while deleting."); 
		 $("#alertError").show(); 
	 } else
	 { 
		 $("#alertError").text("Unknown error while deleting.."); 
		 $("#alertError").show(); 
	 } 
}





//Implement Save button click handler
$(document).on("click", "#btnSave", function(event) { 
 	// Clear alerts---------------------
	 $("#alertSuccess").text(""); 
	 $("#alertSuccess").hide(); 
	 $("#alertError").text(""); 
	 $("#alertError").hide();
	 
	 // Form validation-------------------
	 var status = validatePaymentForm(); 
	
	 // If not valid-------------------
	 if (status != true) 
	 { 
		 $("#alertError").text(status); 
		 $("#alertError").show(); 
		 
		 return; 
	 }
	 
	 // If valid------------------------
 	 var type = ($("#hidPaymentIDSave").val() == "") ? "POST" : "PUT"; 
	 $.ajax( 
	 { 
		 url : "PaymentsAPI", 
		 type : type, 
		 data : $("#formPayment").serialize(), 
		 dataType : "text", 
		 complete : function(response, status) 
		 { 
		 	onPaymentSaveComplete(response.responseText, status); 
		 } 
	 }); 	 

});



// Implement the Update button handler
$(document).on("click", ".btnUpdate", function(event) {
	  $("#hidPaymentIDSave").val($(this).data("paymentid"));   
	  $("#p_code").val($(this).closest("tr").find('td:eq(0)').text()); 
	  $("#p_status").val($(this).closest("tr").find('td:eq(1)').text()); 
	  $("#p_date").val($(this).closest("tr").find('td:eq(2)').text()); 
	  $("#p_crdnumber").val($(this).closest("tr").find('td:eq(3)').text());
	  $("#p_cvv").val($(this).closest("tr").find('td:eq(4)').text());
	  $("#p_amount").val($(this).closest("tr").find('td:eq(5)').text());
});



//For the Delete operation, we can get the item ID from the data attribute of the Remove button. 
$(document).on("click", ".btnRemove", function(event) 
{ 
	 $.ajax( 
	 { 
		 url : "PaymentsAPI", 
		 type : "DELETE", 
		 data : "p_ID=" + $(this).data("paymentid"),
		 dataType : "text", 
		 complete : function(response, status) 
		 { 
		 	onPaymentDeleteComplete(response.responseText, status); 
		 } 
	 }); 
});

