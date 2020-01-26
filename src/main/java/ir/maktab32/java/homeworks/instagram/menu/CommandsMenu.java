package ir.maktab32.java.homeworks.instagram.menu;

import ir.maktab32.java.homeworks.instagram.entities.Account;
import ir.maktab32.java.homeworks.instagram.share.AuthenticationService;

public class CommandsMenu {
    public static void execute(){
        Account currentUser = AuthenticationService.getInstance().getSignedInUser();
        boolean isSignedIn = false;
        if (currentUser != null)
            isSignedIn = true;

        System.out.println("+------------------------------------------------------------------+");
        System.out.println("|                               Menu                               |");
        System.out.println("+------------------------------------------------------------------+");
        if (!isSignedIn) {
            System.out.println("| sign in  .....................  Sign In                          |");
            System.out.println("| sign up  .....................  Sign Up                          |");
        }
        if (isSignedIn) {
            System.out.println("| my account  ..................  Loads Your Account               |");
            System.out.println("| edit account  ................  Edit You Account                 |");
            System.out.println("| delete account  ..............  Delete Your Account              |");
            System.out.println("| search account by id  ........  Search Account By Id             |");
            System.out.println("| search account by username ...  Search Account By Username       |");
            System.out.println("| follow  ......................  Follow Other Accounts            |");
            System.out.println("| unfollow  ....................  UnFollow Your Following Accounts |");
            System.out.println("| my followers  ................  Loads Your Followers             |");
            System.out.println("| my followings  ...............  Loads Your Followings            |");
            System.out.println("| my posts  ....................  Loads Your Posts                 |");
            System.out.println("| add post  ....................  Add Post                         |");
            System.out.println("| edit post  ...................  Edit Your Post                   |");
            System.out.println("| delete post  .................  Delete Your Post                 |");
            System.out.println("| sort posts by like ...........  Loads All Posts Sorted By Likes  |");
            System.out.println("| search post by id  ...........  Search Post By Id                |");
            System.out.println("| search posts by account  .....  Search Posts By Owner Account    |");
            System.out.println("| like .........................  Like Post By Post Id             |");
            System.out.println("| get post likers ..............  Load Accounts Who Liked A Post   |");
            System.out.println("| add comment ..................  Add Comment on A Post            |");
            System.out.println("| delete comment ...............  Delete Your Comment              |");
            System.out.println("| sign out  ....................  Sign Out                         |");
        }
        System.out.println("| exit  ........................  Exit App                         |");
        System.out.println("+------------------------------------------------------------------+");


    }
}
