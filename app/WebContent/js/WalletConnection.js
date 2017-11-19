/* 

I built this login form to block the front end of most of my freelance wordpress projects during the development stage. 

This is just the HTML / CSS of it but it uses wordpress's login system. 

Nice and Simple*/

var mobile = "";
var email_id = "";


$(document).ready(function() {

		var parameters = location.search.substring(1).split("&");

		var temp = parameters[0].split("=");
		email_id = unescape(temp[1]);

	$("#WalletloginForm").submit(function(e) {
		mobile = $("#MobileNumber").val();
		var url = "http://localhost:8080/app/connectwallet";
		$.ajax({
			type : "POST",
			url : url,
			data : {
				email : email_id,
				mobile : mobile
			},
			success : function(res) {				
				alert(res);

				var datatemp = JSON.parse(res);
				var email = datatemp.email_id;
				var mobile = datatemp.mobile_number;
				var state = datatemp.state;
				var url = datatemp.url;
				url = url + "?email=" + email + "&mobile="+ mobile+"&state="+state;
				
				if(typeof error === 'undefined'){
					window.location.href = url;					
				} else{
					$("#error").show().delay(5000).fadeOut();
					$("#error").text("");					
				}				
			},
			error : function(jqXHR, textStatus, errorThrown) {
				var respJSON = ajaxResponseHandlers["defhandler"]
						.parse(jqXHR.responseText);
				if (_.has(respJSON, "errors")) {
					ajaxResponseHandlers["formhandler"].error(jqXHR,
							textStatus, errorThrown);
				}
			}
		});

		e.preventDefault(); // avoid to execute the actual submit of the
							// form.
	});

});

