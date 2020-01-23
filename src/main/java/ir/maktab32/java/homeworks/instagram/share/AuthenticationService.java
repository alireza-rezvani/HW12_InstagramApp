package ir.maktab32.java.homeworks.instagram.share;

import ir.maktab32.java.homeworks.instagram.entities.Account;

public class AuthenticationService {

    private AuthenticationService(){}

    private Account signedInUser;

    private static AuthenticationService authenticationService = null;
    public static AuthenticationService getInstance(){
        if (authenticationService == null)
            authenticationService = new AuthenticationService();
        return authenticationService;
    }

    public Account getSignedInUser(){return signedInUser;}
    public void setSignedInUser(Account user){this.signedInUser = user;}

}
