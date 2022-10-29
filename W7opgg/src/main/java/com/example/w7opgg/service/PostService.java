package com.example.w7opgg.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;

import com.example.w7opgg.dto.*;
import com.example.w7opgg.entity.*;
import com.example.w7opgg.exception.ExceptionType;
import com.example.w7opgg.exception.RequestException;
import com.example.w7opgg.repository.CommentRepository;
import com.example.w7opgg.repository.LikesRepository;
import com.example.w7opgg.dto.PostCreateResponseDto;
import com.example.w7opgg.entity.Post;
import com.example.w7opgg.dto.PostCreateRequestDto;
import com.example.w7opgg.entity.Member;
import com.example.w7opgg.repository.PostRepository;
import com.example.w7opgg.s3.CommonUtils;
import com.example.w7opgg.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final AmazonS3Client amazonS3Client;
    private final TokenProvider tokenProvider;
    private final LikesRepository likesRepository;
    private final CommentRepository commentRepository;

    //사용자 유효성 검사
    public Member validateMember(HttpServletRequest request){
        if(!tokenProvider.validateToken(request.getHeader("Refresh-Token"))){
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
    //게시판이 있는지 없는지
    public Post isPresentPost(Integer id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElse(null);
    }
        //url받아오기
    public String getImageUrlByPost(Post post) {

        return post.getImgUrl();
    }

    // 게시글 작성
    @Transactional
    public PostCreateResponseDto create(PostCreateRequestDto postCreateRequestDto, Member member, MultipartFile multipartFile) throws IOException {

        String imgurl = "";
        String bucket = "woods0611";


        if (!multipartFile.isEmpty()) {
            String fileName = CommonUtils.buildFileName(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());

            byte[] bytes = IOUtils.toByteArray(multipartFile.getInputStream());
            objectMetadata.setContentLength(bytes.length);
            ByteArrayInputStream byteArrayIs = new ByteArrayInputStream(bytes);

            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, byteArrayIs, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            imgurl = amazonS3Client.getUrl(bucket, fileName).toString();
        }

        Post post = postRepository.save(new Post(postCreateRequestDto.getTitle(), postCreateRequestDto.getContent(), member, imgurl)); // + imgUrl
        return PostCreateResponseDto.toDto(post);
    }

    //게시글 전체 조회
    @Transactional(readOnly = true)
    public ResponseEntity<?> AllShow() {
        List<PostAllResponseDto> postAllList = new ArrayList<>();
        List<Post> postList = postRepository.findAllPostBy();
        for(Post post: postList){
            postAllList.add(
                    PostAllResponseDto.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .name(post.getName())
                            .Time(post.getTitle())
                            .likeNum(post.getLikeNum())
                            .commentsNum(post.getCommentNum())
                            .build()
            );
        }
        return ResponseEntity.ok().body(CommonResponseDto.result(postAllList));
    }

    //게시글 상세 조회
    @Transactional(readOnly = true)
    public ResponseEntity<?> DetailShow(Integer id) {
        Post post = isPresentPost(id);
        if (null == post) {
            throw new RequestException(ExceptionType.NOT_FOUND_EXCEPTION);
        }
        String url = getImageUrlByPost(post);
        return ResponseEntity.ok().body(CommonResponseDto.result(
                PostResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .image(url)
                        .name(post.getName())
                        .Time(post.getTitle())
                        .likeNum(post.getLikeNum())
                        .build())
        );
    }

        //게시글 수정
        @Transactional
        public ResponseEntity<?> UpdatePost (Integer id, PostRequestDto postRequestDto, HttpServletRequest request){
            if (null == request.getHeader("Authorization")) {
                throw new RequestException(ExceptionType.AUTHORIZATION_IS_EMPTY);
            }
            if (null == request.getHeader("Refresh-Token")) {
                throw new RequestException(ExceptionType.REFRESHTOKEN_IS_EMPTY);
            }

            Member member = validateMember(request);
            if (null == member) {
                throw new RequestException(ExceptionType.INVALID_TOKEN);
            }

            Post post = isPresentPost(id);
            if (null == post) {
                throw new RequestException(ExceptionType.NOT_FOUND_EXCEPTION);
            }

            post.updatePost(postRequestDto);
            postRepository.save(post);
            return ResponseEntity.ok().body(CommonResponseDto.result(
                    PostResponseDto.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .image(post.getImgUrl())
                            .name(post.getName())
                            .Time(post.getTitle())
                            .likeNum(post.getLikeNum())
                            .build())
            );
        }

        //게시글 삭제
        @Transactional
        public ResponseEntity<?> DeletePost(Integer id, HttpServletRequest request) {
            if (null == request.getHeader("Authorization")) {
                throw new RequestException(ExceptionType.AUTHORIZATION_IS_EMPTY);
            }
            if (null == request.getHeader("Refresh-Token")) {
                throw new RequestException(ExceptionType.REFRESHTOKEN_IS_EMPTY);
            }

            Member member = validateMember(request);
            if (null == member) {
                throw new RequestException(ExceptionType.INVALID_TOKEN);
            }

            Post post = isPresentPost(id);
            if (null == post) {
                throw new RequestException(ExceptionType.NOT_FOUND_EXCEPTION);
            }
            postRepository.delete(post);
            return ResponseEntity.ok().body(CommonResponseDto.result("delete success"));
        }

    // 게시글 좋아요
    @Transactional
    public LikeResponseDto like(int id, Member member) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RequestException(ExceptionType.NOT_FOUND_EXCEPTION));

        if (likesRepository.findByPostAndMember(post, member) == null) {
            post.PlusLike();
            Likes likes = new Likes(post, member);
            likesRepository.save(likes);
        } else {
            Likes likes = likesRepository.findByPostAndMember(post, member);
            post.MinusLike();
            likesRepository.delete(likes);
        }
        return LikeResponseDto.toDto(post.getLikeNum());
    }

    // 인기 게시글 조회
    @Transactional(readOnly = true)
    public List<PostSimpleDto> findPopularPost(Pageable pageable) {
        Page<Post> posts = postRepository.findByLikesGreaterThanEqual(pageable, 5);
        List<PostSimpleDto> postSimpleDtos = new ArrayList<>();
        posts.stream().forEach(i -> postSimpleDtos.add(new PostSimpleDto().toDto(i, commentRepository.findByPostId(i.getId()).size())));
        return postSimpleDtos;
    }
}