package com.app.wallet.actions;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.app.wallet.dbconnection.DbExecutor;
import com.app.wallet.helpers.UserValidator;
import com.app.wallet.http.JsonElem;

@WebServlet(name="/SignUpAction", urlPatterns="/signup")
public class SignUpAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SignUpAction() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String email = request.getParameter("email_id");
			String password = request.getParameter("password");
			UserValidator validator = new UserValidator(email, password);
			JSONObject resp = new JSONObject();
			resp.put("email_id", email);
			Boolean isValid = validator.isValid();
			if(isValid == null) {
				validator.createNewUser();								
				resp.put("client_token", validator.clientToken());
				resp.put("url", "http://localhost:8080/app/index.html");
				resp.put("message", "Signed Up successfully");				
			}else if(isValid) {				
				resp.put("client_token", validator.clientToken());
				resp.put("url", "http://localhost:8080/app/index.html");
				resp.put("message", "Login successfully");				
			} else {
				resp.put("error", "Email or Password is wrong. Please try again");
			}
			System.out.println(resp.toString(2));
			response.getWriter().write(resp.toString());
//			response.sendRedirect(resp.toString());
		}catch (Exception e) {			
			e.printStackTrace();
		}
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

}
