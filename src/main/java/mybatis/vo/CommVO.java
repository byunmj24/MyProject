package mybatis.vo;

public class CommVO {
	//comment_t라는 테이블에 저장된 각 레코드들을 객체화 시키는 클래스다.
	//comment_t라는 테이블에서 필요한 컬럼들을 멤버변수로 선언한다.
	private String c_idx,
					writer,
					content,
					write_date,
					ip,
					b_idx;

	public String getC_idx() {
		return c_idx;
	}

	public void setC_idx(String c_idx) {
		this.c_idx = c_idx;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWrite_date() {
		return write_date;
	}

	public void setWrite_date(String write_date) {
		this.write_date = write_date;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getB_idx() {
		return b_idx;
	}

	public void setB_idx(String b_idx) {
		this.b_idx = b_idx;
	}
	
}
