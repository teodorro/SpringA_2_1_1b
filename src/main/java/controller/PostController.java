package controller;

import com.google.gson.Gson;
import exception.NotFoundException;
import model.Post;
import service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    public void all(HttpServletResponse response) throws IOException {
        final var gson = new Gson();
        final var data = service.all();
        printResponseData(response, gson.toJson(data));
    }

    public void getById(long id, HttpServletResponse response) throws IOException, NotFoundException {
        final var gson = new Gson();
        final var data = service.getById(id);
        printResponseData(response, gson.toJson(data));
    }

    public void save(Reader body, HttpServletResponse response) throws IOException, NotFoundException {
        final var gson = new Gson();
        final var post = gson.fromJson(body, Post.class);
        final var data = service.save(post);
        printResponseData(response, gson.toJson(data));
    }

    private void printResponseData(HttpServletResponse response, String s) throws IOException {
        response.setContentType(APPLICATION_JSON);
        response.getWriter().print(s);
    }

    public void removeById(long id, HttpServletResponse response) throws IOException {
        service.removeById(id);
        printResponseData(response, "Post with id=" + id + " was deleted");
    }
}
