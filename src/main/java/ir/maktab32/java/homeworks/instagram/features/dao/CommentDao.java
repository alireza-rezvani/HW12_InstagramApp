package ir.maktab32.java.homeworks.instagram.features.dao;

import ir.maktab32.java.homeworks.instagram.entities.Comment;

public interface CommentDao {
    Comment add(Long postId, String commentMsg);
    void  deleteById(Long id);
}
