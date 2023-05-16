package com.backend.townbottari.domain.user;


import com.backend.townbottari.domain.BaseTimeEntity;
import com.backend.townbottari.domain.form.Form;
import com.backend.townbottari.domain.post.Post;
import com.backend.townbottari.domain.review.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@DynamicInsert
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(
        name="Users"
)
public class User extends BaseTimeEntity {
    @Column(name = "social_id", nullable = true)
    private String socialId;

    @Column(nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long money;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Form> forms = new ArrayList<>();

    @OneToMany(mappedBy = "targetUser", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    public static User createKakaoUser(String kakaoId, String nickname) {
        return User.builder()
                .socialId(kakaoId)
                .nickname(nickname)
                .role(Role.KAKAO)
                .money(0L)
                .build();
    }

}