<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
$(function() {

			
				$.ajaxSetup( {
					cache : false
					//关闭AJAX相应的缓存 
				});
				
});

</script>


<div class="account_r">
	<div class="account_r_c account_r_c_add">
		<div class="account_r_c_t">
			<h3 class="on">
				我的收益
			</h3>
		</div>
		<div class="account_r_c_3c">
			<table class="table3">
				<tr>
					<th colspan="4">
						回报统计
					</th>
				</tr>
				<tr>
					<td class="even">
						已赚利息
					</td>
					<td>
						${lctjVo.yzlx}
					</td>
					<td class="even">
						已赚提前还款违约金
					</td>
					<td>
						${lctjVo.yztqhkwyj}
					</td>
				</tr>
				<tr>
					<td class="even">
						已赚逾期罚息
					</td>
					<td>
						${lctjVo.yzyqfx}
					</td>
					<td class="even">
						加权平均收益率
					</td>
					<td>
						${lctjVo.jqpjsyl}
					</td>
				</tr>
			</table>
			<table class="table3">
				<tr>
					<th colspan="4">
						个人理财统计
					</th>
				</tr>
				<tr>
					<td class="even">
						总借出金额
					</td>
					<td>
						${lctjVo.zjcje}
					</td>
					<td class="even">
						总借出笔数
					</td>
					<td>
						${lctjVo.zjcbs}
					</td>
				</tr>
				<tr>
					<td class="even">
						已回收本息
					</td>
					<td>
						${lctjVo.yhsbx}
					</td>
					<td class="even">
						已回收笔数
					</td>
					<td>
						${lctjVo.yhsbs}
					</td>
				</tr>
				<tr>
					<td class="even">
						待回收本息
					</td>
					<td>
						${lctjVo.dhsbx}
					</td>
					<td class="even">
						待回收笔数
					</td>
					<td>
						${lctjVo.dhsbs}
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
