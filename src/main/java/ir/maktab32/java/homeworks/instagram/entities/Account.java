package ir.maktab32.java.homeworks.instagram.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private String name;
    private String accountImgSrc;

    @ManyToMany
    @JoinTable(name = "follow_tbl", joinColumns = @JoinColumn(name = "following_id"), inverseJoinColumns = @JoinColumn(name = "follower_id"))
    private List<Account> followers = new ArrayList<>() ;

    @ManyToMany(mappedBy = "followers")
//    @JoinTable(name = "follow_tbl" ,joinColumns = @JoinColumn(name = "follower_id"), inverseJoinColumns = @JoinColumn(name = "following_id"))
    private List<Account> followings = new ArrayList<>() ;

    @OneToMany(mappedBy = "owner")
    private List<Post> posts = new ArrayList<>() ;

    @ManyToMany
    @JoinTable(name = "like_tbl", joinColumns = @JoinColumn(name = "liker_id"), inverseJoinColumns = @JoinColumn(name = "post_id"))
    private List<Post> likedPosts = new ArrayList<>() ;

    @OneToMany(mappedBy = "writer")
    private List<Comment> comments = new ArrayList<>() ;


    @Override
    public String toString() {
        String result = "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", accountImgSrc='" + accountImgSrc + '\'' +
                ", followersCount=" + followers.size() +
                ", followingsCount=" + followings.size() +
                ", postsCount=" + posts.size() +
                ", likedPostsCount=" + likedPosts.size() +
                ", commentsCount=" + comments.size() +
                '}';

        result +="\nPosts of " + username +": (" + posts.size() + " Posts) \n";

        for (Post i : posts) {
            result += "Post Id: " + i.getId() + ", ImageSource: " + i.getPostImgSrc() + "\n";
        }

        return result;
    }
}
