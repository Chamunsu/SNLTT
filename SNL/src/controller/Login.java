package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;

import model.DBFacade;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		process(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		process(request, response);
	}
	
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		PrintWriter out = response.getWriter();
		try {//�ѱ� ó��.
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String mId = request.getParameter("login_id");
		String mPw = request.getParameter("login_password");
		String mAutoLogin = request.getParameter("login_auto");
		
		System.out.println("[MinjuneL] (Login) id = "+mId+", pw = "+mPw+", auto login = "+mAutoLogin);
		
		DBFacade db= new DBFacade();
		JSONObject result = db.login(mId, mPw);
		if(result != null){
			//�α��ο� �������� ��� �����Ѵ�.
			HttpSession session = request.getSession();
			session.setAttribute("userInfo", result);
			response.sendRedirect("main.jsp");
		}else{
			//�α��ο� �������� ��� �����Ѵ�.
			out.print("false");
			response.sendRedirect("/SNL/");
		}

	}

}
