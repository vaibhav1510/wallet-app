/* 

I built this login form to block the front end of most of my freelance wordpress projects during the development stage. 

This is just the HTML / CSS of it but it uses wordpress's login system. 

Nice and Simple*/



 // <script type="text/javascript"></script>
function genOrderSummary( email, password) {
	
		$("#loginForm").submit(function(e) {
			
		    var url= "http://localhost:8080/app/signup";


		    $.ajax({
		           type: "POST",
		           url: url,
		           data: {email_id: email, password: password},
		           success: function(res)
		           {
		        	  console.log("SUCCESS");
		              //alert(res); // show response from the php script.	
		              
		              //var datatemp = JSON.parse(res);
              
        			  //var emailId = datatemp.email;
        			  //var client_token = datatemp.client_token;
            		  //var url = datatemp.url;
            		  //url = url + "?email=" + emailId + "&client_token=" + client_token;
		              
		              emailId = "test@test.com";
		              client_token = "1234567";
		              		              
		  			  url2 = "http://localhost:8080/app/index.html" + "?email=" + emailId + "&client_token=" + client_token;		  			  
		  			  window.location.href = url2;

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
//    $(this).ajaxhandler({
//        url: "http://localhost:8080/app/signup",
//        type: "POST",
//        data: {email_id: email, Password: password},
//        
//        success: function(responseJson, statusText, xhr, $form) {
//        	Console.log("SUCCESS");
//        	  //var arr =JSON.parse(response.xhr.responseText);
//  		  
//  			  //var datatemp = JSON.parse(arr.data);
//
//  			  //var emailId = datatemp.email;
//  			  //var client_token = datatemp.client_token;
//  			  //var url = datatemp.url;
//  			  //url = url + "?email=" + emailId + "&client_token=" + client_token;
//  			  
////  			  url = "http://localhost:8080/app/index.html" ;//+ "?email=" + emailId + "&client_token=" + client_token;
////  			  
////  			  window.open(url);
//        },
//        error: function (jqXHR, textStatus, errorThrown) {
//        	Console.log("ERRORR");
//            var respJSON = ajaxResponseHandlers["defhandler"].parse(jqXHR.responseText);
//            if (_.has(respJSON, "errors")) {
//                ajaxResponseHandlers["formhandler"].error(jqXHR, textStatus, errorThrown);
//            }
//        }
//    });
}


  	 $('#Submit-Login').click(function() {

  		var email = $("#Email-Id").val();
  		var password = $("#Login-Password").val();
  		
  		//var encryptedEmail = CryptoJS.AES.encrypt(email, "My Secret Passphrase");
  		//var encryptedPassword = CryptoJS.AES.encrypt(password, "My Secret Passphrase");
  		
  		genOrderSummary(email, password);
  		
  		
  		///// Decryption code //////
  		
  	   // var decryptedBytes = CryptoJS.AES.decrypt(encryptedAES, "My Secret Passphrase");
  	   // var plaintext = decryptedBytes.toString(CryptoJS.enc.Utf8);
  	   
  	   
  	   
       /* require(['index'], function(SysSynSimAppApi) {
  		
  		 var Initprojectinfo = new SysSynSimAppApi.Projectdetailinfo152(); // Construct a model instance.
  		 Initprojectinfo.simapp_id = gSimappId;
  		 
  		var PreProcessorWorkerSvc = new SysSynSimAppApi.PreProcessorWorkerApi(); // Allocate the API class we're going to use.  
  		var yyy = PreProcessorWorkerSvc.getProjectDetails152(Initprojectinfo, callbackStudies); // Invoke the PreProcessorWorker service 
  	}); */
  	
  	});
      

   