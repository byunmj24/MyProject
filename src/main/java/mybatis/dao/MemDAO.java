package mybatis.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mybatis.vo.MemVO;

@Component
public class MemDAO {
	
	@Autowired
	private SqlSessionTemplate sst;
	
	
	public MemDAO() {
		System.out.println("MemDAO~~~~~!!");
	}
	
	//회원 목록
	public MemVO[] getAll() {
		MemVO[] ar = null;
		
		List<MemVO> list = sst.selectList("mem.all");
		if(list != null && list.size() > 0) {
			ar = new MemVO[list.size()];//현재 배열은 MemVO가 생성된 것이 아니라 MemVO를 
										//저장할 수 있는 공간이 마련된 것이다. 그 크기가 list의 크기와 같다.
			
			//list에 있는 각 요소들을 배열인 ar에 복사한다.
			list.toArray(ar);
		}
		
		return ar;
	}
	
	//로그인을 수행하는 기능 - MemberController
	public MemVO login(String m_id, String m_pw) {
		Map<String, String> map = new Hashtable<String, String>();
		map.put("m_id", m_id);
		map.put("m_pw", m_pw);
		
		//MemVO vo = sst.selectOne("mem.login", map);	
		//return vo;
		return sst.selectOne("mem.login", map);
	}
}
