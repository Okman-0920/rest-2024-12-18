package com.ll.rest.domain.post.post.controller;

import com.ll.rest.domain.post.post.entity.Post;
import com.ll.rest.domain.post.post.service.PostService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // 포장해서 반환할꺼 없고, 찾아서 그냥 반환시키면 되기 때문에 아래꺼
    /* @GetMapping("/{id}")
    public Optional<Post> getItem(@PathVariable long id) {
        return postService.findById(id);
    } */

    // 단건 조회
    @GetMapping("/{id}")
    public Post getItem2(@PathVariable long id) {
        // testCode
        /*Optional<Integer> t2 = Optional.of(2);
        t2.get();
        Optional<Integer> t = Optional.empty();
        t.get();*/
        return postService.findById(id).get();
    }

    // 삭제
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteItem(@PathVariable long id) {
        Post post = postService.findById(id).get();

        postService.delete(post);

        // Object: 뭐든 넣어도 됨
        Map<String, Object> rsData = new HashMap<>();
        rsData.put("resultCode", "200-1");
        rsData.put("msg", "%d번 글을 삭제하였습니다.".formatted(id));

        return rsData;
    }

    @AllArgsConstructor
    @Getter
    public static class PostModifyBody {
        private String title;
        private String content;
    }

    @PutMapping("/{id}")
    public Map<String, Object> modifyItem(
            @PathVariable long id,
            @RequestBody PostModifyBody reqBody
            // @RequestBody: HTTP 요청의 본문(Body)을 Java객체로 변환
            // 쉽게말해서 나(클라이언트)가 입력한 값을 서버로 전송하기 위해 사용
            // 왜냐면, 클라이언트는 웹 브라우저 상에서 정보를 입력할 것이기 때문
    ) {

        Post post = postService.findById(id).get();
        // 수정하는거니까 이미 그 저장된 글임, 그래서 get()으로 불러옴

        postService.modify(post, reqBody.getTitle(), reqBody.getContent());
        // 사용자가 수정할 post의 id값을 가져옴
        // 사용자가 입력한 title과, content값을 인자로 가져옴

        Map<String, Object> rsData = new HashMap<>();
        rsData.put("resultCode", "200-1");
        rsData.put("msg", "%d번 글을 수정하였습니다.".formatted(id));

        return rsData;
    }
}
