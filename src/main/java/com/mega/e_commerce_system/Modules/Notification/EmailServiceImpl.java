package com.mega.e_commerce_system.Modules.Notification;

import com.mega.e_commerce_system.Modules.product.Payload.ProductPurchaseResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine springTemplateEngine;

    @Override
    public void paymentSuccessfulEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference) throws MessagingException
    {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper messageHelper = new MimeMessageHelper(
                message,
                MimeMessageHelper.MULTIPART_MODE_RELATED,
                StandardCharsets.UTF_8.name()
        );
        messageHelper.setFrom("nilkanthmanish0@gmail.com");

        Map<String,Object> variable = new HashMap<>();
        variable.put("customerName",customerName);
        variable.put("totalAmount",amount);
        variable.put("orderReference",orderReference);

        Context context = new Context();
        context.setVariables(variable);
        final String templateName = EmailTemplates.PAYMENT_CONFIRMATION.getTemplate();
        messageHelper.setSubject(EmailTemplates.PAYMENT_CONFIRMATION.getSubject());

        try{
            String html = springTemplateEngine.process(templateName,context);
            messageHelper.setText(html,true);
            messageHelper.setTo(destinationEmail);
            javaMailSender.send(message);
            log.info(String.format("INFO: Email successfully sent to %s with template %s ",destinationEmail,templateName));
        }catch(Exception e)
        {
            log.warn("email cannot be sent using {}",destinationEmail);
        }

    }

    @Override
    public void orderConfirmationEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference,
            List<ProductPurchaseResponse> products) throws MessagingException
    {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_RELATED,
                StandardCharsets.UTF_8.name()
        );

        messageHelper.setFrom("nilkanthmanish0@gmail.com");
        final String templateName = EmailTemplates.ORDER_CONFIRMATION.getTemplate();

        Map<String,Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("totalAmount", amount);
        variables.put("orderReference", orderReference);
        variables.put("products",products);

        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(EmailTemplates.ORDER_CONFIRMATION.getSubject());

        try
        {
            String htmlTemplate = springTemplateEngine.process(templateName,context);
            messageHelper.setText(htmlTemplate,true);

            messageHelper.setTo(destinationEmail);
            javaMailSender.send(mimeMessage);
            log.info(String.format("INFO: Email successfully sent to %s with template %s ",destinationEmail,templateName));

        }catch (Exception e)
        {
            log.warn("WARNING:: Cannot send email to {}",destinationEmail);
        }

    }
}
