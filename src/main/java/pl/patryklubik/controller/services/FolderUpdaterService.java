package pl.patryklubik.controller.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javax.mail.Folder;
import javax.mail.MessagingException;

import java.util.List;

/**
 * Create by Patryk Łubik on 09.01.2021.
 */


public class FolderUpdaterService extends Service {

    private List<Folder> folderList;

    public FolderUpdaterService(List<Folder> folderList) {
        this.folderList = folderList;
    }


    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                for (;;){
                    updateAllMessages();
                }
            }
        };
    }

    private void updateAllMessages(){
        try {
            Thread.sleep(5000);
            for (Folder folder: folderList){
                synchronized (folderList){
                    updateMessagesFromFolder(folder);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void updateMessagesFromFolder(Folder folder) throws MessagingException {
        if (folder.getType() != Folder.HOLDS_FOLDERS && folder.isOpen()) {
            folder.getMessageCount();
        }
    }
}
