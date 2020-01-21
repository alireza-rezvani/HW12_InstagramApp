package ir.maktab32.java.homeworks.instagram.repositories;

import ir.maktab32.java.homeworks.instagram.config.hibernate.repositories.CrudRepository;
import ir.maktab32.java.homeworks.instagram.entities.Comment;

public class CommentRepository extends CrudRepository<Comment, Long> {
    @Override
    protected Class<Comment> getEntityClass() {
        return Comment.class;
    }

    private static CommentRepository commentRepository;
    public static CommentRepository getInstance(){
        if (commentRepository == null)
            commentRepository = new CommentRepository();
        return commentRepository;
    }

    private CommentRepository(){}
}
