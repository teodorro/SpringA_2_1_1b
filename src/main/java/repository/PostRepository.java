package repository;

import exception.NotFoundException;
import model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PostRepository {
    private long nextId = 5;
    private List<Post> repository = new ArrayList<>();


    public List<Post> all(){
        System.out.println("get all");
        return repository;
    }

    public Optional<Post> getById(long id){
        System.out.println("get post id=" + id);
        return repository.stream().filter(x -> x.getId() == id).findAny();
    }

    public Post save(Post post) throws NotFoundException {
        if (post.getId() == 0) {
            post = new Post(nextId++, post.getMessage());
            repository.add(post);
        }
        else{
            var postId = post.getId();
            var item = repository.stream().filter(x -> x.getId() == postId).findAny();
            if (item.isEmpty()){
                throw new NotFoundException(postId);
            } else{
                item.get().setMessage(post.getMessage());
            }
        }

        System.out.println("post id=" + post.getId() + " saved");
        return post;
    }

    public void removeById(long id){
        repository.removeIf(x -> x.getId() == id);
        System.out.println("post id=" + id + " removed");
    }
}
