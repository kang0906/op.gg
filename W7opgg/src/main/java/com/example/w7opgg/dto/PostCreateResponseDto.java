package com.example.w7opgg.dto;

import com.example.w7opgg.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NotBlank
public class PostCreateResponseDto {
    private int id;
    private String title;
    private String content;
    private String writer;
    private String image;

    public static PostCreateResponseDto toDto(Post post){
        return new PostCreateResponseDto(post.getId(), post.getTitle(), post.getContent(), post.getName(), post.getImgUrl());
    }
}
