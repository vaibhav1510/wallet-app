curl https://accounts-uat.paytm.com/signin/otp -X POST -H "Content-Type: application/json" -d '{
   "phone":"7777777777",
   "clientId":"paytm-pg-client-staging",
   "loginData":"ORDS_902042:Charge13239993676478:70000402494:WEB:8BCA0D94D3DAB64EBDB13ABFDFA14513.Charge13239993676478ORDS_902042.webjvm1:MANUAL:908820557:webjvm1",
   "scope":"wallet",
   "responseType":"token",
   "redirectUri":"https://www.chargebee.com",
   "doNotRedirect":"false",
   "createAccount":true,
   "theme":"pg-otp"
}'

 # curl https://accounts-uat.paytm.com/login/otp -X POST -H "Content-Type: application/json" -d '{
#    "data":"7777777777",
#    "createAccount":"true"
#    "clientId":"paytm-pg-client-staging",
#    "scope":"paytm",
#    "doNotRedirect":"true"
#    "responseType":"code",
# "loginData":"ORDS_902042:Charge13239993676478:70000402494:WEB:8BCA0D94D3DAB64EBDB13ABFDFA14513.Charge13239993676478ORDS_902042.webjvm1:MANUAL:908820557:webjvm1"
#
# }'
   # "redirectUri":"https://pguat.paytm.com/oltp-web/oauthResponse",


# curl https://accounts-uat.paytm.com/signin/otp -X POST -H "Content-Type: application/json" -d '{"email":"example@example.com","phone":"7777777777","clientId":"paytm-pg-client-staging","scope":"wallet","responseType":"token"}'
      # "redirectUri":"null"
	  
	  
# "createAccount":true,"theme":"pg-otp"}
