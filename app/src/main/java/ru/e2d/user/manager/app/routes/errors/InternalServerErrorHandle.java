package ru.e2d.user.manager.app.routes.errors;

import ru.e2d.user.manager.commons.ApiError;
import spark.Request;
import spark.Response;
import spark.Route;

public class InternalServerErrorHandle implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        response.status(500);
        return ApiError.of(ApiError.ErrorCode.UNKNOWN, "Unknown error!");
    }
}
