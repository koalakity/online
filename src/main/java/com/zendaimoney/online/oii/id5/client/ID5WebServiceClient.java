package com.zendaimoney.online.oii.id5.client;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zendaimoney.online.admin.entity.account.ID5Check;
import com.zendaimoney.online.oii.id5.common.Des2;
import com.zendaimoney.online.oii.id5.service.QueryValidatorServices;
import com.zendaimoney.online.oii.id5.service.QueryValidatorServicesProxy;

public class ID5WebServiceClient {
	private static Logger logger = LoggerFactory.getLogger(ID5WebServiceClient.class);
	/**
	 * 
	 * @param name：姓名
	 * @param id_card_num：身份证
	 * @return 0成功 -1失败
	 */
	/*  废弃方法
	public static int checkID5(String name,String id_card_num){
		if(StringUtils.isEmpty(name)||StringUtils.isEmpty(id_card_num)){
			logger.error("参数不能为空,name="+name+",id_card_num="+id_card_num);
			return -1;
		}
		String xmlStr=proxy(name,id_card_num);
		//String xmlStr="";
		if(StringUtils.isEmpty(xmlStr)){
			logger.error("WebService接口调用错误,name="+name+",id_card_num="+id_card_num);
			return -1;
		}
		Document doc=creXmlDoc(xmlStr);
		if(doc==null){
			logger.error("creXmlDoc方法创建document出错,xml:"+xmlStr);
			return -2;
		}
		return parseXML(doc);
//		return 0;
	}*/
	
	/**
	 * @author Ray
	 * @date 2012-10-26 上午10:33:54
	 * @param name 姓名
	 * @param id_card_num 身份证号
	 * @return	返回对象ID5Check对象，其中状态为1一致，2不一致。3库中无此号 -1调用接口出错，-2解析或者传输出错
	 * description:调用第三方ID5验证。
	 */
	public static ID5Check checkID5All(String name,String id_card_num){
		if(StringUtils.isEmpty(name)||StringUtils.isEmpty(id_card_num)){
			logger.error("参数不能为空,name="+name+",id_card_num="+id_card_num);
			ID5Check id5Check= new ID5Check();
			id5Check.setCheckStatusCode("-1");
			return id5Check;
		}
		String xmlStr=proxy(name,id_card_num);
		//String xmlStr="";
		if(StringUtils.isEmpty(xmlStr)){
			logger.error("WebService接口调用错误,name="+name+",id_card_num="+id_card_num);
			ID5Check id5Check= new ID5Check();
			id5Check.setCheckStatusCode("-1");
			return id5Check;
		}
		Document doc=creXmlDoc(xmlStr);
		if(doc==null){
			logger.error("creXmlDoc方法创建document出错,xml:"+xmlStr);
			ID5Check id5Check= new ID5Check();
			id5Check.setCheckStatusCode("-2");
			return id5Check;
		}
		return parseXMLAll(doc);
//		return 0;
	}
	
	
	

