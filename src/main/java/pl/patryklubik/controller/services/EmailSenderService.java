package pl.patryklubik.controller.services;

import pl.patryklubik.controller.EmailSendingResult;
import pl.patryklubik.model.EmailAccount;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.io.File;
import java.util.List;

/**
 * Create by Patryk Łubik on 10.01.2021.
 */

public class EmailSenderService extends Service<EmailSendingResult> {

    private EmailAccount emailAccount;
    private String subject;
    private String recipient;
    private String content;
    private List<File> attachments;

    public EmailSenderService(EmailAccount emailAccount, String subject, String recipient, String content, List<File> attachments) {
        this.emailAccount = emailAccount;
        this.subject = subject;
        this.recipient = recipient;
        this.content = content;
        this.attachments = attachments;
    }

    @Override
    protected Task<EmailSendingResult> createTask() {
        return new Task<EmailSendingResult>() {
            @Override
                protected EmailSendingResult call () {
                    try {
                        MimeMessage mimeMessage = new MimeMessage(emailAccount.getSession());
                        Multipart multipart = new MimeMultipart();
                        setBasicSettings(mimeMessage);
                        setMessageContent(mimeMessage, multipart);
                        addAttachments(multipart);
                        transportMessage(mimeMessage);

                        return EmailSendingResult.SUCCESS;
                    } catch (MessagingException e) {
                        e.printStackTrace();
                        return EmailSendingResult.FAILED_BY_PROVIDER;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return EmailSendingResult.FAILED_BY_UNEXPECTED_ERROR;
                    }
                }
        };
    }

    private void setBasicSettings(MimeMessage mimeMessage) throws MessagingException {
        mimeMessage.setFrom(emailAccount.getAddress());
        mimeMessage.addRecipients(Message.RecipientType.TO, recipient);
        mimeMessage.setSubject(subject);
    }

    private void setMessageContent(MimeMessage mimeMessage, Multipart multipart) throws MessagingException {
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(content, "text/html");
        multipart.addBodyPart(messageBodyPart);
        mimeMessage.setContent(multipart);
    }

    private void addAttachments(Multipart multipart) throws MessagingException {
        if(attachments.size()>0){
            for ( File file: attachments){
                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(file.getAbsolutePath());
                mimeBodyPart.setDataHandler(new DataHandler(source));
                mimeBodyPart.setFileName(file.getName());
                multipart.addBodyPart(mimeBodyPart);
            }
        }
    }

    private void transportMessage(MimeMessage mimeMessage) throws MessagingException {
        Transport transport = emailAccount.getSession().getTransport();
        transport.connect(
                emailAccount.getProperties().getProperty("outgoingHost"),
                emailAccount.getAddress(),
                emailAccount.getPassword()
        );
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();
    }
}
