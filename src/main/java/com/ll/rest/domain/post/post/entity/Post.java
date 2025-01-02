package com.ll.rest.domain.post.post.entity;

import com.ll.rest.global.baseInit.entity.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post extends BaseTime {
    @Column(length = 30)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;
}
