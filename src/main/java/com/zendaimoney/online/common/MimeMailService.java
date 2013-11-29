package com.zendaimoney.online.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * MIME邮件服务类.
 * 
 * 演示由Freemarker引擎生成的的html格式邮件, 并带有附件.
 
 * @author calvin
 */
public class MimeMailService {

	private static final String DEFAULT_ENCODING = "utf-8";
	
	private static ResourceBundle bundle= ResourceBundle.getBundle("sms");

	private static Logger logger = LoggerFactory.getLogger(MimeMailService.class);

	private JavaMailSender mailSender;

	private Template template;
	
	private Template template2;
	
	private Template template3;
	
	private static String errorMessage="";
	//发送普通邮件
	public void sendNormalMail(String emailHtml,String toEmail) {

		try {
//			 ApplicationContext context = new ClassPathXmlApplicationContext("email/applicationContext-email.xml");
//			 context.get
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, DEFAULT_ENCODING);
			
			helper.setTo(toEmail);
			
			helper.setFrom("edaiservice@zendaimoney.com");
			helper.setSubject("密码重置");

			String content = generateContent2(emailHtml);
			helper.setText(content, true);
			
//			File attachment = generateAttachment();
//			helper.addAttachment("mailAttachment.txt", attachment);

			mailSender.send(msg);
			logger.info("HTML版邮件已发送至"+toEmail);
		} catch (MessagingException e) {
			logger.error("构造邮件失败", e);
		} catch (Exception e) {
			logger.error("发送邮件失败", e);
		}
	}

	
	
	/**
	 * 发送MIME格式的用户修改通知邮件.
	 */
	public void sendNotificationMail(String activationUrl,String toEmail, String head) {

		try {
//			 ApplicationContext context = new ClassPathXmlApplicationContext("email/applicationContext-email.xml");
//			 context.get
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, DEFAULT_ENCODING);
			
			helper.setTo(toEmail);
			
			helper.setFrom("edaiservice@zendaimoney.com");
			helper.setSubject(head);

			String content = generateContent(activationUrl);
			helper.setText(content, true);

//			File attachment = generateAttachment();
//			helper.addAttachment("mailAttachment.txt", attachment);

			mailSender.send(msg);
			logger.info("HTML版邮件已发送至"+toEmail);
		} catch (MessagingException e) {
			logger.error("构造邮件失败", e);
		} catch (Exception e) {
			logger.error("发送邮件失败", e);
		}
	}

	/**
	 * 使用Freemarker生成html格式内容.
	 */
	private String generateContent(String activationUrl) throws MessagingException {

		try {
			Map context = Collections.singletonMap("activationUrl", activationUrl);
//			System.out.println(template.getCustomAttribute("head"));
//			System.out.println(template.getSetting("head"));
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, context);
		} catch (IOException e) {
			logger.error("生成邮件内容失败, FreeMarker模板不存在", e);
			throw new MessagingException("FreeMarker模板不存在", e);
		} catch (TemplateException e) {
			logger.error("生成邮件内容失败, FreeMarker处理失败", e);
			throw new MessagingException("FreeMarker处理失败", e);
		}
	}

	/**
	 * 使用Freemarker生成html格式内容.
	 */
	private String generateContent2(String activationUrl) throws MessagingException {

		try {
			Map context = Collections.singletonMap("activationUrl", activationUrl);
//			System.out.println(template.getCustomAttribute("head"));
//			System.out.println(template.getSetting("head"));
			return FreeMarkerTemplateUtils.processTemplateIntoString(template2, context);//Collections.singletonMap用来生成只读的单Key和Value组成的Map
		} catch (IOException e) {
			logger.error("生成邮件内容失败, FreeMarker模板不存在", e);
			throw new MessagingException("FreeMarker模板不存在", e);
		} catch (TemplateException e) {
			logger.error("生成邮件内容失败, FreeMarker处理失败", e);
			throw new MessagingException("FreeMarker处理失败", e);
		}
	}
	
	/**
	 * 使用Freemarker生成html格式内容.
	 */
	private String generateContent3(String activationUrl) throws MessagingException {

		try {
			Map context = Collections.singletonMap("activationUrl", activationUrl);
			//Map context = Collections.singletonMap("activationUrl", activationUrl);
			return FreeMarkerTemplateUtils.processTemplateIntoString(template3, context);
		} catch (IOException e) {
			logger.error("生成邮件内容失败, FreeMarker模板不存在", e);
			throw new MessagingException("FreeMarker模板不存在", e);
		} catch (TemplateException e) {
			logger.error("生成邮件内容失败, FreeMarker处理失败", e);
			throw new MessagingException("FreeMarker处理失败", e);
		}
	}
//	/**
//	 * 获取classpath中的附件.
//	 */
//	private File generateAttachment() throws MessagingException {
//		try {
//			Resource resource = new ClassPathResource("/email/mailAttachment.txt");
//			return resource.getFile();
//		} catch (IOException e) {
//			logger.error("构造邮件失败,附件文件不存在", e);
//			throw new MessagingException("附件文件不存在", e);
//		}
//	}

	/**
	 * Spring的MailSender.
	 */
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * 注入Freemarker引擎配置,构造Freemarker 邮件内容模板.
	 */
	public void setFreemarkerConfiguration(Configuration freemarkerConfiguration) throws IOException {
		//根据freemarkerConfiguration的templateLoaderPath载入文件.
		template = freemarkerConfiguration.getTemplate("mailTemplate.ftl", DEFAULT_ENCODING);
		template2 = freemarkerConfiguration.getTemplate("mailTemplate2.ftl", DEFAULT_ENCODING);
		template3 = freemarkerConfiguration.getTemplate("mailTemplate3.ftl",DEFAULT_ENCODING);
	}
	
	public void sendNotifyMail(String message,String toEmail,String head){


		try {
//			 ApplicationContext context = new ClassPathXmlApplicationContext("email/applicationContext-email.xml");
//			 context.get
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, DEFAULT_ENCODING);
			
			helper.setTo(toEmail);
			
			helper.setFrom("edaiservice@zendaimoney.com");
			helper.setSubject(head);

			String content = generateContent3(message);
			helper.setText(content, true);
			
//			File attachment = generateAttachment();
//			helper.addAttachment("mailAttachment.txt", attachment);

			mailSender.send(msg);
			logger.info("HTML版邮件已发送至"+toEmail);
		} catch (MessagingException e) {
			logger.error("构造邮件失败", e);
		} catch (Exception e) {
			logger.error("发送邮件失败", e);
		}
	}
	
	public String transferMailContent(String key,Map<String, String> paraMap){
		String m=bundle.getString(key);
		if(StringUtils.isEmpty(m)){
			errorMessage="sms.properties文件中无此键值对应,key="+key;
			logger.error(errorMessage);
			//throw new RuntimeException(errorMessage);
			return "";
		}
		try {
			m=new String(m.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			errorMessage="sms.properties转码发生错误,错误信息："+e.getMessage();
			logger.error(errorMessage);
			return "";
		}
		for (int i = 0; i < paraMap.size(); i++) {
			m = m.replace("{" + i + "}",paraMap.get(String.valueOf(i)));
		}
		return m;
	}
}
