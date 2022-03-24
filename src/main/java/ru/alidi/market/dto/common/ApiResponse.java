package ru.alidi.market.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.springframework.lang.Nullable;

import static com.google.common.base.Preconditions.checkArgument;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ApiResponse <T> {

    @ApiModelProperty(value = "Код ответа", required = true)
    @JsonProperty("code")
    private final String code;

    @ApiModelProperty(value = "Описание кода ответа")
    @JsonProperty("description")
    @Nullable
    private final String description;

    @ApiModelProperty(value = "Результат")
    @Nullable
    @JsonProperty("result")
    private final T result;

    public static ApiResponse success(String description) {
        return new ApiResponse<>(ResponseCode.OK.getValue(), description, null);
    }

    public static <T> ApiResponse<T> success(T result) {
        return new ApiResponse<>(ResponseCode.OK.getValue(), null, result);
    }

    public static ApiResponse failure(ResponseCode code, String description) {
        checkArgument(!code.isSuccess());
        return new ApiResponse<>(code.getValue(), description, null);
    }

    private ApiResponse(@JsonProperty("code") String code,
                        @JsonProperty("description") @Nullable String description,
                        @JsonProperty("result") @Nullable T result) {
        if (!code.equals(ResponseCode.OK.getValue())) {
            checkArgument(description != null && result == null);
        }
        this.code = code;
        this.description = description;
        this.result = result;
    }
}
