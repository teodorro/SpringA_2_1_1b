package config;

import controller.PostController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.PostRepository;
import service.PostService;

@Configuration
public class Config {
    @Bean
    public PostRepository postRepository(){
        return new PostRepository();
    }

    @Bean
    public PostService postService(PostRepository repository){
        return new PostService(repository);
    }

    @Bean
    public PostController postController(PostService service){
        return new PostController(service);
    }

}
