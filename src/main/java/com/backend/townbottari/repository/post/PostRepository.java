package com.backend.townbottari.repository.post;

import com.backend.townbottari.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Long> {

}

