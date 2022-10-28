package com.example.w7opgg.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.example.w7opgg.dto.PostCreateResponseDto;
import com.example.w7opgg.entity.Post;
import com.example.w7opgg.dto.PostCreateRequestDto;
import com.example.w7opgg.entity.Member;
import com.example.w7opgg.repository.PostRepository;
import com.example.w7opgg.s3.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Objects;


@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final AmazonS3Client amazonS3Client;


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
}
