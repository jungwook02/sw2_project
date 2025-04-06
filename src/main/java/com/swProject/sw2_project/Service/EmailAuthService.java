package com.swProject.sw2_project.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailAuthService {

    @Autowired
    private JavaMailSender emailSender;

    public String sendEmail(String userEmail) {
        Random random = new Random();
        // 100000 ~ 999999 범위의 6자리 숫자 생성
        int authNumber = 100000 + random.nextInt(900000);

        // HTML 이메일 본문
        String htmlMsg = "<html><body>"
                + "<h2>안녕하세요,</h2>"
                + "<p>SW2 프로젝트 회원가입을 위한 인증코드입니다.</p>"
                + "<div style='border:1px solid #000; padding:10px; text-align:center;'>"
                + "<strong style='font-size:1.5em;'>"
                + authNumber
                + "</strong></div>"
                + "<br><p>위 인증코드를 회원가입 페이지에 입력해주세요.</p>"
                + "</body></html>";
        try {
            // MimeMessage 객체 생성
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setSubject("SW2 프로젝트 회원가입 인증 코드");
            helper.setFrom("parkwooga@gmail.com"); // 발신자 이메일
            helper.setTo(userEmail); // 받는 이메일 주소

            helper.setText(htmlMsg, true);  // HTML 이메일 (true는 HTML을 의미)

            emailSender.send(message);
            log.info("회원가입 이메일 인증코드 -> userEmail:  " + userEmail + " 인증번호: " + authNumber);
            return "전송 성공!";

        } catch (MailException e) {
            e.printStackTrace();
            return "이메일 전송에 실패했습니다.";
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
