package com.dodream.core.util.email;

import com.dodream.core.exception.GlobalErrorCode;
import com.dodream.core.util.email.dto.VerificationEmailTemplate;
import com.dodream.core.util.email.factory.VerificationEmailFactory;
import com.dodream.core.util.email.value.VerificationType;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Component
@RequiredArgsConstructor
@Log4j2
public class EmailUtil {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final VerificationEmailFactory factory;

    @Async
    public void sendVerificationEmail(String to, VerificationType type, String code) {

        VerificationEmailTemplate template = factory.createTemplate(type, code);

        Context context = new Context();
        context.setVariable("code", template.getCode());

        String html = templateEngine.process("email/verification-code", context);

        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false);

            helper.setTo(to);
            helper.setFrom("teamdodream.dev@gmail.com", "두드림 관리자");
            helper.setSubject(template.getTitle());
            helper.setText(html, true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw GlobalErrorCode.CANNOT_SEND_MAIL.toException();
        }
    }
}
