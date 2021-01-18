package pl.patryklubik.controller.services;

import pl.patryklubik.model.EmailTreeItem;

import javax.mail.Folder;
import javax.mail.Store;
import java.util.List;

/**
 * Create by Patryk Łubik on 17.01.2021.
 */

public class ServiceManager {

    public void submitFetchFoldersJob(Store store, EmailTreeItem<String> emailTreeItem, List<Folder> folderList){
        FetchFoldersService fetchFoldersService = new FetchFoldersService(store, emailTreeItem, folderList);
        fetchFoldersService.start();
    }
}
