package com.dodream.core.util;

import com.dodream.core.exception.GlobalErrorCode;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
public class EmailUtil {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public void sendMessage(String to, String subject, String content) {
        try {
            Context context = new Context();
            context.setVariable("content", content);

            String htmlContent = templateEngine.process("email-template", context);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            helper.setFrom("teamdodream.dev@gmail.com");

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw GlobalErrorCode.CANNOT_SEND_MAIL.toException();
        }
    }
}
