package pl.patryklubik.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import javax.mail.Store;
import java.util.Properties;

/**
 * Create by Patryk Łubik on 16.01.2021.
 */

public class EmailAccountTest {

    private EmailAccount emailAccount;
    private String someAddress = "some@address.com";
    private String somePassword = "somePassword";
    private Properties props;
    @Mock
    private Store storeMock;


    @BeforeEach
    public void setUp(){
        props = new Properties();
        emailAccount = new EmailAccount(someAddress, somePassword);
    }
    @Test
    public void testFields(){
        assertEquals(emailAccount.getAddress(), someAddress);
        assertEquals(emailAccount.getPassword(), somePassword);
    }

    @Test
    public void testProperties(){
        emailAccount.setProperties(props);
        assertEquals(emailAccount.getProperties(), props);
        emailAccount.setStore(storeMock);
        assertEquals(emailAccount.getStore(), storeMock);
    }
}
