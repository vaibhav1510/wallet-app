curl https://accounts-uat.paytm.com/signin/otp -X POST -H "Content-Type: application/json" -d '{
   "phone":"7777777777",
   "clientId":"paytm-pg-client-staging",
   "scope":"wallet",
   "responseType":"token",
   "redirectUri":"https://pguat.paytm.com/oltp-web/oauthResponse"
}'


# curl https://accounts-uat.paytm.com/signin/otp -X POST -H "Content-Type: application/json" -d '{"email":"example@example.com","phone":"7777777777","clientId":"paytm-pg-client-staging","scope":"wallet","responseType":"token"}'