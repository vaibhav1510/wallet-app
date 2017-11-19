/* 

I built this login form to block the front end of most of my freelance wordpress projects during the development stage. 

This is just the HTML / CSS of it but it uses wordpress's login system. 

Nice and Simple*/

var emailId = "";
var mobile = "";
var otp = "";
var state = "";

$(document).ready(function() {

	$("#otptext").value = "Enter the <strong>One Time Password (OTP) received on </strong>"
		+ mobile + " to login to Paytm Wallet";
	
	var parameters = location.search.substring(1).split("&");
	emailId = unescape(parameters[0].split("=")[1]);
	mobile=unescape(parameters[1].split("=")[1]);
	state=unescape(parameters[2].split("=")[1]);
	
	
	$("#WalletotpForm").submit(
			function(e) {
				otp =$("#OLTP").val();				
				var url = "http://localhost:8080/app/verifyotp";

				$.ajax({
					type : "POST",
					url : url,
					data : {
						email : emailId,
						mobile : mobile,
						state : state,
						otp : otp
					},
					success : function(res) {
						console.log("SUCCESS");
						alert(res); // show response from the php script.

						var datatemp = JSON.parse(res);
						var email = datatemp.email;
						var client_token = datatemp.client_token;
						var url = datatemp.url;
						
						url = url + "?email=" + email + "&client_token="+ client_token;

						if(typeof  error === 'undefined'){
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