package core.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource("classpath:mail.properties")
public class MailConfig {
	@Value("${mail.host}")
	private String host;
	
	@Value("${mail.port}")
	private int port;
	
	@Value("${mail.username}")
	private String username;
	
	@Value("${mail.password}")
	private String password;
	
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(host);
		mailSender.setPort(port);
		mailSender.setUsername(username);
		mailSender.setPassword(password);
		
		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");  // 指定使用 SMTP 來發送郵件
		props.put("mail.smtp.auth", true);  // 寄信時需要進行使用者帳號密碼的驗證
		props.put("mail.smtp.starttls.enable", true);  // 啟用安全加密，Gmail 要求必須使用加密連線，否則會拒絕連線
		props.put("mail.debug", true);  // 在你的控制台（Console）輸出詳細的通訊過程
		props.put("mail.smtp.connectiontimeou", "5000");  // 建立連線超時5秒
		props.put("mail.smtp.timeout", "5000");  // 讀取資料超時5秒
		props.put("mail.smtp.writetimeout", "5000");  // 寫入資料超時5秒
		return mailSender;
	}
}
