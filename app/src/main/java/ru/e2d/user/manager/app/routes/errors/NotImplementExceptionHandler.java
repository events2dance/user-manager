package ru.e2d.user.manager.app.routes.errors;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import ru.e2d.user.manager.commons.ApiError;
import ru.e2d.user.manager.commons.NotImplementException;
import spark.ExceptionHandler;
import spark.Request;
import spark.Response;
import spark.Service;

@AllArgsConstructor(staticName = "of")
public class NotImplementExceptionHandler implements ExceptionHandler<NotImplementException> {
    private final Gson gson;

    public static void inject(Service http, Gson gson) {
        http.exception(NotImplementException.class, of(gson));
    }

    @Override
    public void handle(NotImplementException exception, Request request, Response response) {
        String message = String.format(
                "method %s %s is not implemented",
                exception.getMethod().name(),
                exception.getPath());
        ApiError error = ApiError.of(
                ApiError.ErrorCode.NO_IMPLEMENTED,
                message);

        response.status(501);
        response.body(gson.toJson(error));
    }
}
