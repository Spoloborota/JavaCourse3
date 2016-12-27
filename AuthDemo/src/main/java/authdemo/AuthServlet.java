package authdemo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import authdemo.model.DAO;
import authdemo.model.impl.DAOimlStub;
import authdemo.model.impl.DAOimplHibernate;

public class AuthServlet extends HttpServlet {

	DAO dao = new DAOimlStub();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("paramName");
		String password = request.getParameter("paramPassword");
		if (login(name, password)) {
			response.getWriter().println("WELCOME!");
		} else {
			response.getWriter().println("Password is incorrect!");
		}
	}

	private boolean login(String name, String pass) {
		String passFrombase = dao.getPassword(name);	
		if(pass.equals(passFrombase)) {
			return true;
		} else {
			return false;
		}

	}
}
