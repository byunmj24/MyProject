package com.project.ex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import mybatis.dao.MemDAO;
import mybatis.vo.MemVO;

@Controller
public class IndexController {
	@Autowired
	private MemDAO m_dao; 
	
	@RequestMapping("/")
	public String index() {
		return "index";// views/index.jsp를 의미
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST) //post방식으로 요청된 /login은 이쪽으로 온다!!
	public ModelAndView login(MemVO vo) {
		ModelAndView mv = new ModelAndView();
		
		System.out.println("ID : " + vo.getM_id() + " / " + "PW : " + vo.getM_pw());
		
		
		mv.setViewName("index");
		
		return mv;
	}
}
