package ir.maktab32.java.homeworks.instagram.repositories;

import ir.maktab32.java.homeworks.instagram.config.hibernate.HibernateUtil;
import ir.maktab32.java.homeworks.instagram.config.hibernate.repositories.CrudRepository;
import ir.maktab32.java.homeworks.instagram.entities.Post;
import org.hibernate.Session;

import java.util.List;

public class PostRepository extends CrudRepository<Post, Long> {
    @Override
    protected Class<Post> getEntityClass() {
        return Post.class;
    }

    @Override
    protected Session getSession() {
        return HibernateUtil.getSession();
    }

    private static PostRepository postRepository;
    public static PostRepository getInstance(){
        if (postRepository == null)
            postRepository = new PostRepository();
        return postRepository;
    }

    private PostRepository(){}


    public void deletePostsByOwnerId(Long id){
        List<Post> allPosts = findAll();
        if (allPosts.size() > 0){
            for (Post i : allPosts){
                if (i.getOwner().getId() == id)
                    removeById(i.getId());
            }
        }
    }
}
