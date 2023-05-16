package com.backend.townbottari.domain.review;

import com.backend.townbottari.domain.BaseTimeEntity;
import com.backend.townbottari.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;


@DynamicInsert
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Review extends BaseTimeEntity {

    @Column(nullable = true)
    private String content;

    @Column(nullable = false)
    private Integer score;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "target_user_id")
    private User targetUser;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_user_id")
    private User reviewer;

    public static Review createReview(String content, Integer score, User targetUser, User reviewer) {
        return Review.builder()
                .content(content)
                .score(score)
                .targetUser(targetUser)
                .reviewer(reviewer)
                .build();
    }

}