package ir.maktab32.java.homeworks.instagram.features.dao;

import ir.maktab32.java.homeworks.instagram.entities.Account;

import java.util.List;

public interface AccountDao {
    Account add(Account account);
    Account edit(Account account);
    Account findById(Long id);
    List<Account> findByUsername(String username);
    List<Account> findAll();
    void deleteById(Long id);
    void followById(Long id);
    void unFollowById(Long id);
}
