package com.example.w7opgg.dto.post;

import com.example.w7opgg.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

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

    public PostAllResponseDto toDto(Post post, int commentNum) {
        return new PostAllResponseDto(post.getId(), post.getTitle(), post.getContent(),
                post.getImgUrl(), post.getMember().getName(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd").format(post.getWriteTime()),
                post.getLikeNum(), commentNum);
    }
}