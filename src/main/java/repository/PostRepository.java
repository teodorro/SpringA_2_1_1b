package repository;

import exception.NotFoundException;
import model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PostRepository {
    private long nextId = 5;
    private List<Post> repository = new ArrayList<>();
    private Lock lock = new ReentrantLock();


    public List<Post> all(){
        List<Post> posts = Collections.emptyList();

        lock.lock();
        try {
            posts = List.copyOf(repository);
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        System.out.println("get all");
        return posts;
    }

    public Optional<Post> getById(long id){
        Optional<Post> post = null;

        lock.lock();
        try {
            post = repository.stream().filter(x -> x.getId() == id).findAny();
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        System.out.println("get post id=" + id);
        return post;
    }

    public Post save(Post post) throws NotFoundException {
        if (post.getId() == 0) {
            lock.lock();
            try {
                post = new Post(nextId++, post.getMessage());
                repository.add(post);
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
        else{
            lock.lock();
            try {
                var postId = post.getId();
                var item = repository.stream().filter(x -> x.getId() == postId).findAny();
                if (!item.isEmpty()){
                    throw new NotFoundException(postId);
                } else{
                    item.get().setMessage(post.getMessage());
//                    Thread.sleep(2000);
                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        System.out.println("post id=" + post.getId() + " saved");
        return post;
    }

    public void removeById(long id){
        lock.lock();
        try {
            repository.removeIf(x -> x.getId() == id);
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        System.out.println("post id=" + id + " removed");
    }
}
