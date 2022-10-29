package com.example.w7opgg.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponseDto {
    private int id;
    private String content;
    private String name;
    private LocalDateTime Time;
}
<<<<<<< HEAD
=======

>>>>>>> 91682ca (post,like -> 2차 수정)