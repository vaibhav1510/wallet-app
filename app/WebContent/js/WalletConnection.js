/* 

I built this login form to block the front end of most of my freelance wordpress projects during the development stage. 

This is just the HTML / CSS of it but it uses wordpress's login system. 

Nice and Simple*/

var mobile = "";
var email = "";

function ShowHideInput()
{
	var parameters = location.search.substring(1).split("&");

	var temp = parameters[0].split("=");
	email = unescape(temp[1]);
}

function genOTP(email, password) {

	$("#WalletloginForm").submit(
	function(e) {

		var url = "http://localhost:8080/app/signup";

		$.ajax({
			type : "POST",
			url : url,
			data : {
				email_id : email,
				password : password
			},
			success : function(res) {
				console.log("SUCCESS");
				alert(res); // show response from the php script.

				var datatemp = JSON.parse(res);
				var emailId = datatemp.email_id;
				var client_token = datatemp.client_token;
				var url = datatemp.url;
				url = url + "?email=" + emailId + "&client_token="
						+ client_token;

				window.location.href = url;

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

}

$('#RequestOTP').click(function() {

	mobile = $("#MobileNumber").val();
	
	genOTP();
	
});

