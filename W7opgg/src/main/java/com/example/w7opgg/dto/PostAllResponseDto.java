package com.example.w7opgg.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostAllResponseDto {

    private Integer id;
    private String title;
    private String content;
    private String image;
    private String name;
    private String Time;
    private Integer likeNum;
    private Integer commentsNum;
}