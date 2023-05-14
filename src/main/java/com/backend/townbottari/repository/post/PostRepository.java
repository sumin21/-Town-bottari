package com.backend.townbottari.repository.post;

import com.backend.townbottari.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;


public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByOrderByCreatedDateAsc(Pageable pageable);
    Page<Post> findByTitleContainingOrderByCreatedDateAsc(String title, Pageable pageable);
    Page<Post> findByArriveAddrOrderByCreatedDateAsc(String arriveAddr, Pageable pageable);
    Page<Post> findByTitleContainingAndArriveAddrOrderByCreatedDateAsc(String title, String arriveAddr, Pageable pageable);

    Page<Post> findByOrderByChargeAsc(Pageable pageable);
    Page<Post> findByTitleContainingOrderByChargeAsc(String title, Pageable pageable);
    Page<Post> findByArriveAddrOrderByChargeAsc(String arriveAddr, Pageable pageable);
    Page<Post> findByTitleContainingAndArriveAddrOrderByChargeAsc(String title, String arriveAddr, Pageable pageable);
    Page<Post> findByUserId(@Param("userId") Long userId, Pageable pageable);


}

