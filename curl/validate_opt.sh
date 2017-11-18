curl https://accounts-uat.paytm.com/login/validate/otp -X POST -H "Content-Type: application/json" -d '{
   "otp":"489871",
   "state":"11381148-5de4-482f-9bec-365c898a8084",
   "scope":"wallet",
   "responseType":"token"
}'