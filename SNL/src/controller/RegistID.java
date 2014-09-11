package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DBFacade;

/**
 * made by MinjuneL
 * ver 0.1
 * 2014. 06. 10
 * 
 * 데이터베이스에 아이디와 패스워드를 저장한다.
 * 
 * 
 * 현재 저장하는 데이터는 id와 pw 뿐이며 추후에 추가되어야 한다.
 */
/**
 * Servlet implementation class RegistID
 */
@WebServlet("/RegistID")
public class RegistID extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistID() {
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
		
		try {//한글 처리.
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String mId = request.getParameter("id");
		String mPw = request.getParameter("pw");
		String mName = request.getParameter("name");
		
		System.out.println("[MinjuneL] (RegistID) id = "+mId+", pw = "+mPw+", name = "+mName);
		
		DBFacade db = new DBFacade();
		
		if(db.registerID(mId, mPw, mName)){
			PrintWriter out = response.getWriter();
			out.print("true");
		}else{
			PrintWriter out = response.getWriter();
			out.print("false");
		}
	}

}
