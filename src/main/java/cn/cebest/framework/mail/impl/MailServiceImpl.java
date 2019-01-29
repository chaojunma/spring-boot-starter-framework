package cn.cebest.framework.mail.impl;

import java.io.File;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import cn.cebest.framework.mail.MailService;
import lombok.extern.slf4j.Slf4j;


/**
 *  发送邮件接口实现
  * @author maming  
  * @date 2018年8月28日
 */

@Slf4j
public class MailServiceImpl implements MailService{

	 @Autowired
	 private JavaMailSender mailSender;
	 
	 @Value("${gc.mail.from}")
	 private String from;
	 
	/**
	 * 发送文本邮件
	 * @param to      对方账户
	 * @param subject 主题
	 * @param content 内容
	 */
	@Override
	public void sendSimpleMail(String to, String subject, String content) {
		SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        try {
            mailSender.send(message);
            log.info("简单邮件已经发送。");
        } catch (Exception e) {
            log.error("发送简单邮件时发生异常！", e);
        }

	}

	
	/**
	 * 发送html格式邮件
	 * @param to      对方账户
	 * @param subject 主题
	 * @param content 内容
	 */
	@Override
	public void sendHtmlMail(String to, String subject, String content) {
		MimeMessage message = mailSender.createMimeMessage();

	    try {
	        //true表示需要创建一个multipart message
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        helper.setFrom(from);
	        helper.setTo(to);
	        helper.setSubject(subject);
	        helper.setText(content, true);

	        mailSender.send(message);
	        log.info("html邮件发送成功");
	    } catch (MessagingException e) {
	        log.error("发送html邮件时发生异常！", e);
	    }
	}


	
	/**
	 * 发送带附件的邮件
	 * @param to      对方账户
	 * @param subject 主题
	 * @param content 内容
	 * @param filePath 文件路径
	 */
	@Override
	public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
		 MimeMessage message = mailSender.createMimeMessage();

		    try {
		        MimeMessageHelper helper = new MimeMessageHelper(message, true);
		        helper.setFrom(from);
		        helper.setTo(to);
		        helper.setSubject(subject);
		        helper.setText(content, true);

		        FileSystemResource file = new FileSystemResource(new File(filePath));
		        String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
		        helper.addAttachment(fileName, file);

		        mailSender.send(message);
		        log.info("带附件的邮件已经发送。");
		    } catch (MessagingException e) {
		        log.error("发送带附件的邮件时发生异常！", e);
		    }
	}


	
	/**
	 * 发送带静态资源的邮件
	 * 邮件中的静态资源一般就是指图片
	 * @param to
	 * @param subject
	 * @param content
	 * @param rscPath
	 * @param rscId
	 */
	@Override
	public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId) {
		MimeMessage message = mailSender.createMimeMessage();

	    try {
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        helper.setFrom(from);
	        helper.setTo(to);
	        helper.setSubject(subject);
	        helper.setText(content, true);

	        FileSystemResource res = new FileSystemResource(new File(rscPath));
	        helper.addInline(rscId, res);

	        mailSender.send(message);
	        log.info("嵌入静态资源的邮件已经发送。");
	    } catch (MessagingException e) {
	        log.error("发送嵌入静态资源的邮件时发生异常！", e);
	    }
	}

}
