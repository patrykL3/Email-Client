package pl.patryklubik;

import javafx.scene.control.TreeItem;
import pl.patryklubik.controller.services.FetchFoldersService;
import pl.patryklubik.model.EmailAccount;
import pl.patryklubik.model.EmailTreeItem;

/**
 * Create by Patryk Łubik on 04.01.2021.
 */
public class EmailManager {

    //Folder handling:
    private TreeItem<String> foldersRoot = new TreeItem<String>("");

    public TreeItem<String> getFoldersRoot(){
        return foldersRoot;
    }

    public void addEmailAccount(EmailAccount emailAccount){
        EmailTreeItem<String> treeItem = new EmailTreeItem<String>(emailAccount.getAddress());
        FetchFoldersService fetchFoldersService = new FetchFoldersService(emailAccount.getStore(), treeItem);
        fetchFoldersService.start();
        foldersRoot.getChildren().add(treeItem);
    }
}
