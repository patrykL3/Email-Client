package pl.patryklubik;

import pl.patryklubik.controller.presistance.PersistenceAccess;
import pl.patryklubik.controller.presistance.ValidAccount;
import pl.patryklubik.controller.services.LoginService;
import pl.patryklubik.model.EmailAccount;
import pl.patryklubik.view.ViewFactory;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * JavaFX App
 */
public class Launcher extends Application {

    private PersistenceAccess persistenceAccess = new PersistenceAccess();
    private EmailManager emailManager = new EmailManager();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        ViewFactory viewFactory = new ViewFactory(emailManager);
        List<ValidAccount> validAccountList = persistenceAccess.loadFromPersistence();
        if(validAccountList.size() > 0) {
            viewFactory.showMainWindow();
            for (ValidAccount validAccount: validAccountList){
                EmailAccount emailAccount = new EmailAccount(validAccount.getAddress(), validAccount.getPassword());
                LoginService loginService = new LoginService(emailAccount, emailManager);
                loginService.start();
            }
        } else {
            viewFactory.showLoginWindow();
        }
    }

    @Override
    public void stop() throws Exception {
        List<ValidAccount> validAccountList = new ArrayList<ValidAccount>();
        for (EmailAccount emailAccount: emailManager.getEmailAccounts()){
            validAccountList.add(new ValidAccount(emailAccount.getAddress(), emailAccount.getPassword()));
        }
        persistenceAccess.saveToPersistence(validAccountList);
    }

}