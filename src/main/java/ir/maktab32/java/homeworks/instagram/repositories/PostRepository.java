package ir.maktab32.java.homeworks.instagram.repositories;

import ir.maktab32.java.homeworks.instagram.config.hibernate.repositories.CrudRepository;
import ir.maktab32.java.homeworks.instagram.entities.Post;

public class PostRepository extends CrudRepository<Post, Long> {
    @Override
    protected Class<Post> getEntityClass() {
        return Post.class;
    }

    private static PostRepository postRepository;
    public static PostRepository getInstance(){
        if (postRepository == null)
            postRepository = new PostRepository();
        return postRepository;
    }

    private PostRepository(){}
}
