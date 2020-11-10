<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
  <a class="navbar-brand" href="/">CSS지옥</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarColor01">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="/">Home
          <span class="sr-only">(current)</span>
        </a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="bbs">Board</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="shop">Market</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="goData">WithJAVA</a>
      </li>
      
    </ul>
    <c:if test="${mvo eq null }">
      <button type="button" class="btn btn-outline-secondary" onclick="location.href='login'">Log in</button>
      <button type="button" class="btn btn-outline-secondary">join</button>
    </c:if>
    <c:if test="${mvo ne null }">
      <button type="button" class="btn btn-outline-secondary" onclick="logout()">Log out</button>
    </c:if>
  </div>
</nav>