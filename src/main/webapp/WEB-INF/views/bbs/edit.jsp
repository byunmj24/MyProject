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
</head>
<body>
<div id="wrap">
	<!-- 상단 영역 -->
	<jsp:include page="../header.jsp"/>
	<!-- 상단 영역 끝 -->
	<!-- 콘텐츠 영역 -->
	<div id="contents_sub">
		<h1 style="font-size: 30px; color: #000; margin-bottom: 20px;">공헌편집하기</h1>
		<div class="bbs_area" id="bbs">
			<form action="edit" method="post" encType="multipart/form-data">
		<%--
		<input type="hidden" name="type" value="write"/>
		//위는 Controller로 전달하지 못하므로 의미가 없다.
		 --%>
		<input type="hidden" name="cPage" value="${param.cPage }"/>
		<input type="hidden" name="b_idx" value="${sessionScope.bvo.b_idx }"/>
		<table summary="게시판 글쓰기">
			<caption>게시판 편집하기</caption>
			<tbody>
				<tr>
					<th>제목:</th>
					<td><input type="text" name="subject" size="45" value="${bvo.subject }"/></td>
				</tr>
				<tr>
					<th>내용:</th>
					<td><textarea name="content" cols="50" 
							rows="8" >${bvo.content }</textarea></td>
				</tr>
				<tr>
					<th>첨부파일:<c:if test="${bvo.file_name ne null }">(${bvo.file_name })</c:if></th>
					<td><input type="file" name="file"></td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="button" value="보내기" onclick="sendData()"/>
						<input type="button" value="다시"/>
						<input type="button" value="목록"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
		</div>
	</div>
	<!-- 콘텐츠 영역 끝-->
	<!-- 하단 영역 -->
		<jsp:include page="../footer.jsp"/>
	<!-- 하단 영역 끝 -->
</div>
<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
<script type="text/javascript">
	function sendData(){
		for(var i=0 ; i<document.forms[0].elements.length ; i++){
			if(document.forms[0].elements[i].value == ""){
				alert(document.forms[0].elements[i].name+
						"를 입력하세요");
				document.forms[0].elements[i].focus();
				return;//수행 중단
			}
		}

//		document.forms[0].action = "test.jsp";
		document.forms[0].submit();
	}
</script>
</body>
</html>