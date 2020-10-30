package spring.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FileDownload
@WebServlet("/FileDownload") ---> web.xml에 servlet으로 등록해줘야 한다.
 */
public class FileDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileDownload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 파라미터 값들 받기( dir, filename )
		String dir = request.getParameter("dir");
		String filename = request.getParameter("filename");
		
		//dir을 절대경로로 만든다.
		//내장객체인 ServletContext application이 필요함!
		String path = getServletContext().getRealPath(dir);
		
		//앞서 얻어낸 upload의 절대경로와 파일명을 연결하면 전체경로가 된다.
		String fullPath = path + System.getProperty("file.separator") + filename;
		
		//전체 경로를 가지고 File객체를 생성한다.
		File f= new File(fullPath);
		
		//바구니 역할
		byte[] buf = new byte[2048]; //byte[] buf = new byte[(int형으로 써야한다!라운드로 올림해서 처리)f.length()];
		
		//전송할 데이터가 Stream처리될 때 문자셋 지정(파일 다운로드 할 때마다 헤더에 항상 지정..!)
		response.setContentType("application/octet-stream;charset=8859_1");
		
		//다운로드 대화상자 처리
		response.setHeader("Content-Disposition", 
							"attachment;filename=" + new String(filename.getBytes("utf-8"), "8859_1"));
		
		//전송타입이 binary data
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		if(f.isFile()) {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
			
			//요청한 곳으로 보내기 위해(응답), 스트림을 응답객체(response)로부터 얻어낸다.
			BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
			
			int size = -1;
			try {
				//읽어서 보내기
				while((size = bis.read(buf)) != -1) {//더 이상 파일로부터 읽을 것이 없을때까지 반복
					bos.write(buf, 0, size);//읽은 만큼 쓰기한다.
					bos.flush();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(bos != null)
					bos.close();
				if(bis != null)
					bis.close();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
