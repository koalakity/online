<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.zendaimoney.online.common.Constants" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
		<link href="<%=basePath %>static/css/fileUploaddefault.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath %>static/css/fileUploaduploadify.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=basePath %>static/js/swfobject.js"></script>
		<script type="text/javascript" src="<%=basePath %>static/js/jquery.uploadify.v2.0.1.js"></script>
		<input type="hidden" value="0" id="uploadCount">
		<div id="fileQueue"></div>
		<input type="file" name="uploadify" id="uploadify" />
		<%--
		<br>
		<p>
		<a href="javascript:jQuery('#uploadify').uploadifyUpload()">开始上传</a>&nbsp;
		<a href="javascript:jQuery('#uploadify').uploadifyClearQueue()">取消所有上传</a>
		</p>
		--%>
		<script type="text/javascript">
		$(document).ready(function() {
			$("#uploadify").uploadify({
				'uploader'       : '<%=basePath%>static/js/uploadify.swf',
				'multi'          : true,
				'queueID'        : 'fileQueue',//文件队列的ID，存放文件集合的div的ID一致。 
				'script'         : '<%=basePath%>upload/fileUpload?userId=<%=session.getAttribute("curr_login_user_id")%>;type=<%=request.getParameter("type")==null?request.getAttribute("type"):request.getParameter("type") %>',//请求的地址
				'method':'POST',
				'cancelImg'      : '<%=basePath%>static/images/fileUpload/fileUploadcancel.png',//小红叉
				'auto'           : true,//是否选中文件后自动上传
				'simUploadLimit' : 1,//同时开始个数
				'queueSizeLimit' : 15,//上传文件个数
				'successTimeout' :10,//10秒不返回默认是成功
				'sizeLimit'		 : '1572864',//默认KB,限制1G	1048576  
				'fileExt'		 : '*.jpg;*.png;*.gif;*.bmp',//上传格式jpg、png、gif、bmp
				'fileDesc'		 : '*.jpg;*.png;*.gif;*.bmp',
				'buttonText'	 : ' ',//按钮文字
				'buttonImg'	 : '<%=basePath%>static/images/btn_r2.png' ,//说明：按钮的背景图片，默认为 NULL
				'width' : '82',
				'height' : '24',
				displayData    : 'speed',//显示进度条格式,速度:speed| 百分比:percentage
				
               
               'onFallback' : function() {
		            alert("没有安装flash插件，不能进行文件上传!");
		        },
               //选中某个文件时候调用
               'onSelect':function(event, ID, fileObj, errorObj){
	               /*
	                event:事件对象。
	                ID：文件的唯一标识，由6为随机字符组成。
					fileObj：选择的文件对象，有name、size、creationDate、modificationDate、type 5个属性。
	              
					
                	alert("唯一标识:" + queueId + "\r\n" + 
						"文件名：" + fileObj.name + "\r\n" + 
						"文件大小：" + fileObj.size + "\r\n" + 
						"创建时间：" + fileObj.creationDate + "\r\n" + 
						"最后修改时间：" + fileObj.modificationDate + "\r\n" + 
						"文件类型：" + fileObj.type 
						); 
						 */
                },
                //某个文件上传成功后调用
                'onComplete' : function(event, queueID, fileObj, data) {  
	                /*
		                event:事件对象。
						queueID：文件的唯一标识，由6为随机字符组成。
						fileObj：选择的文件对象，有name、size、creationDate、modificationDate、type 5个属性。
						data:后台返回数据
	                    alert(fileObj.name+"上传成功！");
                    */
                },
                //所有文件上传成功后调用
                'onAllComplete':function(event,data){
                	/*
                	data的属性：
	                	filesUploaded :上传的所有文件个数。 
						errors ：出现错误的个数。 
						allBytesLoaded ：所有上传文件的总大小。 
						speed ：平均上传速率 kb/s 
                	
                	alert("上传的所有文件个数:"+data.filesUploaded+",平均上传速率 kb/s :"+data.speed);*/
                }
			});
		});
		
		var basePath='<%=basePath%>';
		function deleteFile(dbid,_objId){
			//alert("数据库ID："+id+" 超链接ID："+_objId);
	        if(dbid=='' || dbid==null){
	        	//只是删除界面效果
	        	$("#"+_objId).fadeOut(250, function() { jQuery(this).remove()});
	        }else{
		        $.ajax({
		               data:{id:dbid,type:<%=request.getParameter("type")==null?request.getAttribute("type"):request.getParameter("type") %>},
				       url: "<%=basePath%>upload/deleteById",
				       type: "POST",
				       dataType: 'json',
					   success: function(data){
					     if(data==true){
					     	//删除文件
							$("#"+_objId).fadeOut(250, function() { jQuery(this).remove()});
					     }
					   }
				});
			}
		}
		</script>
