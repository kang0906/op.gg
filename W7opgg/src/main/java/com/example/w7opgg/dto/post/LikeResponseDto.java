package com.example.w7opgg.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeResponseDto {
    private int likeNum;

    public static LikeResponseDto toDto(int likeNum) {
        return new LikeResponseDto(
                likeNum
        );
    }
}