package pl.patryklubik.controller;

import pl.patryklubik.EmailManager;
import pl.patryklubik.controller.services.LoginService;
import pl.patryklubik.model.EmailAccount;
import pl.patryklubik.view.ViewFactory;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginWindowController extends BaseController implements Initializable {

    private LoginService loginService;

    @FXML
    private TextField emailAddressField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    public LoginWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
        this.loginService = new LoginService (null,emailManager);
    }

    public LoginWindowController(EmailManager emailManager,ViewFactory viewFactory, String fxmlName,
                                 TextField emailAddressField, PasswordField passwordField, Label errorLabel, LoginService loginService ) {
        super(emailManager, viewFactory, fxmlName);
        this.emailAddressField = emailAddressField;
        this.passwordField = passwordField;
        this.errorLabel = errorLabel;
        this.loginService = loginService;
    }

    @FXML
    void loginButtonAction() {
        if (fieldsAreValid()) {

            EmailAccount emailAccount = new EmailAccount(emailAddressField.getText(), passwordField.getText());
            loginService.setEmailAccount(emailAccount);
            loginService.start();

            loginService.setOnSucceeded(event -> {
                EmailLoginResult emailLoginResult = loginService.getValue();
                reactToLoginResult(emailLoginResult);
            });
        }
    }

    private void reactToLoginResult(EmailLoginResult emailLoginResult) {
        switch (emailLoginResult) {
            case SUCCESS:
                if(!viewFactory.isMainViewInitialized()){
                    viewFactory.showMainWindow();
                }
                Stage stage = (Stage) errorLabel.getScene().getWindow();
                viewFactory.closeStage(stage);
                return;
            case FAILED_BY_CREDENTIALS:
                errorLabel.setText("INVALID CREDENTIALS");
                return;
            case FAILED_BY_UNEXPECTED_ERROR:
                errorLabel.setText("UNEXPECTED ERROR");
                return;
            default:
                return;
        }
    }

    private boolean fieldsAreValid() {
        if(emailAddressField.getText().isEmpty()) {
            errorLabel.setText("PLEASE FILL EMAIL");
            return false;
        }
        if(passwordField.getText().isEmpty()) {
            errorLabel.setText("PLEASE FILL PASSWORD");
            return false;
        }

        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        emailAddressField.setText("");
        passwordField.setText("");
    }
}
