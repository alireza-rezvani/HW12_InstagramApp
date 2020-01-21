package ir.maktab32.java.homeworks.instagram.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String name;
    private String accountImgSrc;

    @ManyToMany
    @JoinTable(name = "follow", joinColumns = @JoinColumn(name = "following"), inverseJoinColumns = @JoinColumn(name = "follower"))
    private List<Account> followers;

    @ManyToMany
    @JoinTable(name = "follow", joinColumns = @JoinColumn(name = "follower"), inverseJoinColumns = @JoinColumn(name = "following"))
    private List<Account> followings;


    private List<Post> posts;
    private List<Post> likedPosts;
    private List<Comment> comments;
}
