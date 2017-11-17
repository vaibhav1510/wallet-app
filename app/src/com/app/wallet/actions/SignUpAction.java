package com.app.wallet.actions;

import java.io.IOException;
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
			resp.put("email", email);
			if(validator.isValid()) {
				resp.put("message", "Login successfully");		
			} else {
				validator.createNewUser();
				resp.put("message", "Signed Up successfully");				
			}
			response.sendRedirect(resp.toString());
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
