package pl.patryklubik.controller;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.mockito.Mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import pl.patryklubik.EmailManager;
import pl.patryklubik.controller.services.LoginService;
import pl.patryklubik.view.ViewFactory;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Create by Patryk Łubik on 16.01.2021.
 */

public class LoginWindowControllerTest {

    @Mock
    private ViewFactory viewFactoryMock;
    @Mock
    private EmailManager emailManagerMock;

    private TextField emailAddressField;

    private PasswordField passwordField;

    private Label errorLabel;

    private LoginWindowController loginWindowController;

    @Mock
    private LoginService loginServiceMock;

    @BeforeAll
    public static void setUpToolkit(){
        Platform.startup(() -> System.out.println("Toolkit initialized ..."));
    }

    @BeforeEach
    public void setUp(){
        openMocks(this);
        emailAddressField = new TextField();
        passwordField = new PasswordField();
        errorLabel = new Label();

        loginWindowController = new LoginWindowController(
                emailManagerMock,
                viewFactoryMock,
                null,
                emailAddressField,
                passwordField,
                errorLabel,
                loginServiceMock
        );
    }

    @Test
    public void testFieldsValidation(){
        loginWindowController.loginButtonAction();
        assertEquals(errorLabel.getText(),"PLEASE FILL EMAIL");
        emailAddressField.setText("some@address.com");
        loginWindowController.loginButtonAction();
        assertEquals(errorLabel.getText(),"PLEASE FILL PASSWORD");
    }

    @Test
    public void testLoginAction(){
            emailAddressField.setText("some@address.com");
            passwordField.setText("password");
        Platform.runLater(()->{
            loginWindowController.loginButtonAction();
            verify(loginServiceMock).setEmailAccount(any());
            verify(loginServiceMock).start();
        });
    }

}
