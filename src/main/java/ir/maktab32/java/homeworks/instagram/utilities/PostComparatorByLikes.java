package ir.maktab32.java.homeworks.instagram.utilities;

import ir.maktab32.java.homeworks.instagram.entities.Post;

import java.util.Comparator;

public class PostComparatorByLikes implements Comparator<Post> {

    @Override
    public int compare(Post o1, Post o2) {
        return o2.getLikers().size() - o1.getLikers().size();
    }
}
