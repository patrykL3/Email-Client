package pl.patryklubik;

import pl.patryklubik.controller.presistance.PersistenceAccess;
import pl.patryklubik.controller.presistance.ValidAccount;
import pl.patryklubik.controller.services.LoginService;
import pl.patryklubik.model.EmailAccount;
import pl.patryklubik.view.ViewFactory;

import java.util.List;

/**
 * Create by Patryk Łubik on 17.01.2021.
 */

public class ProgramState {

    private PersistenceAccess persistenceAccess;
    private ViewFactory viewFactory;

    public ProgramState(PersistenceAccess persistenceAccess, ViewFactory viewFactory) {
        this.persistenceAccess = persistenceAccess;
        this.viewFactory = viewFactory;
    }

    public ProgramState() {
        this(new PersistenceAccess(), new ViewFactory(new EmailManager()));
    }

    public void init() {
        this.checkPersistence();
    }

    private void checkPersistence() {
        if (this.persistenceAccess.loadFromPersistence() != null) {
            List<ValidAccount> validAccountList = persistenceAccess.loadFromPersistence();

            for (ValidAccount validAccount: validAccountList){
                EmailAccount emailAccount = new EmailAccount(validAccount.getAddress(), validAccount.getPassword());
                LoginService loginService = new LoginService(emailAccount, viewFactory.getEmailManager());
                loginService.start();
            }
            showMainScreen();
        } else {
            showLoginWindow();
        }
    }

    private void showLoginWindow() {
        this.viewFactory.showLoginWindow();
    }

    private void showMainScreen() {
        this.viewFactory.showMainWindow();
    }

    public void saveAccountsToPersistence() {
        persistenceAccess.saveToPersistence((viewFactory.getEmailManager()).getEmailAccounts());
    }
}
