package com.ll.rest.domain.post.post.controller;

import com.ll.rest.domain.post.post.entity.Post;
import com.ll.rest.domain.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class ApiV1PostController {
    private final PostService postService;

    @GetMapping
    public List<Post> getItems() {
        return postService.findAllByOrderByIdDesc();
    }

    // 질문(to.강사님): getItem 코드를 형을 Post로 하신 이유? 의도된 것이라면 이유가 무었인가요?
/*    @GetMapping("/{id}")
    public Optional<Post> getItem(@PathVariable long id) {
        return postService.findById(id);
    }*/

    // 강사님 코드
    @GetMapping("/{id}")
    public Post getItem(@PathVariable long id) {
        // testCode
        /*Optional<Integer> t2 = Optional.of(2);
        t2.get();
        Optional<Integer> t = Optional.empty();
        t.get();*/
        return postService.findById(id).get();
    }

    @GetMapping("/{id}")
    public String deleteItem(@PathVariable long id) {
        Post post = postService.findById(id).get();
        postService.delete(post);

        return "삭제가 완료되었습니다";
    }

}
