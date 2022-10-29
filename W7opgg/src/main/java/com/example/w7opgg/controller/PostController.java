package com.example.w7opgg.controller;

import com.example.w7opgg.dto.CommonResponseDto;
import com.example.w7opgg.dto.PostCreateResponseDto;
import com.example.w7opgg.dto.PostRequestDto;
import com.example.w7opgg.entity.Member;
import com.example.w7opgg.entity.PostCreateRequestDto;
import com.example.w7opgg.exception.RequestException;
import com.example.w7opgg.repository.MemberRepository;
import com.example.w7opgg.service.PostService;
//import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
    public CommonResponseDto create(@RequestPart(value = "file", required = false) MultipartFile multipartFile,
                                    @Valid @RequestPart PostCreateRequestDto postCreateRequestDto) throws IOException {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new RequestException(ACCESS_DENIED_EXCEPTION));

        return new CommonResponseDto<PostCreateResponseDto>(true, 200, postService.create(postCreateRequestDto, member, multipartFile));
    }

    // 게시글 전체 조회
    @GetMapping("/post")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> AllShow() {
        return postService.AllShow();
    }

    //    public CommonResponseDto<PostAllResponseDto> AllShow(@PathVariable Long folderId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return CommonResponseDto.result(postService.AllShow(folderId, userDetails.getAccount()));
//    }
    //게시글 상세 조회
    @GetMapping("/post/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> DetailShow(@PathVariable Integer id) {
        return postService.DetailShow(id);
    }

    //게시글 수정
    @PutMapping("/api/auth/post/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> UpdatePost(@PathVariable Integer id,
                                        @RequestBody PostRequestDto postRequestDto,
                                        HttpServletRequest request) {
        return postService.UpdatePost(id, postRequestDto, request);
    }

    //게시글 삭제
    @DeleteMapping(value = "/api/auth/post/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> DeletePost(@PathVariable Integer id, HttpServletRequest request) {
        return postService.DeletePost(id, request);
    }

    // 게시글 좋아요
    @PostMapping("/post/{id}/like")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponseDto like(@PathVariable int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new RequestException(ACCESS_DENIED_EXCEPTION));
        return CommonResponseDto.result(postService.like(id, member));
    }

    // 인기 게시글 조회
    @GetMapping("/post/popular")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponseDto PopularPost(@PageableDefault(size = 5, sort = "like", direction = Sort.Direction.DESC) Pageable pageable) {
        return CommonResponseDto.result(postService.findPopularPost(pageable));
    }
}


