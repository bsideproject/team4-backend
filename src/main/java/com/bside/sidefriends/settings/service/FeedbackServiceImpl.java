package com.bside.sidefriends.settings.service;

import com.bside.sidefriends.settings.service.dto.CreateFeedbackRequestDto;
import com.bside.sidefriends.settings.service.dto.CreateFeedbackResponseDto;
import com.bside.sidefriends.users.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    @Value("${config.properties.FROM}")
    private String FROM;

    @Value("${config.properties.FROM-NAME}")
    private String FROM_NAME;

    @Value("${config.properties.TO}")
    private String TO;

    @Value("${config.properties.SMTP-USERNAME}")
    private String SMTP_USERNAME;

    @Value("${config.properties.SMTP-PASSWORD}")
    private String SMTP_PASSWORD;

    @Value("${config.properties.HOST}")
    private String HOST;

    @Value("${config.properties.PORT}")
    private int PORT;

    @Override
    public CreateFeedbackResponseDto createFeedback(User user, CreateFeedbackRequestDto createFeedbackRequestDto) {

        try {
            sendEmail(createFeedbackRequestDto.getContents());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new CreateFeedbackResponseDto(
                LocalDateTime.now()
        );
    }

    static final String SUBJECT = "펫하루 사용자 피드백";

    public void sendEmail(String contents) throws Exception {
        // Properties
        Properties props = System.getProperties();
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.user", FROM);

        // Session
        Session session = Session.getDefaultInstance(props);

        // Message
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM, FROM_NAME));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
        msg.setSubject(SUBJECT);
        msg.setContent(contents, "text/html;charset=utf-8");

        // Transport
        Transport transport = session.getTransport("smtp");
        try {
            transport.connect(SMTP_USERNAME, SMTP_PASSWORD);
            transport.sendMessage(msg, msg.getAllRecipients());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            transport.close();
        }
    }

}
