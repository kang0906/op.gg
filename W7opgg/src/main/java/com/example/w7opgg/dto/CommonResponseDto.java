package com.example.w7opgg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonResponseDto<T> {
    private boolean result;
    private int code;
    private T data;
}
