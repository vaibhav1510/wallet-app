/* 

I built this login form to block the front end of most of my freelance wordpress projects during the development stage. 

This is just the HTML / CSS of it but it uses wordpress's login system. 

Nice and Simple*/

var emailId = "";
var mobile = "";
var otp = "";

function ShowHideInput() {
	var parameters = location.search.substring(1).split("&");

	var temp = parameters[0].split("=");
	gEmailId = unescape(temp[1]);

	$("#otptext").value = "Enter the <strong>One Time Password (OTP) received on </strong>"
			+ mobile + " to login to Paytm Wallet";

}

function ValidateOTP(email, password) {

	$("#WalletotpForm").submit(
			function(e) {

				var url = "http://localhost:8080/app/verifyotp";

				$.ajax({
					type : "POST",
					url : url,
					data : {
						mobile : email,
						email : password,
						state : state,
						otp : otp
					},
					success : function(res) {
						console.log("SUCCESS");
						alert(res); // show response from the php script.

						var datatemp = JSON.parse(res);
						var emailId = datatemp.email_id;
						var client_token = datatemp.client_token;
						var url = datatemp.url;
						var error = datatemp.error;
						
						url = url + "?email=" + emailId + "&client_token="
								+ client_token;

						if(error === 'undefined'){
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

}

$('#LoginOTP').click(function() {

	otp = $("OLTP").val();
	ValidateOTP();
	// db set mobile

});