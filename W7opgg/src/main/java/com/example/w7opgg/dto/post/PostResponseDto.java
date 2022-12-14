package com.example.w7opgg.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {

    private Integer id;
    private String title;
    private String content;
    private String image;
    private String name;
    private String time;
    private Integer likeNum;
//    private List<CommentResponseDto> commentResponseDtoList;

}
