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
 * Servlet implementation class VerifyOtp
 */
@WebServlet(name = "/VerifyOtp", urlPatterns = "/verifyotp")
public class VerifyOtp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VerifyOtp() {
		super();
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String mobileNumber = request.getParameter("mobile");
			String email = request.getParameter("email");
			String state = request.getParameter("state");
			String otp = request.getParameter("otp");
			
			ConnectWallethelper.getNUpdate(email, mobileNumber, "PayTm", state, otp);

			JSONObject resp = new JSONObject();
			resp.put("message", "Update Successfully");
			response.getWriter().write(resp.toString());
//			resp.put("url", "http://localhost:8080/app/getbalance");
			response.sendRedirect("/getbalance");
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
