package com.backend.townbottari.domain.form;

import com.backend.townbottari.domain.BaseTimeEntity;
import com.backend.townbottari.domain.post.Post;
import com.backend.townbottari.domain.user.Role;
import com.backend.townbottari.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;


@DynamicInsert
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Form extends BaseTimeEntity {

    @Column(nullable = false)
    private String content;

    @Column(name = "store_addr", nullable = false)
    private String storeAddr;

    @Column(nullable = false)
    private Integer charge;

    @Column(name = "is_accept", nullable = false)
    @ColumnDefault("False")
    private Boolean isAccept;

    @Column(name = "is_cancel", nullable = false)
    @ColumnDefault("False")
    private Boolean isCancel;

    @Enumerated(EnumType.STRING)
    @Column(name = "cancel_reason", nullable = true)
    private CancelReason cancelReason;

    @Enumerated(EnumType.STRING)
    @Column(name = "cancel_position", nullable = true)
    private CancelPosition cancelPosition;

    @Column(name = "is_end", nullable = false)
    @ColumnDefault("False")
    private Boolean isEnd;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(targetEntity = Post.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public static Form createForm(String content, String storeAddr, Integer charge, User user, Post post) {
        return Form.builder()
                .content(content)
                .storeAddr(storeAddr)
                .charge(charge)
                .isAccept(Boolean.FALSE)
                .isCancel(Boolean.FALSE)
                .cancelReason(null)
                .cancelPosition(null)
                .isEnd(Boolean.FALSE)
                .user(user)
                .post(post)
                .build();
    }

}