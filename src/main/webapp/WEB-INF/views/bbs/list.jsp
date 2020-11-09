<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link type="text/css" rel="stylesheet" href="css/common.css"/>
<link type="text/css" rel="stylesheet" href="css/login.css"/>
<link type="text/css" rel="stylesheet" href="css/bbs.css"/>
<link type="text/css" rel="stylesheet" href="css/bootstrap.css"/>
</head>
<body>
<div id="wrap">
	<!-- 상단 영역 -->
	<jsp:include page="../header2.jsp"/>
	<!-- 상단 영역 끝 -->
	<!-- 콘텐츠 영역 -->
	<div id="contents_sub">
		<h1 style="font-size: 30px; color: #000; margin-bottom: 20px;">BOARD</h1>
		<div>
			<table class="table table-hover" summary="게시판 목록">
				<caption>게시판 목록</caption>
				<colgroup>
					<col width="10%"/>
					<col width="50%"/>
					<col width="10%"/>
					<col width="20%"/>
					<col width="10%"/>
				</colgroup>
				<thead>
					<tr class="table-primary">
						<th scope="col"><p class="text-primary">번호</p></th>
						<th scope="col"><p class="text-primary">제목</p></th>
						<th scope="col"><p class="text-primary">글쓴이</p></th>
						<th scope="col"><p class="text-primary">날짜</p></th>
						<th scope="col"><p class="text-primary">조회수</p></th>
					</tr>
				</thead>
				
				<tfoot>
	                      <tr>
	                          <td colspan="4">
	                             ${requestScope.p_code }
	                          </td>
							  <td>
								<input type="button" value="글쓰기" class="btn btn-primary"/>
							  </td>
	                      </tr>
	                  </tfoot>
				<tbody>
				<c:if test="${ar ne null }">
						<c:forEach var="vo" items="${requestScope.ar }" varStatus="st">
					<tr class="table-default">
						<td>${rowTotal - st.index - (blockList*(nowPage - 1)) }</td>
						<td style="text-align: left">
							<a href="view?cPage=${nowPage }&b_idx=${vo.b_idx}">${vo.subject }</a></td>
						<td>${vo.writer }</td>
						<td>
						<c:if test="${vo.write_date ne null }">
							${fn:substring(vo.write_date, 0, 10) }
						</c:if>
						</td>
						<td>${vo.hit }</td>
					</tr>
						</c:forEach>
				</c:if>
				<c:if test="${ar eq null }">
					<tr>
						<td colspan="5" class="empty">등록된 게시물이 없습니다.</td>
					</tr>
				</c:if>
				</tbody>
			</table>
		</div>
	</div>
	<!-- 콘텐츠 영역 끝-->
	<!-- 하단 영역 -->
		<jsp:include page="../footer.jsp"/>
	<!-- 하단 영역 끝 -->
</div>
<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
<script>

	$(function(){
		$("#write_btn").bind("click", function(){
			$.ajax({
				url: "write",
				dataType: "json"
			}).done(function(data){
				//data 안에 chk라는 변수가 0이면 로그인이 필요한 상황이다.
				if(data.chk == "0"){
					alert("로그인이 필요합니다.");
					location.href=data.url;
				}else if(data.chk == "1"){
					//글쓰기 화면으로 이동!
					location.href=data.url;
				}
			});
		});
	});
</script>
</body>
</html>