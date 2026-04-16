package web.employee.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {
	@Autowired
	private JavaMailSender mailSender;

	public void sendPasswordEmail(String email, String password) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("登入密碼通知");
		String frontUrl = "http://127.0.0.1/attendance-system/rci-login-front.html";
		message.setText("請用以上密碼" + password + "於" + frontUrl + "登入您的帳號");

		mailSender.send(message);
	}
}
