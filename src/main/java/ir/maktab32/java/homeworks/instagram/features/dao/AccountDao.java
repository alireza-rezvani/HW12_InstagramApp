package ir.maktab32.java.homeworks.instagram.features.dao;

import ir.maktab32.java.homeworks.instagram.entities.Account;

public interface AccountDao {
    Account add(Account account);
    Account edit(Account account);
    Account findById(Long id);
    Account findByUsername(String username);
    void deleteById(Long id);
    void followById(Long id);
    void unFollowById(Long id);
}
