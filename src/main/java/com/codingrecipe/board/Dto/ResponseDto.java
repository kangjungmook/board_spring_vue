package com.codingrecipe.board.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
    private boolean result;
    private String message;
    private Object data;

    public static ResponseDto setSuccess(String message) {
        return new ResponseDto(true, message, null);
    }

    public static ResponseDto setFailed(String message) {
        return new ResponseDto(false, message, null);
    }
}
