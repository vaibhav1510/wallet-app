package com.app.wallet.actions;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.app.wallet.helpers.GetBalanceHelper;
import com.app.wallet.tp.paytm.PayTmCli;

/**
 * Servlet implementation class GetBalance
 */
@WebServlet(name = "/GetBalance", urlPatterns = "/getbalance")
public class GetBalance extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetBalance() throws Exception {
		super();
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String email = request.getParameter("email_id");
			GetBalanceHelper help = new GetBalanceHelper(email);

			JSONObject resp = help.getBalance();
			System.out.println(resp.toString(2));
			
			PayTmCli cli = new PayTmCli();
			String state = cli.requestOtp("7777777777");
			String accessCode = cli.validateOtp(state, "489871");
			String amount = cli.getBalance(accessCode);
			
			System.out.println(amount);
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
