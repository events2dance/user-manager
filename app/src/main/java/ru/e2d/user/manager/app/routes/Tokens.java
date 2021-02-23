package ru.e2d.user.manager.app.routes;

import lombok.AllArgsConstructor;
import ru.e2d.user.manager.commons.HttpMethod;
import ru.e2d.user.manager.commons.NotImplementException;
import spark.Service;


@AllArgsConstructor(staticName = "of")
public class Tokens implements RouteInjection {
    private final Service http;

    @Override
    public void injection() {
        http.get("/tokens", (request, response) -> {
            throw NotImplementException.of(HttpMethod.GET, "/tokens");
        });
        http.post("/tokens", (request, response) -> {
            throw NotImplementException.of(HttpMethod.POST, "/tokens");
        });
        http.get("/tokens/:token", (request, response) -> {
            throw NotImplementException.of(HttpMethod.GET, "/tokens/:token");
        });
        http.get("/tokens/:token", (request, response) -> {
            throw NotImplementException.of(HttpMethod.DELETE, "/tokens/:token");
        });
    }
}
