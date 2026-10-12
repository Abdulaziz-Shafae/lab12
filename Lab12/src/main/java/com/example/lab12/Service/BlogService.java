package com.example.lab12.Service;

import com.example.lab12.Api.ApiException;
import com.example.lab12.Model.Blog;
import com.example.lab12.Model.User;
import com.example.lab12.Repository.AuthRepository;
import com.example.lab12.Repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final AuthRepository authRepository;

    public List<Blog> getUserBlogs(Integer userId) {

        User user = authRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        return blogRepository.findAllByUser(user);
    }

    public void addBlog(Integer userId, Blog blogIn) {

        User user = authRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        Blog blog = new Blog();
        blog.setTitle(blogIn.getTitle());
        blog.setBody(blogIn.getBody());
        blog.setUser(user);

        blogRepository.save(blog);
    }

    public Blog getBlogById(Integer userId, Integer blogId) {

        Blog blog = blogRepository.findBlogById(blogId);
        if (blog == null) {
            throw new ApiException("Blog not found");
        }

        if (!blog.getUser().getId().equals(userId)) {
            throw new ApiException("The blog is not yours");
        }

        return blog;
    }

    public void editBlog(Integer userId, Integer blogId, Blog blogIn) {

        Blog blog = blogRepository.findBlogById(blogId);
        if (blog == null) {
            throw new ApiException("Blog not found");
        }

        if (!blog.getUser().getId().equals(userId)) {
            throw new ApiException("The blog is not yours");
        }

        blog.setTitle(blogIn.getTitle());
        blog.setBody(blogIn.getBody());

        blogRepository.save(blog);
    }

    public void deleteBlog(Integer userId, Integer blogId) {

        Blog blog = blogRepository.findBlogById(blogId);
        if (blog == null) {
            throw new ApiException("Blog not found");
        }

        if (!blog.getUser().getId().equals(userId)) {
            throw new ApiException("The blog is not yours");
        }

        blogRepository.delete(blog);
    }

    public List<Blog> getBlogByTitle(Integer userId, String title) {

        User user = authRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        if (title == null || title.isBlank() || title.length() > 25) {
            throw new ApiException("Title must be between 1 and 25 characters");
        }

        return blogRepository.findAllByUserAndTitle(user, title);
    }

    public List<Blog> adminGetAllBlogs(Integer adminId) {

        User admin = authRepository.findUserById(adminId);
        if (admin == null) {
            throw new ApiException("User not found");
        }

        if (!"ADMIN".equalsIgnoreCase(admin.getRole())) {
            throw new ApiException("You dont have access");
        }

        return blogRepository.findAll();
    }
}