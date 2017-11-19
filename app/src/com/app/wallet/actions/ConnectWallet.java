package com.app.wallet.actions;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.app.wallet.helpers.ConnectWallethelper;

/**
 * Servlet implementation class ConnectWallet
 */
@WebServlet(name = "/ConnectWallet", urlPatterns = "/connectwallet")
public class ConnectWallet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ConnectWallet() {
		super();
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String mobileNumber = request.getParameter("mobile");
			String state = ConnectWallethelper.generateState(mobileNumber, "PayTm");

			JSONObject resp = new JSONObject();
			resp.put("email_id", request.getParameter("email"));
			resp.put("mobileNumber", mobileNumber);
			resp.put("state", state);
			
			System.out.println(resp.toString(2));
			response.getWriter().write(resp.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		service(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		service(request, response);
	}

}
