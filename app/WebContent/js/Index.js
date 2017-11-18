/* 

I built this login form to block the front end of most of my freelance wordpress projects during the development stage. 

This is just the HTML / CSS of it but it uses wordpress's login system. 

Nice and Simple*/

var gEmailId;
var gToken;

function ReadInputData()
{
	//alert("ReadInputData");
	var parameters = location.search.substring(1).split("&");

	var temp = parameters[0].split("=");
	gEmailId = unescape(temp[1]);
	
	temp = parameters[1].split("=");
	gToken = unescape(temp[1]);

}

function TravelLoad()
{
	alert("TravelLoad");
}



function UpdateWalletBalance(ResList, PhoneNum) 
{
	var tblWallet = document.getElementById("tableOutput");
 
	var rowCount = tblWallet.rows.length;
	for (var i = rowCount - 1; i > 0; i--) {
		tblWallet.deleteRow(i);
	}
	
	//Add the data rows.
	for (var i = 0; i < ResList.length; i++) 
	{			 
		  var datatemp = JSON.parse(ResList);

		  var f1 = datatemp.data;
		  var phone = f1.phone;
		  var travel = f1.travel;
		  var balance = f1.balance;
		  console.log('Response:' + JSON.stringify(phone) + '  ' + JSON.stringify(travel) + '  ' + JSON.stringify(balance));
		  
		  var tblWallet = document.getElementById("tableInput");
		  
		  row = tblWallet.insertRow(-1);
		  
		  var cellName = row.insertCell(-1);	
		  cellName.innerHTML = f1.phone;
		  
		  var cellValue = row.insertCell(-1);	
		  cellValue.innerHTML = f1.travel;	
		  
		  var cellValue = row.insertCell(-1);	
		  cellValue.innerHTML = f1.balance;
		
	}
}


function UpdateTravelBalance(ResList, PhoneNum) 
{
	var tblTravel = document.getElementById("tableInput");
 
	var rowCount = tblTravel.rows.length;
	for (var i = rowCount - 1; i > 0; i--) {
		tblTravel.deleteRow(i);
	}
	
	//Add the data rows.
	for (var i = 0; i < ResList.length; i++) 
	{			 
		  var datatemp = JSON.parse(ResList);

		  var f1 = datatemp.data;
		  var phone = f1.phone;
		  var travel = f1.travel;
		  var balance = f1.balance;
		  console.log('Response:' + JSON.stringify(phone) + '  ' + JSON.stringify(travel) + '  ' + JSON.stringify(balance));
		  
		  var tblTravel = document.getElementById("tableInput");
		  
		  row = tblTravel.insertRow(-1);
		  
		  var cellName = row.insertCell(-1);	
		  cellName.innerHTML = f1.phone;
		  
		  var cellValue = row.insertCell(-1);	
		  cellValue.innerHTML = f1.travel;	
		  
		  var cellValue = row.insertCell(-1);	
		  cellValue.innerHTML = f1.balance;
		
	}
}

function UpdateBalance(res) 
{
	var balance = JSON.parse(res);
	
	var phone = balance.phone;
	
	var walletList = balance.balanceListWallet;
	var travelList = balance.balanceListTravel;
	
	UpdateWalletBalance(walletList, phone);
	UpdateTravelBalance(travelList, phone);
}

	$("#cuatro").click(function(e) {
		
	    var url= "http://localhost:8080/app/getbalance";
	    var mobile = "7338867999";
	    
	    $.ajax({
	           type: "POST",
	           url: url,
	           data: {email_id: gEmailId, mobile_num:mobile, token:gToken},
	           success: function(res)
	           {
	        	  console.log("SUCCESS");
	              alert(res); // show response from the php script.	
	                          
	              UpdateBalance(res);

	           },
	           error: function (jqXHR, textStatus, errorThrown) {
	               var respJSON = ajaxResponseHandlers["defhandler"].parse(jqXHR.responseText);
	               if (_.has(respJSON, "errors")) {
	                   ajaxResponseHandlers["formhandler"].error(jqXHR, textStatus, errorThrown);
	               }
	           }
	         });

	    e.preventDefault(); // avoid to execute the actual submit of the form.
	});



$('#t4 > li').click(function() {

	alert("t4");
	    var url= "http://localhost:8080/app/getbalance";
	    var mobile = "7338867999";
	    
	    $.ajax({
	           type: "POST",
	           url: url,
	           data: {email_id: gEmailId, mobile_num:mobile, token:gToken},
	           success: function(res)
	           {
	        	  console.log("SUCCESS");
	              alert(res); // show response from the php script.	
	                          
	              UpdateBalance(res);

	           },
	           error: function (jqXHR, textStatus, errorThrown) {
	               var respJSON = ajaxResponseHandlers["defhandler"].parse(jqXHR.responseText);
	               if (_.has(respJSON, "errors")) {
	                   ajaxResponseHandlers["formhandler"].error(jqXHR, textStatus, errorThrown);
	               }
	           }
	         });

	    e.preventDefault(); // avoid to execute the actual submit of the form.


});



   