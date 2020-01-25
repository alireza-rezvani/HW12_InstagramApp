package ir.maktab32.java.homeworks.instagram.features.impl;

import ir.maktab32.java.homeworks.instagram.entities.Account;
import ir.maktab32.java.homeworks.instagram.features.dao.AccountDao;
import ir.maktab32.java.homeworks.instagram.repositories.AccountRepository;
import ir.maktab32.java.homeworks.instagram.repositories.CommentRepository;
import ir.maktab32.java.homeworks.instagram.repositories.PostRepository;
import ir.maktab32.java.homeworks.instagram.share.AuthenticationService;

import java.util.ArrayList;
import java.util.List;

// TODO: 1/24/2020 add more validation if needed

public class AccountDaoImpl implements AccountDao {
    @Override
    public Account add(String username, String password, String screenName, String accountImg) {
        Account result;
        if (addingValidation(username, password, screenName, accountImg)){
            Account account = new Account();
            account.setUsername(username);
            account.setPassword(password);
            account.setName(screenName);
            account.setAccountImgSrc(accountImg);
            result = AccountRepository.getInstance().save(account);
            System.out.println("Account Added! Id = " + result.getId());
        }
        else {
            result = null;
            System.out.println("Adding Account Failed!");
        }
        return result;
    }

    @Override
    public Account edit(String username, String password, String screenName, String accountImg) {
        Account result;

        if (editingValidation(username, password, screenName, accountImg)){
            Account account = AuthenticationService.getInstance().getSignedInUser();
            account.setUsername(username);
            account.setPassword(password);
            account.setName(screenName);
            account.setAccountImgSrc(accountImg);
            result = AccountRepository.getInstance().update(account);
            System.out.println("Account Edited!");
        }
        else {
            result = null;
            System.out.println("Edit Account Failed!");
        }

        return result;
    }

    @Override
    public Account signIn(String username, String password) {
        Account result;
        if (AuthenticationService.getInstance().getSignedInUser() == null) {
            if (AccountRepository.getInstance().isUsernameExisting(username)) {
                if (AccountRepository.getInstance().findByUsername(username).getPassword().equals(password)) {
                    result = AccountRepository.getInstance().findByUsername(username);
                    AuthenticationService.getInstance().setSignedInUser(result);
                    System.out.println("Sign In Successful!");
                } else {
                    result = null;
                    System.out.println("Invalid Password!");
                }
            } else {
                result = null;
                System.out.println("Username Doesn't Exist!");
            }
        }
        else {
            result = null;
            System.out.println("You Are Signed In Already!");
        }
        return result;
    }

    @Override
    public void signOut() {
        if (AuthenticationService.getInstance().getSignedInUser() != null){
            AuthenticationService.getInstance().setSignedInUser(null);
            System.out.println("Sign Out Successful!");
        }
        else {
            System.out.println("You Are Not Signed In!");
        }
    }

    @Override
    public Account findById(Long id) {
        Account result;
        if (AccountRepository.getInstance().isExisting(id)){
            result = AccountRepository.getInstance().findById(id);
            System.out.println("Account Loaded!");
        }
        else {
            result = null;
            System.out.println("Account Not Existing!");
        }
        return result;
    }

    @Override
    public List<Account> findByUsername(String username) {
        List<Account> result = new ArrayList<>();

        List<Account> allAccounts = AccountRepository.getInstance().findAll();
        if (allAccounts.size() != 0){
            for (Account i : allAccounts){
                if (i.getUsername().contains(username))
                    result.add(i);
            }

            if (result.size() == 0)
                System.out.println("No Matches");
        }
        else {
            result = null;
            System.out.println("No Matches!");
        }

        return result;
    }

    @Override
    public List<Account> findAll() {
        List<Account> result;

        result = AccountRepository.getInstance().findAll();
        if (result.size() == 0)
            System.out.println("No Accounts In Database!");

        return result;
    }

