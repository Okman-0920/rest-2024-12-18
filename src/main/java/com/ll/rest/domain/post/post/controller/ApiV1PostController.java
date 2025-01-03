package com.ll.rest.domain.post.post.controller;

import com.ll.rest.domain.post.post.dto.PostDto;
import com.ll.rest.domain.post.post.entity.Post;
import com.ll.rest.domain.post.post.service.PostService;
import com.ll.rest.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class ApiV1PostController {
    private final PostService postService;

    // 다건 조회
    @GetMapping
    public List<Post> getItems() {
        return postService.findAllByOrderByIdDesc();
    }

    // 단건 조회
    @GetMapping("/{id}")
    public PostDto getItem(@PathVariable long id) {
        return postService.findById(id)
                .map(PostDto::new)
                .orElseThrow();
    }

    // 삭제
    @DeleteMapping("/{id}")
    public RsData deleteItem(@PathVariable long id) {
        Post post = postService.findById(id).get();

        postService.delete(post);

        return new RsData(
                "200-1",
                "%d번 글이 삭제되었습니다".formatted(id)
        );
    }

    // 수정
    public record PostModifyBody (
            @NotBlank (message = "제목을 입력하세요")
            @Length (min = 2, message = "2자이상 입력하세요")
            String title,

            @NotBlank (message = "내용을 입력하세요")
            @Length (min = 2, message = "2자이상 입력하세요")
            String content
    ) {
    }

    @PutMapping("/{id}")
    @Transactional
    public RsData modifyItem(
            @PathVariable long id,
            @RequestBody @Valid PostModifyBody reqBody
            /* @RequestBody: HTTP 요청(request)의 본문(Body)을 Java객체로 변환
            쉽게말해서 나(클라이언트)가 입력한 값을 서버로 전송하기 위해 사용
            왜냐면, 클라이언트는 웹 브라우저 상에서 정보를 입력할 것이기 때문 */
            // @Valid : 클라이언트의 입력값에 대한 유효성 검사
    ) {

        Post post = postService.findById(id).get();
        // 수정하는거니까 이미 그 저장된 글임, 그래서 get()으로 불러옴

        postService.modify(post, reqBody.title(), reqBody.content());
        // 사용자가 수정할 post의 id값을 가져옴
        // 사용자가 입력한 title과, content값을 인자로 가져옴

        return new RsData(
                "200-1",
                "%d번 글이 수정되었습니다".formatted(id)
        );
    }

    // 작성
    public record PostWriteBody (
            @NotBlank (message = "내용을 입력하세요")
            @Length (min = 2)
            String title,

            @NotBlank (message = "내용을 입력하세요")
            @Length (min = 2)
            String content
    ) {
    }

    @PostMapping("/write")
    public RsData writeItem(
            @RequestBody @Valid PostWriteBody reqBody
    ) {
        Post post = postService.write(reqBody.title, reqBody.content);

        return new RsData(
                "200-1",
                "%d번 글이 작성되었습니다".formatted(post.getId())
        );
    }
}
