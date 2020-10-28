package spring.util;

import java.io.File;

public class FileUploadUtil {
	
	public static String checkSameFileName(String filename, String path) {
		//인자인 filename에서 확장자를 뺀 파일명 가려내자!
		//그렇게 하려면 먼저 파일명에 있는 "."의 위치를 알아내야 한다.
		int period = filename.lastIndexOf(".");
		
		//파일명과 확장자를 분리한다.
		String f_name = filename.substring(0, period);//파일명
		String suffix = filename.substring(period);//확장자
		
		//전체경로
		//D:\myStudy\JSP_Study\work\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\MyProject\resources\ upload
		//받은 path의 경로에는 위와 같이 파일명이 빠진 상태다.
		String saveFile = path + System.getProperty("file.separator") + filename;
		
		//위의 경로(path + filename)를 가지고 존재여부를 확인하자! 이때 필요한 것이 java.io.File
		File f = new File(saveFile);
		
		//존재할 경우를 생각해서 이름을 변경시킬 때 사용하는 숫자를 하나 만들자
		int idx = 1;
		while(f.exists()) {//같은 이름으로 파일이 존재하는 경우!! 반복문을 돌린다. false가 나올때까지 반복
			//파일명 뒤에 숫자를 붙여 파일명을 변경한다.
			StringBuffer sb = new StringBuffer();
			sb.append(f_name);
			sb.append("(");
			sb.append(idx++);//숫자를 붙인 후 1증가
			sb.append(")");
			sb.append(suffix);
			
			filename=sb.toString();//변경한 파일 이름
			
			saveFile = path + System.getProperty("file.separator") + filename;
			
			f = new File(saveFile);
		}//while의 끝! f가 존재하지않을때까지 계속 돈다.
		
		return filename;//중복되지 않는 파일명 반환! DefaultFileRenamePolicy()가 하는 역할!!
	}
}
