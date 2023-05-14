package com.backend.townbottari.domain.post;

import com.backend.townbottari.domain.BaseTimeEntity;
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
public class Post extends BaseTimeEntity {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(name = "is_end", nullable = false)
    @ColumnDefault("False")
    private boolean isEnd;

    @Column(name = "arrive_addr", nullable = false)
    private String arriveAddr;

    @Column(name = "way_addr", nullable = false)
    private String wayAddr;

    @Column(name = "arrive_time", nullable = false)
    private LocalDateTime arriveTime;

    @Column(name = "hope_num", nullable = false)
    private int hopeNum;

    @Column(name = "end_num", nullable = false)
    private int endNum;

    @Column(nullable = false)
    private int charge;

    @Column(name = "contact_url", nullable = false)
    private String contactUrl;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static Post createPost(String title, String content, String arriveAddr, String wayAddr, LocalDateTime arriveTime, int hopeNum, int endNum, int charge, String contactUrl, User user) {
        return Post.builder()
                .title(title)
                .content(content)
                .isEnd(false)
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