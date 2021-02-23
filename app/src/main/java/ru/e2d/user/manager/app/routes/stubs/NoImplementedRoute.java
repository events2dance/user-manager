package ru.e2d.user.manager.app.routes.stubs;

import lombok.Value;
import ru.e2d.user.manager.commons.HttpMethod;
import ru.e2d.user.manager.commons.NotImplementException;
import spark.Request;
import spark.Response;
import spark.Route;

@Value(staticConstructor = "of")
public class NoImplementedRoute implements Route {

    @Override
    public Object handle(Request request, Response response) throws Exception {
        throw NotImplementException.of(HttpMethod.valueOf(request.requestMethod()), request.matchedPath());
    }
}
