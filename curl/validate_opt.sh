curl https://accounts-uat.paytm.com/signin/validate/otp -X POST -H "Content-Type: application/json" \
-u staging-hackathalon:51e6d096-56f6-40b4-a2b9-9e0f8fa704b8 -d '{
   "otp":"532523",
   "state":"9a7f2754-3ec6-49ea-bb70-bcbd5d28ca5e"
}'