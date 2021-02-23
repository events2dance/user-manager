package ru.e2d.user.manager.app.routes;

import lombok.AllArgsConstructor;
import ru.e2d.user.manager.app.routes.stubs.NoImplementedRoute;
import spark.Service;

@AllArgsConstructor(staticName = "of")
public class Roles implements RouteInjection {
    private final Service http;

    @Override
    public void injection() {
        http.put("/users/:id/roles/:roleId", NoImplementedRoute.of());
        http.delete("/users/:id/roles/:roleId", NoImplementedRoute.of());
        http.get("/roles", NoImplementedRoute.of());
        http.post("/roles", NoImplementedRoute.of());
        http.get("/roles/:id", NoImplementedRoute.of());
        http.put("/roles/:id", NoImplementedRoute.of());
        http.delete("/roles/:id", NoImplementedRoute.of());
    }
}
