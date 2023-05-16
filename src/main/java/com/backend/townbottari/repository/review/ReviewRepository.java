package com.backend.townbottari.repository.review;

import com.backend.townbottari.domain.form.Form;
import com.backend.townbottari.domain.review.Review;
import com.backend.townbottari.domain.user.Role;
import com.backend.townbottari.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByTargetUserId(@Param("targetUserId") Long targetUserId, Pageable pageable);
    Page<Review> findByReviewerId(@Param("reviewerId") Long reviewerId, Pageable pageable);
    Optional<Review> findByTargetUserIdAndReviewerId(Long targetUserId, Long reviewerId);

}
