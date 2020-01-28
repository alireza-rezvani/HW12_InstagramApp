package ir.maktab32.java.homeworks.instagram;

import ir.maktab32.java.homeworks.instagram.entities.Account;
import ir.maktab32.java.homeworks.instagram.entities.Comment;
import ir.maktab32.java.homeworks.instagram.entities.Post;
import ir.maktab32.java.homeworks.instagram.features.impl.AccountDaoImpl;
import ir.maktab32.java.homeworks.instagram.features.impl.CommentDaoImpl;
import ir.maktab32.java.homeworks.instagram.features.impl.PostDaoImpl;
import ir.maktab32.java.homeworks.instagram.menu.CommandsMenu;
import ir.maktab32.java.homeworks.instagram.repositories.AccountRepository;
import ir.maktab32.java.homeworks.instagram.share.AuthenticationService;
import ir.maktab32.java.homeworks.instagram.utilities.IsNumeric;

import java.util.*;

public class Instagram {
    public static void main(String[] args) {

        //Connecting to Database at First
        AccountRepository.getInstance().findById(1L);

        Scanner scanner = new Scanner(System.in);

        String command = "";

        while (!command.equals("exit")){

            System.out.println("Input Command: (m -> Menu)");
            command = scanner.nextLine();

            if (command.equalsIgnoreCase("m")){
                CommandsMenu.execute();
            }
            else if (command.equalsIgnoreCase("sign up")){
                if (AuthenticationService.getInstance().getSignedInUser() == null) {
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();
                    System.out.print("Screen Name: ");
                    String screenName = scanner.nextLine();
                    System.out.print("Account Image Source: ");
                    String imgSrc = scanner.nextLine();

                    new AccountDaoImpl().add(username, password, screenName, imgSrc);
                }
                else {
                    System.out.println("Sign Out First!");
                }
            }
            else if (command.equalsIgnoreCase("sign in")){
                if (AuthenticationService.getInstance().getSignedInUser() == null) {
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();
                    new AccountDaoImpl().signIn(username, password);
                }
                else
                    System.out.println("Sign Out First!");

            }
            else if (command.equalsIgnoreCase("sign out")){
                new AccountDaoImpl().signOut();
            }
            else if (command.equalsIgnoreCase("my account")){
                Account currentAccount = AuthenticationService.getInstance().getSignedInUser();
                if (currentAccount != null)
                    System.out.println(currentAccount);
                else
                    System.out.println("Sign In First!");
            }
            else if (command.equalsIgnoreCase("edit account")){
                System.out.print("Username: ");
                String username = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();
                System.out.print("Screen Name: ");
                String screenName = scanner.nextLine();
                System.out.print("Account Image Source: ");
                String imgSrc = scanner.nextLine();
                Account editedAccount = new AccountDaoImpl().edit(username, password, screenName, imgSrc);
                if (editedAccount != null)
                    System.out.println(editedAccount);
            }
            else if (command.equalsIgnoreCase("delete account")){
                Account currentUser = AuthenticationService.getInstance().getSignedInUser();
                if (currentUser != null)
                    new AccountDaoImpl().deleteById(currentUser.getId());
                else
                    System.out.println("Sign In First!");
            }
            else if (command.equalsIgnoreCase("search account by id")){
                System.out.print("Account Id: ");
                String id = scanner.nextLine();
                if (IsNumeric.execute(id)) {
                    Account account = new AccountDaoImpl().findById(Long.parseLong(id));
                    if (account != null)
                        System.out.println(account);
                }
                else {
                    System.out.println("invalid Account Id!");
                }
            }
            else if (command.equalsIgnoreCase("search account by username")){
                System.out.print("Account Username: ");
                String username = scanner.nextLine();
                List<Account> accounts = new AccountDaoImpl().findByUsername(username);
                if (accounts != null && accounts.size() != 0)
                    accounts.forEach(System.out::println);
                else if (accounts != null && accounts.size() == 0)
                    System.out.println("No Matches!");
            }
            else if (command.equalsIgnoreCase("add post")){
                System.out.print("Image Source: ");
                String imgSrc = scanner.nextLine();
                System.out.print("Description: ");
                String description = scanner.nextLine();
                Post post = new PostDaoImpl().add(imgSrc, description);
                if (post != null)
                    System.out.println(post);
            }
            else if (command.equalsIgnoreCase("edit post")){
                System.out.print("Post Id: ");
                String id = scanner.nextLine();
                if (IsNumeric.execute(id)) {
                    System.out.print("Image Source: ");
                    String imgSrc = scanner.nextLine();
                    System.out.print("Description: ");
                    String description = scanner.nextLine();

                    Post post = new PostDaoImpl().edit(Long.parseLong(id), imgSrc, description);
                    if (post != null)
                        System.out.println(post);
                }
                else {
                    System.out.println("Invalid Id!");
                }
            }
            else if (command.equalsIgnoreCase("my posts")){
                Account currentUser = AuthenticationService.getInstance().getSignedInUser();
                if (currentUser != null && currentUser.getPosts().size() > 0)
                    currentUser.getPosts().forEach(System.out::println);
                else
                    System.out.println("No Result!");
            }
            else if (command.equalsIgnoreCase("search post by id")){
                System.out.print("Post Id: ");
                String id = scanner.nextLine();
                if (IsNumeric.execute(id)) {
                    Post post = new PostDaoImpl().findById(Long.parseLong(id));
                    if (post != null)
                        System.out.println(post);
                }
            }
            else if (command.equalsIgnoreCase("delete post")){
                System.out.print("Post Id: ");
                String id = scanner.nextLine();
                if (IsNumeric.execute(id)) {
                    new PostDaoImpl().deleteById(Long.parseLong(id));
                }
            }
            else if (command.equalsIgnoreCase("search posts by account")){
                System.out.print("Input Account Id or Username: ");
                String input = scanner.nextLine();
                Long resultId = null;
                if (IsNumeric.execute(input)) {
                    resultId = Long.parseLong(input);
                }
                else {
                    List<Account> accounts = new AccountDaoImpl().findByUsername(input);
                    if (accounts.size() > 0) {
                        for (Account i : accounts) {
                            if (i.getUsername().equals(input)) {
                                resultId = i.getId();
                                break;
                            }
                        }
                    }
                }
                if (resultId != null) {
                    new PostDaoImpl().findByAccountId(resultId).forEach(System.out::println);
                }
                else {
                    System.out.println("Invalid Input!");
                }
            }
            else if (command.equalsIgnoreCase("follow")){
                System.out.print("Input Account Id or Username: ");
                String input = scanner.nextLine();
                Long resultId = null;
                if (IsNumeric.execute(input)) {
                    resultId = Long.parseLong(input);
                }
                else {
                    List<Account> accounts = new AccountDaoImpl().findByUsername(input);
                    if (accounts.size() > 0) {
                        for (Account i : accounts) {
                            if (i.getUsername().equals(input)) {
                                resultId = i.getId();
                                break;
                            }
                        }
                    }
                }
                if (resultId != null) {
                    new AccountDaoImpl().followById(resultId);
                }
                else {
                    System.out.println("Invalid Input!");
                }
            }
            else if (command.equalsIgnoreCase("unfollow")){
                System.out.print("Input Account Id or Username: ");
                String input = scanner.nextLine();
                Long resultId = null;
                if (IsNumeric.execute(input)) {
                    resultId = Long.parseLong(input);
                }
                else {
                    List<Account> accounts = new AccountDaoImpl().findByUsername(input);
                    if (accounts.size() > 0) {
                        for (Account i : accounts) {
                            if (i.getUsername().equals(input)) {
                                resultId = i.getId();
                                break;
                            }
                        }
                    }
                }
                if (resultId != null) {
                    new AccountDaoImpl().unFollowById(resultId);
                }
                else {
                    System.out.println("Invalid Input!");
                }
            }
            else if (command.equalsIgnoreCase("my followers")){
                Account currentUser = AuthenticationService.getInstance().getSignedInUser();
                if (currentUser != null){
                    if (currentUser.getFollowers().size() > 0){
                        System.out.println("Your Followers: ");
                        currentUser.getFollowers().forEach(System.out::println);
                    }
                    else
                        System.out.println("You Don't Have Followers!");
                }
                else
                    System.out.println("Sign In First!");
            }
            else if (command.equalsIgnoreCase("my followings")){
                Account currentUser = AuthenticationService.getInstance().getSignedInUser();
                if (currentUser != null){
                    if (currentUser.getFollowings().size() > 0){
                        System.out.println("Your Followings: ");
                        currentUser.getFollowings().forEach(System.out::println);
                    }
                    else
                        System.out.println("You Don't Have Followings!");
                }
                else
                    System.out.println("Sign In First!");
            }
            else if (command.equalsIgnoreCase("like")){
                System.out.print("Input Post Id to Like: ");
                String id = scanner.nextLine();
                if (IsNumeric.execute(id)) {
                    new PostDaoImpl().likeById(Long.parseLong(id));
                }
                else
                    System.out.println("Invalid Post Id!");
            }
            else if (command.equalsIgnoreCase("get post likers")){
                System.out.print("Post Id: ");
                String id = scanner.nextLine();
                if (IsNumeric.execute(id)){
                    Post post = new PostDaoImpl().findById(Long.parseLong(id));
                    if (post != null && post.getLikers().size() > 0){
                        System.out.println("Accounts Who Liked Post(Id: "+ id +")");
                        post.getLikers().forEach(System.out::println);
                    }

                }
                else
                    System.out.println("Invalid Post Id!");
            }
            else if (command.equalsIgnoreCase("sort posts by like")){
                List<Post> sortedPosts = new PostDaoImpl().findAllSortedByLikes();
                if (sortedPosts.size() > 0){
                    sortedPosts.forEach(System.out::println);
                }
                else
                    System.out.println("There is No Posts!");
            }
            else if (command.equalsIgnoreCase("add comment")){
                System.out.print("Post Id: ");
                String id = scanner.nextLine();
                if (IsNumeric.execute(id)) {
                    System.out.print("Message: ");
                    String message = scanner.nextLine();

                    Comment comment = new CommentDaoImpl().add(Long.parseLong(id), message);
                    if (comment != null)
                        System.out.println(new PostDaoImpl().findById(Long.parseLong(id)));
                }
                else {
                    System.out.println("Invalid Post Id!");
                }
            }
            else if (command.equalsIgnoreCase("delete comment")){
                System.out.print("Comment Id: ");
                String id = scanner.nextLine();
                if (IsNumeric.execute(id)){
                    new CommentDaoImpl().deleteById(Long.parseLong(id));
                }
                else
                    System.out.println("Invalid Comment Id!");
            }
            else if (command.equalsIgnoreCase("exit")){
                System.out.println("Good Bye!");
            }
            else {
                System.out.println("Invalid Command!");
            }


        }
    }
}
