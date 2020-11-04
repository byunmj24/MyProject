package com.project.ex;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import mybatis.vo.MemVO;

@Controller
public class KakaoController {	
	@Autowired
	private HttpSession session;
	
	@RequestMapping("/kakao_login")
	public ModelAndView kakaoLogin(String code) {
		//카카오서버에서 인증코드를 전달해 주는 곳!
		ModelAndView mv = new ModelAndView();
		
		//인증코드는 인자인 code로 받는다.
		//System.out.println("CODE:" + code);
		
		//받은 코드를 가지고 다시 토큰을 받기 위한 작업 - POST방식
		String access_Token = "";
		String refresh_Token = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";
		
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			//POST방식으로 요청하기 위해 setDoOutput을 true로 지정
			conn.setRequestMethod("POST");//요청방식 설정
			conn.setDoOutput(true);
			
			//인자 4개를 만들어서 카카오 서버로 보내야한다.
			//grant_type, client_id, redirect_uri, code
			StringBuffer sb = new StringBuffer();
			sb.append("grant_type=authorization_code&client_id=62760ebf9274fa8626834c26cc29f7b8");
			sb.append("&redirect_uri=http://localhost:8080/kakao_login");
			sb.append("&code=" + code);
			
			//전달하고자 하는 파라미터들을 보낼 스트림 준비(문자열만 전송하므로 Writer와 Reader를 쓴다.)
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			bw.write(sb.toString());//요청을 POST방식으로 보낸다.
			bw.flush();//스트림 비우기
			
			//결과코드가 200번이면 성공!!!
			int res_code = conn.getResponseCode();
			//System.out.println(res_code);
			
			if(res_code == 200) {//요청이 성공시
				//요청을 통해 얻은 JSON 타입의 결과메세지를 읽어온다.
				//conn으로 통신을 한다. conn은 InputStream과 OutputStream만 가지고 있다.
				//InputStream과 OutputStream을 Reader와 Writer로 변환해주는 역할을 하는
				//InputStreamReader와 OutputStreamWriter를 생성하여 변환해준다.
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				
				//읽을 준비
				String line = "";
				StringBuffer result = new StringBuffer(); 
				while ((line = br.readLine()) != null) {
					result.append(line);
				}//while의 끝!
				
				br.close();
				bw.close();
				
				//받은 결과 확인
				//System.out.println(result.toString());
				
				//JSON파싱 처리
				//"access_token"과 "refresh_token"을 잡아내어 ModelAndView에 저장한 후
				//result.jsp로 이동하여 결과를 표현한다.
				JSONParser j_par = new JSONParser();//JSONParser : org.json.simple.parser
				Object obj = j_par.parse(result.toString());
				
				//자바에서 편하게 사용할 수 있도록 JSON객체로 변환하자!
				JSONObject j_obj = (JSONObject) obj;
				
				access_Token = (String) j_obj.get("access_token");//key값으로 내용을 가져온다.
				refresh_Token = (String) j_obj.get("refresh_token");
				
				//사용자 정보를 얻기 위해 토큰을 이용한 서버 요청 시작!(준비)
				String header = "Bearer " + access_Token;
				String apiURL = "https://kapi.kakao.com/v2/user/me";
				
				URL url2 = new URL(apiURL);
				HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
				
				//POST방식 설정
				conn2.setRequestMethod("POST");
				conn2.setDoOutput(true);
				
				conn2.setRequestProperty("Authorization", header);
				
				res_code = conn2.getResponseCode();//200이 나오면 통신 성공!
				
				if(res_code == HttpURLConnection.HTTP_OK) {//HttpURLConnection.HTTP_OK를 부르면 통신성공 200값을 부른다.
					//정상적으로 사용자 정보를 요청했다면...프로필 정보에서 파일이 아니라 파일의 경로를 알려준다.
					BufferedReader brd = new BufferedReader(new InputStreamReader(conn2.getInputStream())); 
					
					StringBuffer sBuff = new StringBuffer();
					String str = null;
					while ((str = brd.readLine()) != null) {
						sBuff.append(str);//카카오 서버에서 전달되는 모든 값들이
										//sBuff에 누적된다. (JSON)
					}//while의 끝
					
					obj = j_par.parse(sBuff.toString());//파싱해서 JSON 인식
					
					//JSON으로 인식된 정보를 다시 JSON객체로 형변환한다.
					j_obj = (JSONObject) obj;
					
					JSONObject n = (JSONObject) j_obj.get("properties");
					
					String name = (String) n.get("nickname");
					String p_img = (String) n.get("profile_image");
					
					
					//세션에 로그인 정보 저장하기
					MemVO mvo = new MemVO();
					mvo.setM_name(name);
					
					session.setAttribute("mvo", mvo);//로그인!!
					
					mv.addObject("nickname", name);
					mv.addObject("pic", p_img);
				}
			}//if의 끝
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.setViewName("kakao_result");
		return mv;
	}
}
