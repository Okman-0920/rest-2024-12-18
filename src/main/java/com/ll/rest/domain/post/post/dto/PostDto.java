package com.ll.rest.domain.post.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ll.rest.domain.post.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostDto {
    private long id;

    @JsonProperty("createDatetime")
    private LocalDateTime createDate;

    @JsonProperty("modifyDatetime")
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
}
