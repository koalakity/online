package com.zendaimoney.online.oii.pay.yeepay;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *
 * <p>Title: </p>
 * <p>Description: http utils </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author LiLu
 * @version 1.0
 */
public class HttpUtils {

 
  private static final String URL_PARAM_CONNECT_FLAG = "&";
  private static final int SIZE 	= 1024 * 1024;
  private static Log log = LogFactory.getLog(HttpUtils.class);
  
  private HttpUtils() {
  }

  /**
   * GET METHOD
   * @param strUrl String
   * @param map Map
   * @throws IOException
   * @return List
   */
  public static List URLGet(String strUrl, Map map) throws IOException {
    String strtTotalURL = "";
    List result = new ArrayList();
    if(strtTotalURL.indexOf("?") == -1) {
      strtTotalURL = strUrl + "?" + getUrl(map);
    } else {
      strtTotalURL = strUrl + "&" + getUrl(map);
    }
    log.debug("strtTotalURL:" + strtTotalURL);
    URL url = new URL(strtTotalURL);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setUseCaches(false);
    con.setFollowRedirects(true);
    BufferedReader in = new BufferedReader(
        new InputStreamReader(con.getInputStream()),SIZE);
    while (true) {
      String line = in.readLine();
      if (line == null) {
        break;
      }
      else {
    	  result.add(line);
      }
    }
    in.close();
    return (result);
  }

  /**
   * POST METHOD
   * @param strUrl String
   * @param content Map
   * @throws IOException
   * @return List
   */
  public static List URLPost(String strUrl, Map map) throws IOException {

    String content = "";
    content = getUrl(map);
    String totalURL = null;
    if(strUrl.indexOf("?") == -1) {
      totalURL = strUrl + "?" + content;
    } else {
      totalURL = strUrl + "&" + content;
    }
    URL url = new URL(strUrl);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setDoInput(true);
    con.setDoOutput(true);
    con.setAllowUserInteraction(false);
    con.setUseCaches(false);
    con.setRequestMethod("POST");
    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=GBK");
    BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(con.
        getOutputStream()));
    bout.write(content);
    bout.flush();
    bout.close();
    BufferedReader bin = new BufferedReader(new InputStreamReader(con.getInputStream()),SIZE);
    List result = new ArrayList(); 
    while (true) {
      String line = bin.readLine();
      if (line == null) {
        break;
      }
      else {
    	  result.add(line);
      }
    }
    return (result);
  }

  /**
   * ���URL
   * @param map Map
   * @return String
   */
  private static String getUrl(Map map) {
    if (null == map || map.keySet().size() == 0) {
      return ("");
    }
    StringBuffer url = new StringBuffer();
    Set keys = map.keySet();
    for (Iterator i = keys.iterator(); i.hasNext(); ) {
      String key = String.valueOf(i.next());
      if (map.containsKey(key)) {
    	 Object val = map.get(key);
    	 String str = val!=null?val.toString():"";
    	 try {
			str = URLEncoder.encode(str, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        url.append(key).append("=").append(str).
            append(URL_PARAM_CONNECT_FLAG);
      }
    }
    String strURL = "";
    strURL = url.toString();
    if (URL_PARAM_CONNECT_FLAG.equals("" + strURL.charAt(strURL.length() - 1))) {
      strURL = strURL.substring(0, strURL.length() - 1);
    }
    return (strURL);
  }
  /**
   * 
   * @param args
   */
  public static void main(String[] args) throws Exception{
		String keyValue = Configuration.getInstance().getValue("keyValue"); // 商家密钥
		String nodeAuthorizationURL = Configuration.getInstance().getValue("yeepayCommonReqURL"); // 交易请求地址
		// 商家设置用户购买商品的支付信息
		String p0_Cmd = "Buy"; // 在线支付请求，固定值 ”Buy”
		String p1_MerId = Configuration.getInstance().getValue("p1_MerId"); // 商户编号
		String p2_Order = ""; // 商户订单号
		String p3_Amt = "0.001"; // 支付金额
		String p4_Cur = "CNY"; // 交易币种
		String p5_Pid = ""; // 商品名称
		String p6_Pcat = ""; // 商品种类
		String p7_Pdesc = ""; // 商品描述
		String p8_Url = ""; // 商户接收支付成功数据的地址
		String p9_SAF = "0"; // 需要填写送货信息 0：不需要 1:需要
		String pa_MP = ""; // 商户扩展信息
		String pd_FrpId = ""; // 支付通道编码
		// 银行编号必须大写
		pd_FrpId = pd_FrpId.toUpperCase();
		String pr_NeedResponse = "0"; // 是否需要应答机制
		String hmac = ""; // 交易签名串
	    
	    // 获得MD5-HMAC签名
	    hmac = PaymentForOnlineService.getReqMd5HmacForOnlinePayment(p0_Cmd,
				p1_MerId,p2_Order,p3_Amt,p4_Cur,p5_Pid,p6_Pcat,p7_Pdesc,
				p8_Url,p9_SAF,pa_MP,pd_FrpId,pr_NeedResponse,keyValue);
		Map map = new HashMap();
		map.put("p0_Cmd",p0_Cmd);
		map.put("p1_MerId", p1_MerId);
		map.put("p2_Order",p2_Order);
		map.put("p3_Amt", p3_Amt);
		map.put("p4_Cur", p4_Cur);
		map.put("p5_Pid", p5_Pid);
		map.put("p6_Pcat", p6_Pcat);
		map.put("p7_Pdesc", p7_Pdesc);
		map.put("p8_Url",p8_Url);
		map.put("p9_SAF", p9_SAF);
		map.put("pa_MP", pa_MP);
		map.put("pd_FrpId", pd_FrpId);
		map.put("pr_NeedResponse", pr_NeedResponse);
		map.put("hmac", hmac);
		List list = URLPost(nodeAuthorizationURL, map);
	  System.out.print(getUrl(map));
  }
}

