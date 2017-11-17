package com.app.wallet.tp;

import com.app.wallet.models.UserToken;

public interface TpImpl {
	public String balance(UserToken tok) throws Exception;
	
	public String getAccess() throws Exception;
	
}