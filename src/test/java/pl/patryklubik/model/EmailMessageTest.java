package pl.patryklubik.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.MockitoAnnotations.openMocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.mail.Message;

import java.util.Date;

/**
 * Create by Patryk Łubik on 16.01.2021.
 */

public class EmailMessageTest {

    private EmailMessage emailMessage;
    private String sender = "some@sender.com";
    private String subject = "some Subject";
    private String recipient = "some@recipient.com";
    private int size = 1000;
    @Mock
    private Date date;
    private boolean isRead = true;
    @Mock
    private Message messageMock;


    @BeforeEach
    public void setUp(){
        openMocks(this);
        emailMessage = new EmailMessage(
                subject,
                sender,
                recipient,
                size,
                date,
                isRead,
                messageMock
        );
    }

    @Test
    public void testFields(){
        assertEquals(emailMessage.getSender(), sender);
        assertEquals(emailMessage.getSubject(), subject);
        assertEquals(emailMessage.getRecipient(), recipient);
        assertEquals(emailMessage.getSize().toString(), new SizeInteger(size).toString());
        assertEquals(emailMessage.getDate(), date);
        assertEquals(emailMessage.isRead(), isRead);
        assertEquals(emailMessage.getMessage(), messageMock);
    }
    @Test
    public void testSetRead(){
        emailMessage.setRead(false);
        assertEquals(emailMessage.isRead(), false);
    }
}
