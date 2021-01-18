package pl.patryklubik.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.util.Date;

/**
 * Create by Patryk Łubik on 16.01.2021.
 */

public class EmailTreeItemTest {

    private EmailTreeItem<String> emailTreeItem;
    private int sizeToTest = 1000;
    @Mock
    private Message messageMock;
    @Mock
    private Flags flagsMock;
    @Mock
    private Address fromAddressMock;
    @Mock
    private Address toAddressMock;
    @Mock
    private Date sendDateMock;

    @BeforeEach
    public void setUp() throws MessagingException {
        openMocks(this);
        emailTreeItem = new EmailTreeItem<String>("Inbox");
        when(messageMock.getFlags()).thenReturn(flagsMock);
        when(flagsMock.contains(Flags.Flag.SEEN)).thenReturn(true);

        when(messageMock.getSubject()).thenReturn("someSubject");

        when(fromAddressMock.toString()).thenReturn("some@from.com");
        Address[] fromAddressArray = {fromAddressMock};
        when(messageMock.getFrom()).thenReturn(fromAddressArray);

        when(toAddressMock.toString()).thenReturn("some@recipient.com");
        Address[] toAddressArray = {toAddressMock};
        when(messageMock.getRecipients(MimeMessage.RecipientType.TO)).thenReturn(toAddressArray);

        when(messageMock.getSize()).thenReturn(sizeToTest);
        when(messageMock.getSentDate()).thenReturn(sendDateMock);
    }

    @Test
    public void testAddEmail() throws MessagingException {
        emailTreeItem.addEmail(messageMock);
        EmailMessage emailMessage = emailTreeItem.getEmailMessages().get(0);
        assertEquals(emailMessage.getSubject(), "someSubject");
        assertEquals(emailMessage.getSender(), "some@from.com");
        assertEquals(emailMessage.getRecipient(), "some@recipient.com");
        assertEquals(emailMessage.getSize().toString(), new SizeInteger(sizeToTest).toString());
        assertEquals(emailMessage.getDate(), sendDateMock);
        assertEquals(emailMessage.getMessage(), messageMock);
        assertEquals(emailTreeItem.getEmailMessages().size(), 1);
        assertEquals(emailTreeItem.getValue(), "Inbox");
    }

    @Test
    public void testAddUnreadEmail() throws MessagingException {
        when(flagsMock.contains(Flags.Flag.SEEN)).thenReturn(false);
        emailTreeItem.addEmail(messageMock);
        EmailMessage emailMessage = emailTreeItem.getEmailMessages().get(0);
        assertEquals(emailMessage.isRead(), false);

        assertEquals(emailTreeItem.getValue(), "Inbox(1)");
        emailTreeItem.decrementMessagesCount();
        assertEquals(emailTreeItem.getValue(), "Inbox");
    }
}
