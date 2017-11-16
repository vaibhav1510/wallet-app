/* 

I built this login form to block the front end of most of my freelance wordpress projects during the development stage. 

This is just the HTML / CSS of it but it uses wordpress's login system. 

Nice and Simple


 <script src="http://crypto-js.googlecode.com/svn/tags/3.1.2/build/rollups/aes.js"></script>
 
function genOrderSummary( encryptedUserName, encryptedPassword) {

    $(this).ajaxhandler({
        url: "/pages/" + _cb_hp_token + "/checkout",
        type: "GET",
        data: {email_id: encryptedUserName, password: encryptedPassword},
        success: function(responseJson, statusText, xhr, $form) {
        	
        	  //var arr =JSON.parse(response.xhr.responseText);
  		  
  			  //var datatemp = JSON.parse(arr.data);

  			  //var emailId = datatemp.email;
  			  //var client_token = datatemp.client_token;
  			  //var url = datatemp.url;
  			  //url = url + "?email=" + emailId + "&client_token=" + client_token;
  			  
  			  url = "http://localhost:8080/app/index.html" ;//+ "?email=" + emailId + "&client_token=" + client_token;
  			  
  			  window.open(url);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            var respJSON = ajaxResponseHandlers["defhandler"].parse(jqXHR.responseText);
            if (_.has(respJSON, "errors")) {
                ajaxResponseHandlers["formhandler"].error(jqXHR, textStatus, errorThrown);
            }
        }
    });
}

require(['index'], function(Syssyn) {
  	 $('#Submit-Login').click(function() {

  		var email = $("#Email-Id").val();
  		var password = $("#Login-Password").val();
  		
  		var encryptedEmail = CryptoJS.AES.encrypt(email, "My Secret Passphrase");
  		var encryptedPassword = CryptoJS.AES.encrypt(password, "My Secret Passphrase");
  		
  		genOrderSummary(encryptedUserName, encryptedPassword);
  		
  		
  		///// Decryption code //////
  		
  	   // var decryptedBytes = CryptoJS.AES.decrypt(encryptedAES, "My Secret Passphrase");
  	   // var plaintext = decryptedBytes.toString(CryptoJS.enc.Utf8);
  	   
  	   
  	   
       /* require(['index'], function(SysSynSimAppApi) {
  		
  		 var Initprojectinfo = new SysSynSimAppApi.Projectdetailinfo152(); // Construct a model instance.
  		 Initprojectinfo.simapp_id = gSimappId;
  		 
  		var PreProcessorWorkerSvc = new SysSynSimAppApi.PreProcessorWorkerApi(); // Allocate the API class we're going to use.  
  		var yyy = PreProcessorWorkerSvc.getProjectDetails152(Initprojectinfo, callbackStudies); // Invoke the PreProcessorWorker service 
  	}); */
  	
  //	});
      

   // });  */