package com.example.lab12.Controller;

import com.example.lab12.Api.ApiResponse;
import com.example.lab12.Model.Blog;
import com.example.lab12.Model.User;
import com.example.lab12.Service.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PostMapping("/add")
    public ResponseEntity<?> addBlog(@AuthenticationPrincipal User user, @RequestBody @Valid Blog blog) {
        blogService.addBlog(user.getId(), blog);
        return ResponseEntity.status(200).body(new ApiResponse("Blog added"));
    }

    @PutMapping("/edit/{blogId}")
    public ResponseEntity<?> editBlog(@AuthenticationPrincipal User user, @PathVariable Integer blogId, @RequestBody @Valid Blog blog) {
        blogService.editBlog(user.getId(), blogId, blog);
        return ResponseEntity.status(200).body(new ApiResponse("Blog edited"));
    }

    @DeleteMapping("/delete/{blogId}")
    public ResponseEntity<?> deleteBlog(@AuthenticationPrincipal User user, @PathVariable Integer blogId) {
        blogService.deleteBlog(user.getId(), blogId);
        return ResponseEntity.status(200).body(new ApiResponse("Blog deleted"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getUserBlogs(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(blogService.getUserBlogs(user.getId()));
    }

    @GetMapping("/get/id/{blogId}")
    public ResponseEntity<?> getBlogById(@AuthenticationPrincipal User user, @PathVariable Integer blogId) {
        return ResponseEntity.status(200).body(blogService.getBlogById(user.getId(), blogId));
    }

    @GetMapping("/get/title/{title}")
    public ResponseEntity<?> getBlogByTitle(@AuthenticationPrincipal User user, @PathVariable String title) {
        return ResponseEntity.status(200).body(blogService.getBlogByTitle(user.getId(), title));
    }

    @GetMapping("/admin/get")
    public ResponseEntity<?> adminGetAllBlogs(@AuthenticationPrincipal User admin) {
        return ResponseEntity.status(200).body(blogService.adminGetAllBlogs(admin.getId()));
    }
}