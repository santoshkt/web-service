package aol;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AccountServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public RegisterServlet() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("register.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String result = RegisterService.processRegistration(request);
		if (result == null) {
			request.setAttribute("errorMsg", "Error occured. Please try again.");
		} else if (result.equals("success")) {
			request.setAttribute("successMsg",
					RegisterService.getSuccessMessage());
		} else {
			request.setAttribute("errorMsg", result);
		}

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("register.jsp");
		dispatcher.forward(request, response);
	}

}
