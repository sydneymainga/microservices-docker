package com.sydney.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sydney.model.Notification;
import com.sydney.repository.NotificationRepository;
import com.sydney.request.NotificationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void send(NotificationRequest notificationRequest) {

        notificationRepository.save(
                Notification.builder()
                        .toCustomerId(notificationRequest.getToCustomerId())
                        .toCustomerEmail(notificationRequest.getToCustomerName())
                        .toCustomerEmail(notificationRequest.getToCustomerEmail())
                        .sender("MagicianSydney")
                        .message(notificationRequest.getMessage())
                        .sentAt(LocalDateTime.now())
                        .build()
        );

        log.info("sending notification to customer {} with sendgrid",notificationRequest.getToCustomerId());
        Email from = new Email("sydneymainga6@gmail.com");//MY_EMAIL_GMAIL
        String subject = "SUCCESSFULLY REGISTERED";//notificationRequest.title;
        Email to = new Email(notificationRequest.getToCustomerEmail());
        Content content = new Content("text/plain",notificationRequest.getMessage());
        log.info("We are trying to send this email ==> "+notificationRequest.getMessage());
        Mail mail = new Mail(from,subject,to,content);

        SendGrid sendGrid = new SendGrid("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");

        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendGrid.api(request);

            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());

            log.info("status-code==>" + response.getStatusCode());
            log.info("body==>" + response.getBody());
            log.info("header==>" + response.getHeaders());




        }catch (Exception ex){
            log.warn("we are unable to send mails " + ex.getMessage());
            System.out.println("we are unable to send mails " + ex.getMessage());

        }
    }
}
