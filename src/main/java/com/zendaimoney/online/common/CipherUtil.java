package com.zendaimoney.online.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CipherUtil{
   
    private static MessageDigest digest = null;
        
    /** * 把inputString加密     */    
    public static String generatePassword(String inputString){    
        return encodeByMD5(inputString);    
    }    
        
       
    public static boolean validatePassword(String password, String inputString){    
        if(password.equals(encodeByMD5(inputString))){    
            return true;    
        } else{    
            return false;    
        }    
    }    
    public static String returnEncodeByMde(String originString){  
        return encodeByMD5(originString);  
    }  
    /**  对字符串进行MD5加密     */    
    private static String encodeByMD5(String originString){  
    	  if (digest == null) {
    	   try {
    	    digest = MessageDigest.getInstance("MD5");
    	   } catch (NoSuchAlgorithmException nsae) {
    	    System.err.println(
    	     "Failed to load the MD5 MessageDigest. "
    	      + "Jive will be unable to function normally.");
    	    nsae.printStackTrace();
    	   }   }
    	  // Now, compute hash.
    	  digest.update(originString.getBytes());
    	  return encodeHex(digest.digest());
    }    

    
    /** 将一个字节数组转化成十六进制形式的字符串     */  
    public static final String encodeHex(byte[] bytes) {
    	  StringBuffer buf = new StringBuffer(bytes.length * 2);
    	  int i;

    	  for (i = 0; i < bytes.length; i++) {
    	   if (((int) bytes[i] & 0xff) < 0x10) {
    	    buf.append("0");
    	   }
    	   buf.append(Long.toString((int) bytes[i] & 0xff, 16));
    	  }
    	  return buf.toString();
    	 }
        
}    