package com.ll.rest.domain.post.post.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.rest.domain.post.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostDto {
    private long id;

    @JsonIgnore // 제이슨으로 바뀔때 보이지 않게 하라
    private LocalDateTime createDate;

    @JsonIgnore
    private LocalDateTime modifyDate;

    private String title;
    private String content;

    public PostDto(Post post) {
        this.id = post.getId();
        this.createDate = post.getCreateDate();
        this.modifyDate = post.getModifiedDate();
        this.title = post.getTitle();
        this.content = post.getTitle();
    }

    // get 으로 시작 하는 getter메서드는 api에 노출됨
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }
}
