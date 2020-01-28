package ir.maktab32.java.homeworks.instagram.features.dao;

import ir.maktab32.java.homeworks.instagram.entities.Post;

import java.util.List;

public interface PostDao {
    Post add(String imgSrc, String description);
    Post edit(Long postId, String imgSrc, String description);
    Post findById(Long id);
    List<Post> findByAccountId(Long accountId);
    List<Post> findAll();
    List<Post> findAllSortedByLikes();
    void likeById(Long id);
    void deleteById(Long id);
}
