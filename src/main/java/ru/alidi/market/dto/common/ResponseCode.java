package ru.alidi.market.dto.common;

import lombok.Getter;

@Getter
public enum ResponseCode {

    OK("ok", true),
    INTERNAL_ERROR("internal_error", false),
    VALIDATION_ERROR("validation_error", false),
    UNAUTHORIZED_ERROR("unauthorized_error", false);

    private final String value;

    private final boolean success;

    ResponseCode(String value, boolean success) {
        this.value = value;
        this.success = success;
    }
}
