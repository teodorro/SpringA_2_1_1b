package springPack.service;

import org.springframework.beans.factory.annotation.Autowired;
import springPack.exception.NotFoundException;
import springPack.model.Post;
import org.springframework.stereotype.Service;
import springPack.repository.PostRepository;

import java.util.List;

@Service
public class PostService {
    private final PostRepository repository;

    @Autowired
    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<Post> all(){
        return repository.all();
    }

    public Post getById(long id) throws NotFoundException {
        return repository.getById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Post save(Post post) throws NotFoundException {
        return repository.save(post);
    }

    public void removeById(long id){
        repository.removeById(id);
    }
}
