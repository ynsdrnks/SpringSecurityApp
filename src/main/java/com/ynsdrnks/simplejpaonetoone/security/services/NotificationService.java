package com.ynsdrnks.simplejpaonetoone.security.services;

import com.ynsdrnks.simplejpaonetoone.entity.Calisan;
import com.ynsdrnks.simplejpaonetoone.security.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private JavaMailSender javaMailSender;

    @Autowired
    public NotificationService(JavaMailSender javaMailSender){
        this.javaMailSender= javaMailSender;
    }

    public void sendNotification(Calisan calisan) throws MailException {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(calisan.getClsnEmail());
        mail.setFrom("yunusdurankus333333@gmail.com");
        mail.setSubject("Mesai Saatlerimiz Hk!");
        mail.setText("Mesai Saatlerimiz 9.00 18.00 olarak güncellenmiştir!");

        javaMailSender.send(mail);
    }

}
