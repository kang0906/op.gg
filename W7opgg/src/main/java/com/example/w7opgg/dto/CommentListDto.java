package com.example.w7opgg.dto;

import com.example.w7opgg.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentListDto {
    private boolean correctComment;
    private int id;
    private String comment;
    private String name;
    private LocalDateTime time;

    public CommentListDto(Comment comment, boolean correctComment){
        this.correctComment = correctComment;
        this.id = comment.getId();
        this.comment = comment.getContent();
        this.name = comment.getName();
        this.time = comment.getWriteTime();
    }

}
