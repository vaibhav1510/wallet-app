curl https://accounts-uat.paytm.com/login/validate/otp -X POST -H "Content-Type: application/json" -d '{
   "otp":"489871",
   "state":"24726a73-b05a-41f3-b5b3-782690b0b985",
   "scope":"paytm",
   "responseType":"token"
}'