package com.zendaimoney.online.common;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/**
 * 实现token令牌的产生已经验证
 * @author 王腾飞
 *
 */
public class TokenUtil { 
    /** 
     * 获得唯一的token 
     * @param request 
     * @return 
     */ 
    public static String getToken(HttpServletRequest request,String attributeName) { 
        //★UUID可以产生唯一的序列码 
        UUID uuid = UUID.randomUUID(); 
        String token = uuid.toString(); 
         
        HttpSession session = request.getSession(); 
        session.setAttribute(attributeName, token); 
         
        return token; 
    } 
    /** 
     * 验证token 
     * @param request 
     * @param requestToken 
     * @return 
     */ 
    public static boolean validateToken(HttpServletRequest request,String requestToken,String attributeName) { 
        HttpSession session = request.getSession(); 
        String sessionToken = (String) session.getAttribute(attributeName); 
         
        if(sessionToken != null && 
           requestToken != null && 
           sessionToken.equals(requestToken)) { 
             
            session.removeAttribute(attributeName); 
            return true; 
             
        } else { 
            return false; 
        } 
    } 
     
} 
