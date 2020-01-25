package ir.maktab32.java.homeworks.instagram.repositories;

import ir.maktab32.java.homeworks.instagram.config.hibernate.HibernateUtil;
import ir.maktab32.java.homeworks.instagram.config.hibernate.repositories.CrudRepository;
import ir.maktab32.java.homeworks.instagram.entities.Account;
import org.hibernate.Session;

import java.util.List;

public class AccountRepository extends CrudRepository<Account, Long> {
    @Override
    protected Class<Account> getEntityClass() {
        return Account.class;
    }

    @Override
    protected Session getSession() {
        return HibernateUtil.getSession();
    }

    private static AccountRepository accountRepository;
    public static AccountRepository getInstance(){
        if (accountRepository == null)
            accountRepository = new AccountRepository();
        return accountRepository;
    }

    private AccountRepository(){}


    public boolean isUsernameExisting(String username){
        boolean result = false;
        List<Account> allAccounts = findAll();
        if (allAccounts != null && !allAccounts.isEmpty()){
            for (Account i : allAccounts) {
                if (i.getUsername().equals(username)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    public Account findByUsername(String username){
        Account result = null;
        List<Account> allAccounts = findAll();
        if (allAccounts != null && !allAccounts.isEmpty()){
            for (Account i : allAccounts) {
                if (i.getUsername().equals(username)) {
                    result = i;
                    break;
                }
            }
        }
        return result;
    }
}
