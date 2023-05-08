package com.backend.townbottari.domain.user;


import com.backend.townbottari.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;


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


    public static User createKakaoUser(String kakaoId, String nickname) {
        return User.builder()
                .socialId(kakaoId)
                .nickname(nickname)
                .role(Role.KAKAO)
                .money(0L)
                .build();
    }

}