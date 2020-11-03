package com.project.ex;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.go.vo.DataVO;

@Controller
public class GoDataController {
	
	private DataVO[] ar;
	
	@RequestMapping("/goData")
	//public String data(Model model){ // 이렇게 하면 model에 저장해서 jsp에서 쓸 수도 있다.
	public ModelAndView data() throws Exception {
		//REST API서버의 URL을 객체로 만들어 두자.
		URL url = new URL("http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchFestival?serviceKey=NYp%2FQaCy%2FuN8vfu6q%2B770FaOzi%2BClyqZxpeAgWrZcR8es58ma1LYGWVOPKn%2BAlIxv1Vb89HT%2FOysXR7mrPY6Ew%3D%3D&MobileOS=ETC&MobileApp=AppTest&arrange=A&listYN=Y&eventStartDate=20201101");
		
		//JAVA에서는 외부 자원을 읽거나 쓰기를 할 때 반드시 Stream 작업을 해야 한다.
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		conn.setRequestProperty("Content-Type", "application/xml");//MIME TYPE
		conn.connect();//연결과 요청!!!
		
		SAXBuilder builder = new SAXBuilder();
		//기존 XML파일이 존재하지 않고 Stream을 연결했다. InputStream으로 받아준다.
		Document doc = builder.build(conn.getInputStream());
		
		Element root = doc.getRootElement();//response
		
		//우린 response(root) 안에 body / items / item들이 필요하다.
		Element body = root.getChild("body");
		Element items = body.getChild("items");
		
		//items안에 있는 여러 개의 item들을 얻어낸다.
		List<Element> e_list = items.getChildren("item");
		
		//e_list에 있는 것은 Element이므로 이것을 List<DataVO> 또는 DataVO[]로 바꿔야 JSP에서 표현할 수 있다.
		//List<DataVO> dvo = new ArrayList<DataVO>();
		ar = new DataVO[e_list.size()];
		int i = 0;
		for(Element e : e_list) {
			String addr1 = e.getChildText("addr1");
			String addr2 = e.getChildText("addr2"); 
			String eventenddate = e.getChildText("eventenddate");
			String eventstartdate = e.getChildText("eventstartdate");
			String firstimage = e.getChildText("firstimage");
			String firstimage2 = e.getChildText("firstimage2");
			String mapx = e.getChildText("mapx");
			String mapy = e.getChildText("mapy");
			String tel = e.getChildText("tel");
			String title = e.getChildText("title");
			DataVO vo = new DataVO(addr1, addr2, eventenddate,
					eventstartdate, firstimage, firstimage2, mapx, mapy, tel, title);
			ar[i++] = vo;
		}//for의 끝
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", ar);
		
		mv.setViewName("tour/goData");
		
		return mv;
	}
	
	@RequestMapping("/viewData")
	public ModelAndView viewData(int idx) {
		ModelAndView mv = new ModelAndView();
		
		//System.out.println(idx);
		//DataVO vo = ar[idx];
		mv.addObject("vo", ar[idx]);
		mv.setViewName("tour/viewData");
		
		return mv;
	}
}
