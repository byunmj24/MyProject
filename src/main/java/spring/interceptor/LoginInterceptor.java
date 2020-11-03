package spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	// 어떤 행동을 수행하기 전에 이 메소드를 먼저 수행(preHandle)
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//로그인 체크
		HttpSession session = request.getSession(true);
		
		//로그인 시 저장했던 객체를 얻어낸다.
		Object obj = session.getAttribute("mvo");//obj가 null이 아니면(있으면) true를 반환한다.
		
		//false가 떨어지면 뭔가가 잘못됐다!! 진행되면 안됨!!
		if(obj == null) {
			response.sendRedirect("/login");//로그인 화면으로 이동
			//로그인이 안되어있다면 여기에서 method가 끝난다.
			return false;
		}
		
		//여기까지 왔으면 로그인이 된 상태 true를 반환하면 된다.
		return true;
	}
	
	
}
