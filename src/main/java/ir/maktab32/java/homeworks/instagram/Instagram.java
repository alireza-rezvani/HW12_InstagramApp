package ir.maktab32.java.homeworks.instagram;

import ir.maktab32.java.homeworks.instagram.config.hibernate.HibernateUtil;
import ir.maktab32.java.homeworks.instagram.entities.Account;
import ir.maktab32.java.homeworks.instagram.entities.Comment;
import ir.maktab32.java.homeworks.instagram.entities.Post;
import ir.maktab32.java.homeworks.instagram.features.dao.AccountDao;
import ir.maktab32.java.homeworks.instagram.features.impl.AccountDaoImpl;
import ir.maktab32.java.homeworks.instagram.features.impl.CommentDaoImpl;
import ir.maktab32.java.homeworks.instagram.features.impl.PostDaoImpl;
import ir.maktab32.java.homeworks.instagram.repositories.AccountRepository;
import ir.maktab32.java.homeworks.instagram.repositories.CommentRepository;
import ir.maktab32.java.homeworks.instagram.repositories.PostRepository;
import ir.maktab32.java.homeworks.instagram.share.AuthenticationService;

import java.util.*;

public class Instagram {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command = "";
        String commandMenu = "(sign up | sign in)";
        while (!command.equals("exit")){
            System.out.println("Choose Command: " + commandMenu);
            command = scanner.nextLine();

            if (command.equals("sign up")){
                System.out.print("Username: ");
                String username = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();
                System.out.print("Screen Name: ");
                String screenName = scanner.nextLine();
                System.out.print("Account Image Source: ");
                String imgSrc = scanner.nextLine();
                
                new AccountDaoImpl().add(username,password,screenName,imgSrc);
                commandMenu = "(sign up | sign in)";
            }
            if (command.equals("sign in")){
                System.out.print("Username: ");
                String username = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();
                
                if (new AccountDaoImpl().signIn(username, password) != null){
                    commandMenu = "(view account | edit account | delete account | search account by username | add post" +
                            " | edit post | view post | delete post | view account posts ...)";
                }
            }
            if (command.equals("view account")){}
            if (command.equals("edit account")){}
            if (command.equals("delete account")){}
            if (command.equals("search account by username")){}
            if (command.equals("add post")){}
            if (command.equals("edit post")){}
            if (command.equals("view post")){}
            if (command.equals("delete post")){}
            if (command.equals("view account posts")){}
            if (command.equals("follow")){}
            if (command.equals("unfollow")){}
            if (command.equals("like")){}
            if (command.equals("sort posts by like")){}
            if (command.equals("add comment")){}

            // TODO: 1/25/2020 complete main 
        }
    }
}
