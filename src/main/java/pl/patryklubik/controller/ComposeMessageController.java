package pl.patryklubik.controller;

import javafx.scene.control.PasswordField;
import pl.patryklubik.EmailManager;
import pl.patryklubik.controller.services.EmailSenderService;
import pl.patryklubik.controller.services.LoginService;
import pl.patryklubik.model.EmailAccount;
import pl.patryklubik.view.ViewFactory;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Create by Patryk Łubik on 10.01.2021.
 */


public class ComposeMessageController extends BaseController implements Initializable {

    private List<File> attachments = new ArrayList<File>();

    @FXML
    private TextField recipientTextField;

    @FXML
    private TextField subjectTextField;

    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private Label errorLabel;

    @FXML
    private Label attachLabel;

    @FXML
    private ChoiceBox<EmailAccount> emailAccountChoice;

    @FXML
    void sendButtonAction() {
        EmailSenderService emailSenderService = new EmailSenderService(
                emailAccountChoice.getValue(),
                subjectTextField.getText(),
                recipientTextField.getText(),
                htmlEditor.getHtmlText(),
                attachments
        );
        emailSenderService.start();
        emailSenderService.setOnSucceeded(e->{
            EmailSendingResult emailSendingResult = emailSenderService.getValue();
            reactToResultEmailSending(emailSendingResult);
        });
    }

    private void reactToResultEmailSending(EmailSendingResult emailSendingResult){
        switch (emailSendingResult) {
            case SUCCESS:
                Stage stage = (Stage) recipientTextField.getScene().getWindow();
                viewFactory.closeStage(stage);
                break;
            case FAILED_BY_PROVIDER:
                errorLabel.setText("Provider error!");
                break;
            case FAILED_BY_UNEXPECTED_ERROR:
                errorLabel.setText("Unexpected error!");
                break;
        }
    }

    @FXML
    void attachBtnAction() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null){
            attachments.add(selectedFile);
        }
        setAttachLabelText(selectedFile.getName());
    }

    public ComposeMessageController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    private void setAttachLabelText(String selectedFileName) {
        attachLabel.setText(attachLabel.getText() + selectedFileName + "      ");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        emailAccountChoice.setItems(emailManager.getEmailAccounts());
        emailAccountChoice.setValue(emailManager.getEmailAccounts().get(0));
    }

}
