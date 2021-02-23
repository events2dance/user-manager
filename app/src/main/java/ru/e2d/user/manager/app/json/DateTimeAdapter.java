package ru.e2d.user.manager.app.json;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class DateTimeAdapter implements JsonDeserializer<LocalDateTime>, JsonSerializer<LocalDateTime> {
    private final ZoneId utc = ZoneId.of(ZoneOffset.UTC.getId());

    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return Instant
                .ofEpochSecond(json.getAsLong())
                .atZone(utc)
                .toLocalDateTime();
    }

    @Override
    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toEpochSecond(ZoneOffset.UTC));
    }
}
