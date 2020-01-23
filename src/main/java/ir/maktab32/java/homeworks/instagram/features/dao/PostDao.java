package ir.maktab32.java.homeworks.instagram.features.dao;

import ir.maktab32.java.homeworks.instagram.entities.Account;
import ir.maktab32.java.homeworks.instagram.entities.Post;

import java.util.List;

public interface PostDao {
    Post add(Post post);
    Post edit(Post post);
    Post findById(Long id);
    List<Post> findByAccountAccount(Account account);
    List<Post> findAllSortedByLikes();
    void likeById(Long id);
    void deleteById(Long id);
}
