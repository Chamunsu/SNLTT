package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import model.AppInfo;
import model.DBFacade;
import model.PdfToImage;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class FileUpload
 */
@WebServlet("/FileUpload")
public class FileUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUpload() {

        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}
	
	public void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {//�ѱ� ó��.
			request.setCharacterEncoding("EUC-KR");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//web���� �ٿ���� �� �ֵ��� �� ���� �ȿ� ���ε� ������ �������.
		String path = AppInfo.FILE_PATH;
		System.out.println("[MinjuneL] (FileUpload) file path = "+path);
		File dir = new File(path);
		if(!dir.exists()){
			dir.mkdirs();
		}
		//���� ������ �н� ó�� 
		
		String enctype = "EUC-KR";
		int maxFileSize = 50*1024*1024;
		
		String originalName = new String();
		String systemName = new String();
		String contentType = new String();
		File savedFile = null;
		String title = new String();
		String summary = new String();
		String userId = new String();
		
		try{
			MultipartRequest req = null;
			req = new MultipartRequest(request, path, maxFileSize, enctype, new DefaultFileRenamePolicy());
			originalName = req.getOriginalFileName("upload");
			systemName = req.getFilesystemName("upload");
			contentType = req.getContentType("upload");
			savedFile = req.getFile("upload");
			
			userId = req.getParameter("user_id");
			title = req.getParameter("inputTitle");
			summary = req.getParameter("inputSummary");
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		System.out.println("[MinjuneL] (FileUpload) original name = "+originalName);
		System.out.println("[MinjuneL] (FileUpload) system name = "+systemName);
		System.out.println("[MinjuneL] (FileUpload) content type = "+contentType);
		
		if(savedFile != null)
			System.out.println("file is existed");
		else
			System.out.println("file is not existed");
		
		//������ ������ ������ �����.
		path+=this.destFilePrefix(systemName)+"/";
		dir = new File(path);
		if(!dir.exists()){
			dir.mkdirs();
		}
		
		PdfToImage pdf = new PdfToImage();
		int endOfPages = pdf.extractPagesAsImage( savedFile, path, 100, "");
		
		//���� �ð��� ������.
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat CurTimeFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        String strCurTime = CurTimeFormat.format(date);
        
        DBFacade db = new DBFacade();
        
        db.insertData(userId, title, summary, systemName, destFilePrefix(systemName), endOfPages, strCurTime, now);
        
        
		HttpSession session = request.getSession();
		response.sendRedirect("main.jsp");
	}
	
	 public String destFilePrefix(String sourceFile) {
	        
         String result = "";
         int lastSeparatorIndex = sourceFile.lastIndexOf("/") + 1;
         int lastCommaIndex =  sourceFile.lastIndexOf(".");
        
         result = sourceFile.substring(0, lastSeparatorIndex);
         result = result + (sourceFile.substring(lastSeparatorIndex,lastCommaIndex));
        
         return result;
        
	 }
}
