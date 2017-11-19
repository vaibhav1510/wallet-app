package com.app.wallet.helpers;

import com.app.wallet.models.App;
import com.app.wallet.models.User;
import com.app.wallet.models.UserWallet;
import com.app.wallet.tp.paytm.PayTmCli;

public class ConnectWallethelper {

	public static String generateState(String mobileNumber, String walletName) throws Exception{
		PayTmCli cli = new PayTmCli();
		return cli.requestOtp(mobileNumber);		
	}
	
	public static String getNUpdate(String email, String mobileNumber, String walletName,String state, String otp) throws Exception{
		PayTmCli cli = new PayTmCli();
		String accessCode = cli.validateOtp(state, otp);
		String amount = cli.getBalance(accessCode);
		dbInsert(email, mobileNumber, walletName, amount);
		return amount;
	}
	
	public static void dbInsert(String email, String mobileNumber, String walletName, String amount) throws Exception{
		User u = User.getUser(email);
		App app = App.getApp(walletName);
		UserWallet uw =UserWallet.getWallet(u, mobileNumber);
		if(uw==null) {
			uw.dbInsert();
		}else {
			uw.setBalance(Integer.parseInt(amount));
			uw.dbUpdate();
		}
	}
	
}
