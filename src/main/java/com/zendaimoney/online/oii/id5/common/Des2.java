package com.zendaimoney.online.oii.id5.common;

import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Des2
{
    public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";
    private static Log log = LogFactory.getLog(Des2.class);

    /**
     * DES算法，加密
     *
     * @param data 待加密字符串
     * @param key  加密私钥，长度不能够小于8位
     * @return 加密后的字节数组，一般结合Base64编码使用
     * @throws CryptException 异常
     */
    public static String encode(String key,String data) throws Exception
    {
        return encode(key, data.getBytes("GBK"));
    }
    /**
     * DES算法，加密
     *
     * @param data 待加密字符串
     * @param key  加密私钥，长度不能够小于8位
     * @return 加密后的字节数组，一般结合Base64编码使用
     * @throws CryptException 异常
     */
    public static String encode(String key,byte[] data) throws Exception
    {
        try
        {
	    	DESKeySpec dks = new DESKeySpec(key.getBytes());
	    	
	    	SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            //key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.ENCRYPT_MODE, secretKey,paramSpec);
            
            byte[] bytes = cipher.doFinal(data);
            return Base64.encode(bytes);
        } catch (Exception e)
        {
            throw new Exception(e);
        }
    }

    /**
     * DES算法，解密
     *
     * @param data 待解密字符串
     * @param key  解密私钥，长度不能够小于8位
     * @return 解密后的字节数组
     * @throws Exception 异常
     */
    public static byte[] decode(String key,byte[] data) throws Exception
    {
        try
        {
        	SecureRandom sr = new SecureRandom();
	    	DESKeySpec dks = new DESKeySpec(key.getBytes());
	    	SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            //key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.DECRYPT_MODE, secretKey,paramSpec);
            return cipher.doFinal(data);
        } catch (Exception e)
        {
            throw new Exception(e);
        }
    }
    
    /**
     * 获取编码后的值
     * @param key
     * @param data
     * @return
     * @throws Exception
     */
    public static String decodeValue(String key,String data) 
    {
    	byte[] datas;
    	String value = null;
		try {
			if(System.getProperty("os.name") != null && (System.getProperty("os.name").equalsIgnoreCase("sunos") || System.getProperty("os.name").equalsIgnoreCase("linux")))
	        {
	    		log.debug("os.name(true)=" + System.getProperty("os.name"));
	    		datas = decode(key, Base64.decode(data));
	    		log.debug("ddd=" + new String(datas));
	        }
	    	else
	    	{
	    		log.debug("os.name(false)=" + System.getProperty("os.name"));
	    		datas = decode(key, Base64.decode(data));
	    		log.debug("ddd=" + new String(datas));
	    	}
			
			value = new String(datas,"GBK");
		} catch (Exception e) {
			e.printStackTrace();
			log.warn("解密失败");
			value = "";
		}
    	return value;
    }

    public static void main(String[] args) throws Exception
    {
      
      //System.out.println("明：abc ；密：" + Des2.encode("12345678","abc"));
  	 // System.out.println("明：ABC ；密：" + Des2.encode("12345678","ABC"));
  	 // //System.out.println("明：中国人 ；密：" + Des2.encode("12345678","中国人"));
  	//  System.out.println("g3ME4dIH/5dpd1M mHqZ2eWk8pxuq8V5=" + Des2.decodeValue("88888888", "g3ME4dIH/5dpd1MmHqZ2eWk8pxuq8V5"));
//  	  System.out.println("ss=" + Des2.decodeValue("88888888", "rc+uwHsk8I4SGFElQ4NMQbZgdrPwMSNrfv0Pt5RQ15/2q+i7jd6JFOgahlPhhGDuohv5MS7K8UZiVxP7Zp796Y0FQPJu58wx5LhlHUEZCUXcHAKnjX0EsQSrpfmIF/JZxMfcGCNWCABDTRQG8oDs2CMYGI/g2cmL9nU6EpG4RGH9zhx9Gsovv/mnxKKqLI9fySUfAaMxReEZoUWpEFPKk7xvV7mxQsUNNaaCekCt0D+FP65bQBweZA=="));
  	 
  	 // System.out.println("明：在那遥远的地方 ；密：" + Des2.encode("12345678","g3ME4dIH/5dpd1M mHqZ2eWk8pxuq8V5"));
  	  //System.out.println(new String(Des2.decode("wwwid5cn", "ABB0E340805D367C438E24FC4005C121F60247F6EE4430B5".toLowerCase().getBytes())));
  	  //System.out.println("dd=" + Des2.encode("12345678", new String("idtagpckhd")));
//  	  String testStr="dd=" + Des2.encode("88888888", new String("周慧".getBytes("GBK"),"GBK"));
//  	 System.out.println("dd=" + Des2.encode("88888888", new String("周慧".getBytes("GBK"),"GBK")));
    	System.out.println(encode("12345678", "马英波,220324198010203712"));
  	//System.out.println("dd=" + Des2.decodeValue("12345678", "S078JKKtSvAMWap/sIGpd4eiphXoB0MB8EfFuFogsz8RLhrqnYxuAI8jchhX2zrYlCcVJcbeIfkwP7h6A+13QRIPDjUZPyuG6UtnbQ6iIeZ3kbVstT9zQlcDUPMVjAD4JHVZ2lWCPqykPdm+SBwc7OSYGsS5Q8mEyAjGgMXHAVxlQeLauYa356CSOcsB/pjUMYnx0gIkq6UbBaww+hoQmEhAruzflpFx55lURPRvl3NV8WF848eB9oBQRlEVZO042VqVHB8Ke9GL+UOgWHgU54XcjBzP6r4zZNv45C4CuHiZc4P5YWU3xoS7+LA6lUpdKd2IUuLi5jgAmE4Z2y+1x7HzLoraA/xoi1VQ+aa6pEpMjDuyUtKOtoI5clFVx1KycSsZtRHVFvNLijz/YgkBBKb6TztzvZBBgnvuUM+dYGlW6DB8K6mqWu+8f2qK/W1Ulm51lL1oajz+knPxf1BaV1CifFg2TF9tmxKzwNPhgIXm1LE5kj2Hfj06ZE6WvxTLMHOwO2Uc3a/0KUAvom3tR3x7SDzJfbkTMTQO1vICznmm88lkXZRQ8YTugRKUxj+y2W3D+NO4dDE53oJCjmq70bwp3erYiNcGBxsgIZLImrgDox4v6Gwt/tQP0cFOEHErf765rjvhJtpBSVjbt7GtUdGmxK5hR6+avaijmoDoeGeF4iaPed7Yi+nuRvvm7NbGC+sh7nl5ztJXu0LdpU4coXgBDCVhchcJwoqyIFwXbK75GUYdhY4iP/Ttl+yWDR/07zepfEGLbIoJWQnmFVL8Di2GZ7A8mbKP"));
    }
}
