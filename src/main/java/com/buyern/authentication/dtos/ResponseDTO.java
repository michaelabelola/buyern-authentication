package com.buyern.authentication.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO<T> {
    /**
     * "00"= successful,
     * "03"= Bad Request,
     * "04"= entity already exist,
     * "05"= record not found,
     * "91"= other errors,
     */
    private String code;
    private String message;
    private T data;
    private String help;

    public ResponseDTO(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseDTO(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class OfString extends ResponseDTO<String> {
        public OfString() {
        }

        public OfString(String code, String message) {
            super(code, message);
        }

        public OfString(String code, String message, String data) {
            super(code, message, data);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class OfObject extends ResponseDTO<Object> {
        public OfObject(String code, String message) {
            super(code, message);
        }

        public OfObject(String code, String message, Object data) {
            super(code, message, data);
        }
    }
}