    @Override
    public void deleteById(Long id) {
        if (deletingValidation(id)){
            Account account = AccountRepository.getInstance().findById(id);

            int commentsSize = account.getComments().size();
            if (commentsSize > 0){
                for (int i = commentsSize - 1; i >= 0; i--){
                    account.getComments().remove(account.getComments().get(i));
                }
                CommentRepository.getInstance().deleteCommentsByOwnerId(id);
            }

            int postsSize = account.getPosts().size();
            if (postsSize > 0) {
                for (int i = postsSize - 1; i >= 0; i--){
                    account.getPosts().remove(account.getPosts().get(i));
                }
                PostRepository.getInstance().deletePostsByOwnerId(id);
            }


            List<Account> followers = AccountRepository.getInstance().findById(id).getFollowers();
            List<Account> followings = AccountRepository.getInstance().findById(id).getFollowings();

            if (followers.size() > 0){
                for (int i = 0; i < followers.size(); i++){
                    followers.get(i).getFollowings().remove(account);
                    account.getFollowers().remove(followers.get(i));
                }
            }

            if (followings.size() > 0){
                for (int i = 0; i<followings.size();i++){
                    followings.get(i).getFollowers().remove(account);
                    account.getFollowings().remove(followings.get(i));
                }
            }

            AccountRepository.getInstance().update(account);
            AccountRepository.getInstance().removeById(id);
            System.out.println("Deleted Successfully!");
        }
        else
            System.out.println("Delete Failed!");
    }

    @Override
    public void followById(Long id) {
        Account currentUser = AuthenticationService.getInstance().getSignedInUser();
        if (currentUser != null && currentUser.getId() != id && AccountRepository.getInstance().isExisting(id)
                && !currentUser.getFollowings().contains(AccountRepository.getInstance().findById(id))){
            Account following = AccountRepository.getInstance().findById(id);
            following.getFollowers().add(currentUser);
            currentUser.getFollowings().add(following);
            AccountRepository.getInstance().update(currentUser);
            System.out.println("Follow Successful!");
        }
        else {
            System.out.println("Follow Failed!");
        }
    }

    @Override
    public void unFollowById(Long id) {
        Account currentUser = AuthenticationService.getInstance().getSignedInUser();
        if (currentUser != null && currentUser.getId() != id && AccountRepository.getInstance().isExisting(id)
                && currentUser.getFollowings().contains(AccountRepository.getInstance().findById(id))){
            Account following = AccountRepository.getInstance().findById(id);
            following.getFollowers().remove(currentUser);
            currentUser.getFollowings().remove(following);
            AccountRepository.getInstance().update(currentUser);
            System.out.println("UnFollow Successful!");
        }
        else {
            System.out.println("UnFollow Failed!");
        }
    }

    private boolean addingValidation(String username, String password, String screenName, String accountImg){
        boolean result = true;
        if (username == null || username.isEmpty()
                || AccountRepository.getInstance().isUsernameExisting(username)){
            result = false;
            System.out.println("Invalid Username!");
        }
        if (password == null || password.isEmpty()){
            result = false;
            System.out.println("Invalid Password!");
        }

        return result;
    }

    private boolean editingValidation(String username, String password, String screenName, String accountImg){
        boolean result = true;

        Account currentUser = AuthenticationService.getInstance().getSignedInUser();
        if (currentUser == null){
            result = false;
            System.out.println("Sign In First!");
        }
        else if (username == null || username.isEmpty()
                || !AccountRepository.getInstance().isUsernameExisting(username)){
            result = false;
            System.out.println("Invalid Username!");
        }
        if (password == null || password.isEmpty()){
            result = false;
            System.out.println("Invalid Password!");
        }

        return result;
    }

    private boolean deletingValidation(Long id){
        boolean result = true;
        Account currentUser = AuthenticationService.getInstance().getSignedInUser();
        if (currentUser == null){
            System.out.println("Sign In First!");
            result = false;
        }
        else if (currentUser.getId() != id){
            System.out.println("No Permission to Delete!");
            result = false;
        }
        return result;
    }
}
