package ir.maktab32.java.homeworks.instagram.features.impl;

import ir.maktab32.java.homeworks.instagram.entities.Account;
import ir.maktab32.java.homeworks.instagram.entities.Comment;
import ir.maktab32.java.homeworks.instagram.features.dao.CommentDao;
import ir.maktab32.java.homeworks.instagram.repositories.AccountRepository;
import ir.maktab32.java.homeworks.instagram.repositories.CommentRepository;
import ir.maktab32.java.homeworks.instagram.repositories.PostRepository;
import ir.maktab32.java.homeworks.instagram.share.AuthenticationService;

import java.util.List;

public class CommentDaoImpl implements CommentDao {
    @Override
    public Comment add(Long postId, String commentMsg) {
        Comment result;

        if (addingValidation(postId,commentMsg)){
            Comment comment = new Comment();
            comment.setPost(PostRepository.getInstance().findById(postId));
            comment.setWriter(AuthenticationService.getInstance().getSignedInUser());
            comment.setMsg(commentMsg);
            PostRepository.getInstance().findById(postId).getComments().add(comment);
            AuthenticationService.getInstance().getSignedInUser().getComments().add(comment);
            result = CommentRepository.getInstance().save(comment);
            System.out.println("Comment Saved!");
        }
        else {
            result = null;
            System.out.println("Adding Comment Failed!");
        }

        return result;
    }

    @Override
    public void deleteById(Long id) {
        if (deletingValidation(id)){
            AuthenticationService.getInstance().getSignedInUser().getComments().remove(CommentRepository.getInstance().findById(id));
            CommentRepository.getInstance().findById(id).getPost().getComments().remove(CommentRepository.getInstance().findById(id));
            CommentRepository.getInstance().removeById(id);
            AccountRepository.getInstance().update(AuthenticationService.getInstance().getSignedInUser());
            PostRepository.getInstance().update(CommentRepository.getInstance().findById(id).getPost());

            System.out.println("Comment Deleted!");
        }
        else {
            System.out.println("Deleting Comment Failed!");
        }
    }

    private boolean addingValidation(Long postId, String commentMsg){
        boolean result = true;

        Account currentUser = AuthenticationService.getInstance().getSignedInUser();

        if (currentUser == null){
            result = false;
            System.out.println("Sign In First!");
        }
        else if (!PostRepository.getInstance().isExisting(postId)){
            result = false;
            System.out.println("Invalid Post!");
        }
        else if (commentMsg == null || commentMsg.isEmpty()){
            result = false;
            System.out.println("Invalid Comment!");
        }

        return result;
    }

    private boolean deletingValidation(Long id){
        boolean result = true;

        Account currentUser = AuthenticationService.getInstance().getSignedInUser();
        if (!CommentRepository.getInstance().isExisting(id)){
            result = false;
            System.out.println("Invalid Comment Id!");
        }
        else if (currentUser == null || currentUser.getId() != CommentRepository.getInstance().findById(id).getWriter().getId()){
            result = false;
            System.out.println("No Permission to Delete Comment!");
        }

        return result;
    }
}
