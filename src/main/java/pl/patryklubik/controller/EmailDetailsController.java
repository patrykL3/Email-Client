package pl.patryklubik.controller;

import pl.patryklubik.EmailManager;
import pl.patryklubik.controller.services.MessageRendererService;
import pl.patryklubik.model.EmailMessage;
import pl.patryklubik.view.ViewFactory;

import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;
import javafx.concurrent.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;

import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Create by Patryk Łubik on 10.01.2021.
 */

public class EmailDetailsController extends BaseController implements Initializable
{
    private String LOCATION_OF_DOWNLOADS = System.getProperty("user.home") + "/Downloads/";

    @FXML
    private WebView webView;

    @FXML
    private Label attachmentLabel;

    @FXML
    private Label subjectLabel;

    @FXML
    private Label senderLabel;

    @FXML
    private HBox hBoxDownloads;

    public EmailDetailsController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        EmailMessage emailMessage = emailManager.getSelectedMessage();
        subjectLabel.setText(emailMessage.getSubject());
        senderLabel.setText(emailMessage.getSender());
        loadAttachments(emailMessage);
        MessageRendererService messageRendererService = new MessageRendererService(webView.getEngine());
        messageRendererService.setEmailMessage(emailMessage);
        messageRendererService.restart();
    }

    private void loadAttachments(EmailMessage emailMessage){
        if (emailMessage.hasAttachments()){
            for (MimeBodyPart mimeBodyPart: emailMessage.getAttachmentList()){
                createAttachmentButton(mimeBodyPart);
            }
        } else {
            attachmentLabel.setText("");
        }
    }

    private void createAttachmentButton(MimeBodyPart mimeBodyPart) {
        try {
            AttachmentButton button = new AttachmentButton(mimeBodyPart);
            hBoxDownloads.getChildren().add(button);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class AttachmentButton extends Button {

        private MimeBodyPart mimeBodyPart;
        private String downloadedFilePath;

        public  AttachmentButton (MimeBodyPart mimeBodyPart) throws MessagingException {
            this.mimeBodyPart = mimeBodyPart;
            this.setText(mimeBodyPart.getFileName());
            this.downloadedFilePath = LOCATION_OF_DOWNLOADS + mimeBodyPart.getFileName();

            this.setOnAction( e -> downloadAttachment());
        }

        private void downloadAttachment(){
            colorBlue();
            Service service = new Service() {
                @Override
                protected Task createTask() {
                    return new Task() {
                        @Override
                        protected Object call() throws Exception {
                            mimeBodyPart.saveFile(downloadedFilePath);
                            return null;
                        }
                    };
                }
            };
            service.restart();
            service.setOnSucceeded(e ->{
                colorGreen();
                this.setOnAction( eventOpenFile->{
                    File file = new File(downloadedFilePath);
                    Desktop desktop = Desktop.getDesktop();
                    if(file.exists()){
                        openFile(file, desktop);
                    }
                });
            });
        }

        private void openFile(File fileToOpen, Desktop desktop) {
            try {
                desktop.open(fileToOpen);
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }

        private void colorBlue(){
            this.setStyle("-fx-background-color: Blue");
        }
        private void colorGreen(){
            this.setStyle("-fx-background-color: Green");
        }
    }

}
