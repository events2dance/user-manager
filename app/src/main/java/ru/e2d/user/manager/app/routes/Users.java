package ru.e2d.user.manager.app.routes;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import ru.e2d.user.manager.commons.NotImplementException;
import spark.*;

import static ru.e2d.user.manager.commons.HttpMethod.GET;

@RequiredArgsConstructor(staticName = "of")
public class Users implements RouteInjection {
    private final Service http;

    @Override
    public void injection() {
        http.get("/users", GetList.of("/users"));
        http.post("/users", Post.of("/users"));

        http.get("/users/:id", GetById.of("/users/:id"));
        http.put("/users/:id", Put.of("/users/:id"));
        http.delete("/users/:id", Delete.of("/users/:id"));
    }

    @AllArgsConstructor(staticName = "of")
    static class GetList implements Route {
        private final String path;

        @Override
        public Object handle(Request request, Response response) throws Exception {
            throw NotImplementException.of(GET, path);
        }
    }

    @AllArgsConstructor(staticName = "of")
    static class Post implements Route {
        private final String path;

        @Override
        public Object handle(Request request, Response response) throws Exception {
            throw NotImplementException.of(GET, path);
        }
    }

    @AllArgsConstructor(staticName = "of")
    static class GetById implements Route {
        private final String path;

        @Override
        public Object handle(Request request, Response response) throws Exception {
            throw NotImplementException.of(GET, path);
        }
    }

    @AllArgsConstructor(staticName = "of")
    static class Put implements Route {
        private final String path;

        @Override
        public Object handle(Request request, Response response) throws Exception {
            throw NotImplementException.of(GET, path);
        }
    }

    @AllArgsConstructor(staticName = "of")
    static class Delete implements Route {
        private final String path;

        @Override
        public Object handle(Request request, Response response) throws Exception {
            throw NotImplementException.of(GET, path);
        }
    }

}
