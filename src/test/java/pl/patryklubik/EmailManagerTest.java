package pl.patryklubik;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

import pl.patryklubik.controller.services.ServiceManager;
import pl.patryklubik.model.EmailAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.mail.Store;

/**
 * Create by Patryk Łubik on 17.01.2021.
 */

public class EmailManagerTest {

    private EmailManager emailManager;

    @Mock
    private ServiceManager serviceManagerMock;
    @Mock
    private EmailAccount emailAccountMock;
    @Mock
    private Store storeMock;

    @BeforeEach
    public void setUp(){
        openMocks(this);
        emailManager = new EmailManager(serviceManagerMock);
    }


    @Test
    public void testFoldersRoot(){
        assertTrue(emailManager.getFoldersRoot().getValue().isEmpty());
    }

    @Test
    public void testAddEmailAccount(){
        when(emailAccountMock.getAddress()).thenReturn("some@address.com");
        when(emailAccountMock.getStore()).thenReturn(storeMock);
        emailManager.addEmailAccount(emailAccountMock);
        assertTrue(emailManager.getFoldersRoot().getChildren().get(0).getValue() == "some@address.com");
        verify(serviceManagerMock).submitFetchFoldersJob(eq(storeMock), any(), any());
    }
}
