package ru.e2d.user.manager.commons;

import lombok.Value;

@Value(staticConstructor = "of")
public class ApiError {
    public enum ErrorCode {
        UNKNOWN,
        NO_IMPLEMENTED
    }
    ErrorCode code;
    String message;
}
