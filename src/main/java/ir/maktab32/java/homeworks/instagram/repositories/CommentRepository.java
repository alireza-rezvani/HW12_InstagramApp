package ir.maktab32.java.homeworks.instagram.repositories;

import ir.maktab32.java.homeworks.instagram.config.hibernate.repositories.CrudRepository;
import ir.maktab32.java.homeworks.instagram.entities.Comment;
import ir.maktab32.java.homeworks.instagram.entities.Post;

import java.util.List;

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

    public void deleteCommentsByOwnerId(Long id){
        List<Comment> allComments = findAll();
        if (allComments.size() > 0){
            for (Comment i : allComments){
                if (i.getWriter().getId() == id)
                    removeById(i.getId());
            }
        }
    }

    public void deleteCommentsByPostId(Long id){
        List<Comment> allComments = findAll();
        if (allComments.size() > 0){
            for (Comment i : allComments){
                if (i.getPost().getId() == id)
                    removeById(i.getId());
            }
        }
    }
}
