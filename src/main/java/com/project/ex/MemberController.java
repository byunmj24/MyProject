package com.project.ex;

import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import mybatis.dao.MemDAO;
import mybatis.vo.MemVO;

@Controller
public class MemberController {
	
	@Autowired
	private MemDAO m_dao;
	
	@Autowired
	private HttpSession session;//자동으로 저장된다.
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(String m_id, String m_pw){
		
		Map<String, Object> map = new Hashtable<String, Object>();
		
		//인자 두개를 MemDAO의 login함수를 호출하면서 전달하면 정확한 정보일 때만 MemVO 한 개를 받게 된다.
		MemVO mvo = m_dao.login(m_id, m_pw);
		
		//mvo가 null이면 아이디 및 비밀번호를 잘못 입력한 경우! 그렇지 않으면 잘 입력한 경우!
		if(mvo != null) {
			//세션에 mvo라는 이름을 mvo를 저장한다.
			session.setAttribute("mvo", mvo);
			map.put("res", "1");
			map.put("mvo", mvo);
		}else {
			map.put("res", "0");
		}
		
		return map;
	}
	
	@RequestMapping("/logout")
	@ResponseBody
	public Map<String, String> logout() {
		//세션으로부터 저장된 mvo를 삭제한다.
		session.removeAttribute("mvo");
		Map<String, String> map = new Hashtable<String, String>();
		map.put("res", "0");
		return map;
	}
}
