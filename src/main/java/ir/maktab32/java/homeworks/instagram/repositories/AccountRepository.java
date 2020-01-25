package ir.maktab32.java.homeworks.instagram.repositories;

import ir.maktab32.java.homeworks.instagram.config.hibernate.repositories.CrudRepository;
import ir.maktab32.java.homeworks.instagram.entities.Account;
import ir.maktab32.java.homeworks.instagram.utilities.Follow;

import java.util.ArrayList;
import java.util.List;

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


    public boolean isUsernameExisting(String username){
        boolean result = false;
        List<Account> allAccounts = AccountRepository.getInstance().findAll();
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
        List<Account> allAccounts = AccountRepository.getInstance().findAll();
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

//    public boolean isFirstFollowerOfSecond(Long firstAccountId, Long secondAccountId){
//        boolean result = false;
//
//        if (isExisting(firstAccountId) && isExisting(secondAccountId)){
//            getSession().beginTransaction();
//            List<Follow> followList = getSession().createQuery("from follow_tbl", Follow.class).list();
//            getSession().getTransaction().commit();
//
//            if (followList != null && !followList.isEmpty()){
//                for (Follow i : followList){
//                    if (i.getFollowerId() == firstAccountId && i.getFollowingId() == secondAccountId)
//                        result = true;
//                }
//            }
//        }
//
//        return result;
//    }

//    @Override
//    public void removeById(Long id) {
//        if (isExisting(id)) {
//            getSession().beginTransaction();
//
//            getSession().createQuery("delete from follow_tbl where follower_id = " + id).list();
//            getSession().createQuery("delete from follow_tbl where following_id = " + id).list();
//            getSession().remove(findById(id));
//
//            getSession().getTransaction().commit();
//
//        }
//    }
//
//    public List<Account> findFollowers(Long id){
//        List<Account> result = new ArrayList<>();
//        if (isExisting(id)){
//            List<Account> followers = new ArrayList<>(AccountRepository.getInstance().findById(id).getFollowers());
//        }
//
//    }
}
