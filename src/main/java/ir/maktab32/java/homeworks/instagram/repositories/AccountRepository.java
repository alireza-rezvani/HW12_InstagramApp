package ir.maktab32.java.homeworks.instagram.repositories;

import ir.maktab32.java.homeworks.instagram.config.hibernate.repositories.CrudRepository;
import ir.maktab32.java.homeworks.instagram.entities.Account;

public class AccountRepository extends CrudRepository<Account, Long> {
    @Override
    protected Class<Account> getEntityClass() {
        return Account.class;
    }

    private static AccountRepository accountRepository;
    public static AccountRepository getInstance(){
        if (accountRepository == null)
            accountRepository = new AccountRepository();
        return accountRepository;
    }

    private AccountRepository(){}
}