	/**
	 * 解析XML返回的状态
	 * @param doc
	 * @return 0成功 -1失败
	 */
//	private static int parseXML(Document doc){
//		List<Element> list=doc.selectNodes("data/policeCheckInfos/policeCheckInfo/compStatus");
//		if(list==null||list.size()==0){
//			logger.error("XML数据返回格式数据,XML:"+doc);
//			return -2;
//		}
//		Element e=list.get(0);
//		String s=e.getText();
//		int status;
//		try {
//			status=Integer.parseInt(s);
//			if(status==3){
//				logger.info("对比一致:"+doc);
//				return 3;
//			}else if(status==2){
//				logger.info("对比不一致:"+doc);
//				return -2;
//			}else if(status==1){
//				logger.info("库中无此号:"+doc);
//				return -2;
//			}else{
//				logger.error("XML数据返回格式数据,XML:"+doc);
//				return -2;
//			}
//		} catch (Exception ep) {
//			logger.error("XML数据返回值格式错误,XML:"+doc);
//			ep.printStackTrace();
//			return -2;
//		}
//	}
	
	
	/**
	 * @author Ray
	 * @date 2012-10-26 上午10:45:01
	 * @param doc
	 * @return
	 * description:解析XML全部信息
	 */
	private static ID5Check parseXMLAll(Document doc){
		ID5Check id5Check= new ID5Check();
		List<Element> statuslist=doc.selectNodes("data/policeCheckInfos/policeCheckInfo/compStatus");
		if(statuslist==null||statuslist.size()==0){
			logger.error("XML数据返回格式错误,XML:"+doc);
			id5Check.setCheckStatusCode("-2");
			return id5Check;
		}
		List<Element> nameList=doc.selectNodes("data/policeCheckInfos/policeCheckInfo/name");
		List<Element> identitycardList=doc.selectNodes("data/policeCheckInfos/policeCheckInfo/identitycard");
		String name=nameList.get(0).getText();
		String cardId=identitycardList.get(0).getText();
		id5Check.setName(name);
		id5Check.setCardId(cardId);
		
		Element e=statuslist.get(0);
		String checkStatusCode=e.getText();
		int status;
		try {
			status=Integer.parseInt(checkStatusCode);
			if(status==3){
				logger.info("对比一致:"+doc);
				List<Element> policeaddList=doc.selectNodes("data/policeCheckInfos/policeCheckInfo/policeadd");
				List<Element> checkPhotoList=doc.selectNodes("data/policeCheckInfos/policeCheckInfo/checkPhoto");
				List<Element> idcOriCt2List=doc.selectNodes("data/policeCheckInfos/policeCheckInfo/idcOriCt2");
				String location1=policeaddList.get(0).getText();
				String location2=null;
				if(idcOriCt2List.size()>0){
					location2=idcOriCt2List.get(0).getText();
				}
				if(checkPhotoList==null ||checkPhotoList.size()<=0){
					logger.info("对比一致,但此人无照片信息反馈！");
				}else{
					String photo=checkPhotoList.get(0).getText();
					id5Check.setPhoto(photo);
				}
				id5Check.setCheckStatusCode("3");
				id5Check.setLocation1(location1);
				id5Check.setLocation2(location2);
				return id5Check;
			}else if(status==2){
				logger.info("对比不一致:"+doc);
				id5Check.setCheckStatusCode("2");
				return id5Check;
			}else if(status==1){
				logger.info("库中无此号:"+doc);
				id5Check.setCheckStatusCode("1");
				return id5Check;
			}else{
				logger.error("XML数据返回格式数据错误,XML:"+doc);
				id5Check.setCheckStatusCode("-2");
				return id5Check;
			}
		} catch (Exception ep) {
			logger.error("XML数据返回值格式错误,XML:"+doc);
			ep.printStackTrace();
			id5Check.setCheckStatusCode("-2");
			return id5Check;
		}
	}
	
	
	private static Document creXmlDoc(String xmlStr){
		if (StringUtils.isEmpty(xmlStr)) {
			throw new RuntimeException("参数不能为空");
		}
		SAXReader saxReader = new SAXReader();
		Document document=null;
		try {
			document = saxReader.read(new ByteArrayInputStream(xmlStr.getBytes("UTF-8")));
			Element rootElement = document.getRootElement();
			String getXMLEncoding = document.getXMLEncoding();
			String rootname = rootElement.getName();
			System.out.println("getXMLEncoding>>>" + getXMLEncoding+ ",rootname>>>" + rootname);
			OutputFormat format = OutputFormat.createPrettyPrint();
			/** 指定XML字符集编码 */
			format.setEncoding("UTF-8");
			///** 将document中的内容写入文件中 */
			//XMLWriter writer = new XMLWriter(new FileWriter(new File("cctv.xml")), format);
			//writer.write(document);
			//writer.close();
			logger.info(xmlStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return document;
	}
	/**
	 * 
	 * @param name
	 * @param id_card_num
	 * @throws Exception
	 */
	private static String proxy(String name,String id_card_num) 
	{
		try {
			//获得WebServices的代理对象
			QueryValidatorServicesProxy proxy = new QueryValidatorServicesProxy();
			proxy.setEndpoint("http://gboss.id5.cn/services/QueryValidatorServices");
			QueryValidatorServices service =  proxy.getQueryValidatorServices();
			//对调用的参数进行加密
			String key = "12345678";
			String userName = Des2.encode(key, "zhengdajiekou");
			String password = Des2.encode(key, "zhengdajiekou_CDG3_@l(");
			System.setProperty("javax.net.ssl.trustStore", "keystore");
			//查询返回结果
			String resultXML = "";
			/*------身份信息核查比对-------*/
			String datasource = Des2.encode(key,"1A020201");
			//单条
//			String param = "宋雪,210219197101011246";
			String param=name+","+id_card_num;
			
//			resultXML=Des2.encode(key, new String(param.getBytes("GBK"),"GBK"));
//			resultXML=Des2.encode(key, param);
			resultXML = service.querySingle(userName, password, datasource, Des2.encode(key, param));
			//批量
//			String params = "王茜,150202198302101248;" + 
//							"吴晨晨,36252519821201061x;" + 
//							"王鹏,110108197412255477";
//			resultXML = service.queryBatch(userName, password, datasource, Des2.encode(key, params));
			//解密
			resultXML = Des2.decodeValue(key, resultXML);
			return resultXML;
		} catch (Exception e) {
			logger.error("WebService接口调用错误,name="+name+",id_card_num="+id_card_num+",错误信息："+e.getMessage());
			e.printStackTrace();
			return "";
		}
	}
	/**
	 * 
	 * @param args
	 */
//	public static void main(String[] args){
//		String xmlStr=
//			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
//			+"<data>"
//			+"	<message>"
//			+"		<status>0</status>"
//			+"		<value>处理成功</value>"
//			+"	</message>"
//			+"	<policeCheckInfos>"
//			+"		<policeCheckInfo name=\"刘静\""
//			+"			id=\"110107197111011528\">"
//			+"			<message>"
//			+"				<status>0</status>"
//			+"				<value>查询成功</value>"
//			+"			</message>"
//			+"			<name desc=\"姓名\">刘静</name>"
//			+"			<identitycard desc=\"身份证号\">110107197111011528</identitycard>"
//			+"			<compStatus desc=\"比对状态\">3</compStatus>"
//			+"			<compResult desc=\"比对结果\">一致</compResult>"
//			+"			<policeadd desc=\"原始发证地\">北京市石景山区</policeadd>"
//			+"			<checkPhoto desc=\"照片\">照片(base64码)</checkPhoto>"
//			+"			<idcOriCt2 desc=\"原始发证地2\">北京市石景山区</idcOriCt2>"
//			+"		</policeCheckInfo>"
//			+"	</policeCheckInfos>"
//			+"</data>";
//		Document doc=creXmlDoc(xmlStr);
//		List<Element> list1=doc.selectNodes("data/policeCheckInfos/policeCheckInfo/name");
//		List<Element> list2=doc.selectNodes("data/policeCheckInfos/policeCheckInfo/identitycard");
//		List<Element> list3=doc.selectNodes("data/policeCheckInfos/policeCheckInfo/compStatus");
//		List<Element> list4=doc.selectNodes("data/policeCheckInfos/policeCheckInfo/compResult");
//		List<Element> list5=doc.selectNodes("data/policeCheckInfos/policeCheckInfo/policeadd");
//		
//		List<Element> list6=doc.selectNodes("data/policeCheckInfos/policeCheckInfo/checkPhoto");
//		System.out.println((list6.size()<=0));
//		List<Element> list7=doc.selectNodes("data/policeCheckInfos/policeCheckInfo/idcOriCt2");
//		
//		Element e1=list1.get(0);
//		Element e2=list2.get(0);
//		Element e3=list3.get(0);
//		Element e4=list4.get(0);
//		Element e5=list5.get(0);
//		Element e6=list6.get(0);
//		Element e7=list7.get(0);
//		
//		System.out.println(e1.getText()+"  "+e2.getText()+"  "+e3.getText()+"  "+e4.getText()+"  "+e5.getText()+"  "+e6.getText()+"  "+e7.getText());
		//================================================================
//		String rsXML=proxy("马英波","220324198010203712");
//		System.out.println(rsXML);
//		int i=checkID5("马英波","220324198010203712");
//	}
}
