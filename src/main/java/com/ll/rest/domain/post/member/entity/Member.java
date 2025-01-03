package com.ll.rest.domain.post.member.entity;

import com.ll.rest.global.jpa.entity.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseTime {
    @Column(unique = true, length = 20)
    private String username;

    @Column(length = 30)
    private String password;

    @Column(length = 10)
    private String nickname;
}
