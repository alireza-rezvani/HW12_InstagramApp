package ir.maktab32.java.homeworks.instagram.features.dao;

import ir.maktab32.java.homeworks.instagram.entities.Account;

import java.util.List;

public interface AccountDao {
    Account add(String username, String password, String screenName, String accountImg);
    Account edit(String username, String password, String screenName, String accountImg);
    Account signIn(String username, String password);
    void signOut();
    Account findById(Long id);
    List<Account> findByUsername(String username);
    List<Account> findAll();
    void deleteById(Long id);
    void followById(Long id);
    void unFollowById(Long id);
}
