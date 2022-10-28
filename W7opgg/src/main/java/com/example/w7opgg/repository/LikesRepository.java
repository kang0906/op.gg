package com.example.w7opgg.repository;

import com.example.w7opgg.entity.Likes;
import com.example.w7opgg.entity.Post;
import com.example.w7opgg.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Integer> {
    Likes findByPostAndUser(Post post, Member member);

    Page<Likes> findByUserId(Pageable pageable, int id);
}
