package com.example.w7opgg.controller;

import com.example.w7opgg.dto.CommonResponseDto;
import com.example.w7opgg.dto.PostCreateResponseDto;
import com.example.w7opgg.entity.Member;
import com.example.w7opgg.entity.PostCreateRequestDto;
import com.example.w7opgg.exception.RequestException;
import com.example.w7opgg.repository.MemberRepository;
import com.example.w7opgg.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

import static com.example.w7opgg.exception.ExceptionType.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api")
public class PostController {

    private final PostService postService;
    private final MemberRepository userRepository;

    // 게시물 작성
    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
//    public Response create(@Valid @RequestBody PostCreateRequestDto postCreateRequestDto) {
    public CommonResponseDto create(@RequestPart(value="file",required = false) MultipartFile multipartFile,
                                    @Valid @RequestPart PostCreateRequestDto postCreateRequestDto) throws IOException {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new RequestException(ACCESS_DENIED_EXCEPTION));

        return new CommonResponseDto<PostCreateResponseDto>(true, 200, postService.create(postCreateRequestDto, member, multipartFile));
    }
}
