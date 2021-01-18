package pl.patryklubik.controller.presistance;

import pl.patryklubik.model.EmailAccount;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by Patryk Łubik on 10.01.2021.
 */

public class PersistenceAccess {

    private String VALID_ACCOUNTS_LOCATION = System.getProperty("user.home") + File.separator + "validAccounts.ser";
    private Encoder encoder = new Encoder();

    public List<ValidAccount> loadFromPersistence(){
        List<ValidAccount> resultList = new ArrayList<ValidAccount>();
        try {
            File fileWithAccountsData = new File(VALID_ACCOUNTS_LOCATION);

            if(fileWithAccountsData.exists()) {
                FileInputStream fileInputStream = new FileInputStream(VALID_ACCOUNTS_LOCATION);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                List<ValidAccount> persistedList = (List<ValidAccount>) objectInputStream.readObject();
                decodePasswords(persistedList);
                resultList.addAll(persistedList);
            } else {
                resultList = null;
            }

        } catch ( Exception e){
            e.printStackTrace();
        }
        return resultList;
    }


    public void saveToPersistence(List<EmailAccount> emailAccountsList){
        try {
            File file = new File(VALID_ACCOUNTS_LOCATION);
            List<ValidAccount> validAccounts = createValidAccountsFromEmailAccounts(emailAccountsList);
            if(!validAccounts.isEmpty()) {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

                encodePasswords(validAccounts);
                objectOutputStream.writeObject(validAccounts);
                objectOutputStream.close();
                fileOutputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<ValidAccount> createValidAccountsFromEmailAccounts(List<EmailAccount> emailAccountsList) {
        List<ValidAccount> validAccountList = new ArrayList<>();

        for (EmailAccount emailAccount: emailAccountsList){
            ValidAccount validAccount = new ValidAccount(emailAccount.getAddress(), emailAccount.getPassword());
            validAccountList.add(validAccount);
        }
        return validAccountList;

    }

    private void decodePasswords(List<ValidAccount> persistedList) {
        for (ValidAccount validAccount: persistedList){
            String originalPassword = validAccount.getPassword();
            validAccount.setPassword(encoder.decode(originalPassword));
        }
    }

    private void encodePasswords(List<ValidAccount> persistedList) {
        for (ValidAccount validAccount: persistedList){
            String originalPassword = validAccount.getPassword();
            validAccount.setPassword(encoder.encode(originalPassword));
        }
    }
}
