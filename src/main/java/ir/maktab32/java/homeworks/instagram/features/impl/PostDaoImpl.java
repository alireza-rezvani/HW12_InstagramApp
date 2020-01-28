package ir.maktab32.java.homeworks.instagram.features.impl;

import ir.maktab32.java.homeworks.instagram.entities.Account;
import ir.maktab32.java.homeworks.instagram.entities.Post;
import ir.maktab32.java.homeworks.instagram.features.dao.PostDao;
import ir.maktab32.java.homeworks.instagram.repositories.AccountRepository;
import ir.maktab32.java.homeworks.instagram.repositories.CommentRepository;
import ir.maktab32.java.homeworks.instagram.repositories.PostRepository;
import ir.maktab32.java.homeworks.instagram.share.AuthenticationService;
import ir.maktab32.java.homeworks.instagram.utilities.PostComparatorByLikes;

import java.util.Collections;
import java.util.List;

// TODO: 1/24/2020 add more validations if needed

public class PostDaoImpl implements PostDao {
    @Override
    public Post add(String imgSrc, String description) {
        Post result;

        if (addingValidation(imgSrc, description)){
            Post post = new Post();
            post.setPostImgSrc(imgSrc);
            post.setDescription(description);
            Account currentUser = AuthenticationService.getInstance().getSignedInUser();
            post.setOwner(currentUser);
            AccountRepository.getInstance().findById(currentUser.getId()).getPosts().add(post);
            result = PostRepository.getInstance().save(post);
            System.out.println("Post Added! id = " + post.getId());
        }
        else {
            result = null;
            System.out.println("Adding Post Failed!");
        }

        return result;
    }

    @Override
    public Post edit(Long postId, String imgSrc, String description) {
        Post result;

        if (editingValidation(postId, imgSrc, description)){
            Post post = PostRepository.getInstance().findById(postId);
            post.setId(postId);
            post.setPostImgSrc(imgSrc);
            post.setDescription(description);
            post.setOwner(AuthenticationService.getInstance().getSignedInUser());
            result = PostRepository.getInstance().update(post);
            System.out.println("Post Edited!");
        }
        else {
            result = null;
            System.out.println("Editing Post Failed!");
        }

        return result;
    }

    @Override
    public Post findById(Long id) {
        Post result;

        if (PostRepository.getInstance().isExisting(id)) {
            result = PostRepository.getInstance().findById(id);
        }
        else {
            result = null;
            System.out.println("Invalid Post Id!");
        }

        return result;
    }

    @Override
    public List<Post> findByAccountId(Long accountId) {
        List<Post> result;

        if (AccountRepository.getInstance().isExisting(accountId)){
            result = AccountRepository.getInstance().findById(accountId).getPosts();
        }
        else {
            result = null;
            System.out.println("Invalid Account!");
        }

        return result;
    }

    @Override
    public List<Post> findAll() {
        List<Post> result;

        result = PostRepository.getInstance().findAll();

        return result;
    }

    @Override
    public List<Post> findAllSortedByLikes() {
        List<Post> result;

        result = PostRepository.getInstance().findAll();
        Collections.sort(result, new PostComparatorByLikes());

        return result;
    }

    @Override
    public void likeById(Long id) {
        Account currentUser = AuthenticationService.getInstance().getSignedInUser();
        if (currentUser != null){
            if (PostRepository.getInstance().isExisting(id)) {
                if (!currentUser.getLikedPosts().contains(PostRepository.getInstance().findById(id))) {
                    Post post = PostRepository.getInstance().findById(id);
                    post.getLikers().add(currentUser);
                    PostRepository.getInstance().update(post);
                    currentUser.getLikedPosts().add(post);
                    System.out.println("Liked Succesfully!");
                } else {
                    System.out.println("You Have Liked This Post Already!");
                }
            }
            else
                System.out.println("Invalid Post Id!");
        }
        else {
            System.out.println("Like Failed!\nSign In First!");
        }

    }

    @Override
    public void deleteById(Long id) {
        if (deletingValidation(id)){
            Post post = PostRepository.getInstance().findById(id);
            Account owner = AccountRepository.getInstance().findById(post.getOwner().getId());

            int commentsSize = post.getComments().size();
            if (commentsSize > 0) {
                for (int i = commentsSize - 1; i >= 0; i--) {
                    post.getComments().get(i).getWriter().getComments().remove(post.getComments().get(i));
                    AccountRepository.getInstance().update(post.getComments().get(i).getWriter());
                    post.getComments().remove(post.getComments().get(i));
                }
                CommentRepository.getInstance().deleteCommentsByPostId(id);
            }

            int likesSize = post.getLikers().size();
            if (likesSize > 0){
                for (int i = likesSize - 1; i >= 0; i--){
                    post.getLikers().get(i).getLikedPosts().remove(post);
                    AccountRepository.getInstance().update(post.getLikers().get(i));
                    post.getLikers().remove(post.getLikers().get(i));
                }
            }

            owner.getPosts().remove(post);

            PostRepository.getInstance().removeById(id);

            AccountRepository.getInstance().update(owner);

            System.out.println("Post Deleted!");
        }
        else {
            System.out.println("Deleting Post Failed!");
        }
    }

    private boolean addingValidation(String imgSrc, String description){
        boolean result = true;

        Account currentUser = AuthenticationService.getInstance().getSignedInUser();
        if (currentUser == null){
            result = false;
            System.out.println("No Permission to Add!");
        }
        else if (imgSrc == null || imgSrc.isEmpty()){
            result = false;
            System.out.println("Invalid Image Source!");
        }
        return result;
    }

    private boolean editingValidation(Long postId, String imgSrc, String description){
        boolean result = true;

        Account currentUser = AuthenticationService.getInstance().getSignedInUser();
        if (!PostRepository.getInstance().isExisting(postId)){
            result = false;
            System.out.println("Invalid Post!");
        }
        else if (currentUser == null || currentUser.getId() != PostRepository.getInstance().findById(postId).getOwner().getId()){
            result = false;
            System.out.println("Edit Permission Denied!");
        }
        else if (imgSrc == null || imgSrc.isEmpty()){
            result = false;
            System.out.println("Invalid Image Source!");
        }

        return result;
    }

    private boolean deletingValidation(Long id){
        boolean result = true;

        Account currentUser = AuthenticationService.getInstance().getSignedInUser();

        if (!PostRepository.getInstance().isExisting(id)){
            result = false;
            System.out.println("Invalid Post Id!");
        }
        else if (currentUser == null || currentUser.getId() != PostRepository.getInstance().findById(id).getOwner().getId()){
            result = false;
            System.out.println("No Permission to Delete Post!");
        }

        return result;
    }
}
