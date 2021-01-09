package springPack.servlet;

import springPack.controller.PostController;
import springPack.exception.NotFoundException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springPack.repository.PostRepository;
import springPack.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static springPack.servlet.RequestType.*;

public class MainServlet extends HttpServlet {
    private PostController controller;

    @Override
    public void init() {
        final var context = new AnnotationConfigApplicationContext("springPack");
        controller = context.getBean(PostController.class);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();

            switch (getRequestType(method, path)) {
                case GET_ALL:
                    getAll(resp);
                    return;
                case GET_BY_ID:
                    getById(resp, path);
                    return;
                case ADD:
                    add(resp, req);
                    return;
                case DELETE:
                    delete(resp, path);
                    return;
                default:
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void delete(HttpServletResponse resp, String path) throws IOException {
        final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
        controller.removeById(id, resp);
    }

    private void add(HttpServletResponse resp, HttpServletRequest req) throws IOException, NotFoundException {
        controller.save(req.getReader(), resp);
    }

    private void getById(HttpServletResponse resp, String path) throws IOException, NotFoundException {
        final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
        controller.getById(id, resp);
    }

    private void getAll(HttpServletResponse resp) throws IOException {
        controller.all(resp);
    }

    private RequestType getRequestType(String method, String path) {
        if (method.equals("GET") && path.equals("/api/posts"))
            return GET_ALL;
        if (method.equals("GET") && path.matches("/api/posts/\\d+"))
            return GET_BY_ID;
        if (method.equals("POST") && path.equals("/api/posts"))
            return ADD;
        if (method.equals("DELETE") && path.matches("/api/posts/\\d+"))
            return DELETE;
        return UNKNOWN;
    }

}
