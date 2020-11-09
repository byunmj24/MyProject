<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="header">
			<div class="txt_right">
			<%--
				Object obj = request.getAttribute("login_chk");
				boolean chk = (boolean)obj;
				if(!chk){
			--%>
			<c:if test="${sessionScope.mvo eq null }">
				<span><a href="login">로그인</a></span>
			</c:if>
			<c:if test="${sessionScope.mvo ne null }">
				<%--<span><a href="javascript:location.href='logout'">로그아웃</a></span> --%>
				<span><a href="javascript:logout()">로그아웃</a></span>
			</c:if>
			</div>
			<h1>SK Together</h1>
			<ul class="gnb">		 <!-- class 다중 적용 -->
				<li><a href=""><span class="menu m01">기브유</span></a></li>
				<li><a href="/goData"><span class="menu m02">위드유</span></a></li>
				<li><a href="shop"><span class="menu m03">스마트 전통시장</span></a></li>
				<li><a href="notice"><span class="menu m04">BRAVO!</span></a></li>
				<li><a href="bbs"><span class="menu m05">SKT와 사회공헌</span></a></li>
				<li class="end"></li>
			</ul>
		</div>
