package com.project.ex;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import mybatis.dao.BbsDAO;
import mybatis.dao.MemDAO;
import mybatis.util.Paging;
import mybatis.vo.BbsVO;
import mybatis.vo.MemVO;
import spring.util.FileUploadUtil;

@Controller
public class ShopController {
	
	private int blockList = 10;//한 페이지에 보여질 게시물 수
	private int blockPage = 5;//한 블록당 보여질 페이지 수
	
	
	@Autowired
	private BbsDAO b_dao;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ServletContext application;
	
	//첨부파일이 저장될 위치
	private String uploadPath = "/resources/upload";
	
	@RequestMapping("/shop")
	public ModelAndView shopList(String bname, String cPage) {
		ModelAndView mv = new ModelAndView();
		if(bname == null)
			bname = "SHOP";
		
		int c_page = 1;
		if(cPage != null)
			c_page = Integer.parseInt(cPage);
		
		int rowTotal = b_dao.totalCount(bname);
		
		Paging page = new Paging(c_page, rowTotal, blockList, blockPage);
		
		BbsVO[] ar = b_dao.getList(page.getBegin(), page.getEnd(), bname);
		
		mv.addObject("ar", ar);
		mv.addObject("rowTotal", rowTotal);
		mv.addObject("nowPage", c_page);
		mv.addObject("blockList", blockList);
		mv.addObject("p_code", page.getSb().toString());
		
		mv.setViewName("shop/list");
		return mv;
	}
	
	@RequestMapping("/shopWrite")
	@ResponseBody
	public Map<String, String> shopWrite() {
		Map<String, String> map = new Hashtable<String, String>();
		MemVO mvo = (MemVO) session.getAttribute("mvo");
		
		if(mvo != null) {
			map.put("chk", "1");
			map.put("url", "shop_writeForm");
		}else {
			map.put("chk", "0");
			map.put("url", "shop_writeForm");
		}
		
		return map;
	}
	
	@RequestMapping("/shop_writeForm")
	public String shopWriteForm() {
		MemVO mvo = (MemVO) session.getAttribute("mvo");
		String viewPath = null;
		if(mvo != null)
			viewPath = "shop/write";
		else
			viewPath = "login";
		return viewPath;
	}
	
	@RequestMapping("/shopWrite_ok")
	public ModelAndView writeOk(BbsVO vo) throws Exception {
		ModelAndView mv = new ModelAndView();
		
		MultipartFile mf = vo.getFile();
		if(mf != null && mf.getSize() > 0) {
			String path = application.getRealPath(uploadPath);
			String f_name = mf.getOriginalFilename();
			
			f_name = FileUploadUtil.checkSameFileName(f_name, path);
			
			mf.transferTo(new File(path, f_name));
			
			vo.setFile_name(f_name);
		}
		
		vo.setIp(request.getRemoteAddr());
		
		MemVO mvo = (MemVO) session.getAttribute("mvo");
		vo.setWriter(mvo.getM_name());
		
		b_dao.add(vo);
		
		mv.setViewName("redirect:/shop");
		
		return mv;
	}
	
	@RequestMapping("/shopView")
	public ModelAndView shopView(String cPage, String b_idx) {
		Object obj = session.getAttribute("view_list");
		List<BbsVO> v_list = null;
		if(obj == null) {
			v_list = new ArrayList<BbsVO>();
			
			session.setAttribute("view_list", v_list);
		}else {
			v_list = (List<BbsVO>) obj;
		}
		
		boolean chk = false;
		for(BbsVO bvo : v_list) {
			if(bvo.getB_idx().equals(b_idx)) {
				chk = true;
				break;
			}
		}
		
		BbsVO vo = b_dao.getBbs(b_idx);
		
		if(!chk) {
			b_dao.updateHit(b_idx);
			int getHit = Integer.parseInt(vo.getHit()) + 1;
			vo.setHit(String.valueOf(getHit));
			v_list.add(vo);
		}
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("vo", vo);
		mv.setViewName("shop/view");
		
		session.setAttribute("bvo", vo);
		
		return mv;
	}
	
	
	@RequestMapping("/shopEdit")
	public ModelAndView edit(BbsVO vo) throws Exception {
		ModelAndView mv = new ModelAndView();
		
		String c_type = request.getContentType();
		
		
		if(c_type != null && c_type.startsWith("multipart")) {
			
			MultipartFile mf = vo.getFile();
			
			if(mf != null && mf.getSize() > 0 && mf.getOriginalFilename().trim().length() > 0) {
				
				String path = application.getRealPath(uploadPath);
				
				String f_name = mf.getOriginalFilename();
				
				f_name = FileUploadUtil.checkSameFileName(f_name, path);
				
				mf.transferTo(new File(path, f_name));
				
				vo.setFile_name(f_name);
			}
			
			vo.setIp(request.getRemoteAddr());
			
			b_dao.editBbs(vo);
			
			mv.setViewName("redirect:/shopView?b_idx="+vo.getB_idx()+"&cPage="+vo.getcPage());
		}else {
			mv.setViewName("shop/edit");
		}
		
		return mv;
	}
	
}