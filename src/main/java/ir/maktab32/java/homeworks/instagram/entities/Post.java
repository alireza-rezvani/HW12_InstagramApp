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
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String postImgSrc;

    private String description;

    @ManyToOne
    private Account owner;

    @ManyToMany
    @JoinTable(name = "like_tbl", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "liker_id"))
    private List<Account> likers = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;


}
