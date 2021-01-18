package pl.patryklubik;

import pl.patryklubik.controller.services.FolderUpdaterService;
import pl.patryklubik.controller.services.ServiceManager;
import pl.patryklubik.model.EmailAccount;
import pl.patryklubik.model.EmailMessage;
import pl.patryklubik.model.EmailTreeItem;
import pl.patryklubik.view.IconResolver;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javax.mail.Flags;
import javax.mail.Folder;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by Patryk Łubik on 04.01.2021.
 */
public class EmailManager {

    private ServiceManager serviceManager;
    private TreeItem<String> foldersRoot = new TreeItem<String>("");
    private EmailTreeItem<String> selectedFolder;
    private FolderUpdaterService folderUpdaterService;
    private List<Folder> folderList = new ArrayList<Folder>();
    private EmailMessage selectedMessage;
    private ObservableList<EmailAccount> emailAccounts = FXCollections.observableArrayList();
    private IconResolver iconResolver = new IconResolver();

    public EmailManager(){
        this(new ServiceManager());
        folderUpdaterService = new FolderUpdaterService(folderList);
        folderUpdaterService.start();
    }

    public EmailManager(ServiceManager serviceManager){
        this.serviceManager = serviceManager;
    }

    public  ObservableList<EmailAccount> getEmailAccounts(){
        return  emailAccounts;
    }

    public EmailMessage getSelectedMessage() {
        return selectedMessage;
    }

    public void setSelectedMessage(EmailMessage selectedMessage) {
        this.selectedMessage = selectedMessage;
    }

    public EmailTreeItem<String> getSelectedFolder() {
        return selectedFolder;
    }

    public void setSelectedFolder(EmailTreeItem<String> selectedFolder) {
        this.selectedFolder = selectedFolder;
    }

    public TreeItem<String> getFoldersRoot(){
        return foldersRoot;
    }

    public  List<Folder> getFolderList(){
        return this.folderList;
    }

    public void setRead() {
        try {
            selectedMessage.setRead(true);
            selectedMessage.getMessage().setFlag(Flags.Flag.SEEN, true);
            selectedFolder.decrementMessagesCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUnRead() {
        try {
            selectedMessage.setRead(false);
            selectedMessage.getMessage().setFlag(Flags.Flag.SEEN, false);
            selectedFolder.incrementMessagesCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteSelectedMessage() {
        try {
            selectedMessage.getMessage().setFlag(Flags.Flag.DELETED, true);
            selectedFolder.getEmailMessages().remove(selectedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addEmailAccount(EmailAccount emailAccount){
        emailAccounts.add(emailAccount);
        EmailTreeItem<String> emailTreeItem = new EmailTreeItem<String>(emailAccount.getAddress());
        emailTreeItem.setGraphic(iconResolver.getIconForFolder(emailAccount.getAddress()));
        emailTreeItem.setExpanded(true);
        foldersRoot.getChildren().add(emailTreeItem);
        serviceManager.submitFetchFoldersJob(emailAccount.getStore(), emailTreeItem, folderList);
    }

}
