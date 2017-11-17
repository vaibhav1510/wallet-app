/* 

I built this login form to block the front end of most of my freelance wordpress projects during the development stage. 

This is just the HTML / CSS of it but it uses wordpress's login system. 

Nice and Simple*/

function UpdateInputExposures(ResList, PhoneNum) 
{
	var tblInputExposure = document.getElementById("tableInput");
 
	var rowCount = tblInputExposure.rows.length;
	for (var i = rowCount - 1; i > 0; i--) {
		tblInputExposure.deleteRow(i);
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
		  
		  var tblInputExposure = document.getElementById("tableInput");
		  
		  row = tblInputExposure.insertRow(-1);
		  
		  var cellName = row.insertCell(-1);	
		  cellName.innerHTML = f1.phone;
		  
		  var cellValue = row.insertCell(-1);	
		  cellValue.innerHTML = f1.travel;	
		  
		  var cellValue = row.insertCell(-1);	
		  cellValue.innerHTML = f1.balance;
		
	}
}


   