package com.backend.townbottari.domain.post;

import com.backend.townbottari.domain.BaseTimeEntity;
import com.backend.townbottari.domain.user.User;
import io.swagger.models.auth.In;
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
public class Post extends BaseTimeEntity {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(name = "is_end", nullable = false)
    @ColumnDefault("False")
    private Boolean isEnd;

    @Column(name = "arrive_addr", nullable = false)
    private String arriveAddr;

    @Column(name = "way_addr", nullable = false)
    private String wayAddr;

    @Column(name = "arrive_time", nullable = false)
    private LocalDateTime arriveTime;

    @Column(name = "hope_num", nullable = false)
    private Integer hopeNum;

    @Column(name = "end_num", nullable = false)
    private Integer endNum;

    @Column(nullable = false)
    private Integer charge;

    @Column(name = "contact_url", nullable = false)
    private String contactUrl;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static Post createPost(String title, String content, String arriveAddr, String wayAddr, LocalDateTime arriveTime, Integer hopeNum, Integer endNum, Integer charge, String contactUrl, User user) {
        return Post.builder()
                .title(title)
                .content(content)
                .isEnd(Boolean.FALSE)
                .arriveAddr(arriveAddr)
                .wayAddr(wayAddr)
                .arriveTime(arriveTime)
                .hopeNum(hopeNum)
                .endNum(endNum)
                .charge(charge)
                .contactUrl(contactUrl)
                .user(user)
                .build();
    }

}