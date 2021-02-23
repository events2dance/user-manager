package ru.e2d.user.manager.commons;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class NotImplementException extends RuntimeException {
    @Getter @NonNull private HttpMethod method;
    @Getter @NonNull private String path;
}
