package ru.e2d.user.manager.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.e2d.user.manager.app.json.DateTimeAdapter;
import spark.ResponseTransformer;

import java.time.LocalDateTime;

public class JsonTransformer implements ResponseTransformer {

    private final Gson gson;

    public JsonTransformer(Gson gson) {
        this.gson = gson;
    }

    public static JsonTransformer build() {
        GsonBuilder builder = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(LocalDateTime.class, new DateTimeAdapter());

        return new JsonTransformer(builder.create());
    }

    @Override
    public String render(Object model) {
        return gson.toJson(model);
    }

    public Gson getGson() {
        return gson;
    }
}
