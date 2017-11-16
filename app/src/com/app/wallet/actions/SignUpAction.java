package com.app.wallet.actions;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.wallet.dbconnection.DbExecutor;
import com.app.wallet.http.JsonElem;

@WebServlet(name="/SignUpAction", urlPatterns="/signup")
public class SignUpAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SignUpAction() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		String sql = "select * from users";
		DbExecutor exec = DbExecutor.init();
		String[] cols = {"id", "email", "password"};
		JsonElem ele = exec.select(sql, cols);		
		
		ServletOutputStream out = response.getOutputStream();
		out.print("Test is correct");
		out.print(ele.toString(2));
		
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
