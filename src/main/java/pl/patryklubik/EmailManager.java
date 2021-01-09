package pl.patryklubik;

import javafx.scene.control.TreeItem;
import pl.patryklubik.controller.services.FetchFoldersService;
import pl.patryklubik.controller.services.FolderUpdaterService;
import pl.patryklubik.model.EmailAccount;
import pl.patryklubik.model.EmailTreeItem;

import javax.mail.Folder;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by Patryk Łubik on 04.01.2021.
 */
public class EmailManager {

    //Folder handling:
    private TreeItem<String> foldersRoot = new TreeItem<String>("");
    private EmailTreeItem<String> selectedFolder;
    private FolderUpdaterService folderUpdaterService;
    private List<Folder> folderList = new ArrayList<Folder>();

    public TreeItem<String> getFoldersRoot(){
        return foldersRoot;
    }

    public  List<Folder> getFolderList(){
        return this.folderList;
    }

    public EmailManager(){
        folderUpdaterService = new FolderUpdaterService(folderList);
        folderUpdaterService.start();
    }

    public void setSelectedFolder(EmailTreeItem<String> selectedFolder) {
        this.selectedFolder = selectedFolder;
    }

    public void addEmailAccount(EmailAccount emailAccount){
        EmailTreeItem<String> treeItem = new EmailTreeItem<String>(emailAccount.getAddress());
        FetchFoldersService fetchFoldersService = new FetchFoldersService(emailAccount.getStore(), treeItem, folderList);
        fetchFoldersService.start();
        foldersRoot.getChildren().add(treeItem);
    }

}
