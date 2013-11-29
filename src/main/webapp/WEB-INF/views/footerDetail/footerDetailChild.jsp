<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
			<div class="account_r_c account_r_c_add">
				
				<div class="news_t">
					<a href="${ctx }">
						${category==""?"首页":category }&nbsp;
					</a>
					>
					<a href="${sort_url }">
						&nbsp;${sort==""?"栏目名称":sort }
					</a>
					>
					&nbsp;${c_sort==""?"标题":c_sort }
					
				</div>
					
				<div class="news_c2">
					<h3>${twoGradePage.title }</h3>
					<div class="news_detail">
						${twoGradePage.content}
					</div>
				</div>
			</div>
