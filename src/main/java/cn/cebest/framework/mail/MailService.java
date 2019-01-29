package cn.cebest.framework.mail;

/**
 *  发送邮件接口
  * @author maming  
  * @date 2018年8月28日
 */
public interface MailService {
	
	/**
	 * 发送文本邮件
	 * @param to      对方账户
	 * @param subject 主题
	 * @param content 内容
	 */
	public void sendSimpleMail(String to, String subject, String content);
	
	
	/**
	 * 发送html格式邮件
	 * @param to      对方账户
	 * @param subject 主题
	 * @param content 内容
	 */
	public void sendHtmlMail(String to, String subject, String content);
	
	
	/**
	 * 发送带附件的邮件
	 * @param to      对方账户
	 * @param subject 主题
	 * @param content 内容
	 * @param filePath 文件路径
	 */
	public void sendAttachmentsMail(String to, String subject, String content, String filePath);
	
	
	/**
	 * 发送带静态资源的邮件
	 * 邮件中的静态资源一般就是指图片
	 * @param to
	 * @param subject
	 * @param content
	 * @param rscPath
	 * @param rscId
	 */
	public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId);

}
